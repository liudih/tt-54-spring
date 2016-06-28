/**
 * 
 */
package com.tomtop.image.thread;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.lang.StringUtils;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.tomtop.image.configuration.FastdfsSettings;
import com.tomtop.image.models.bo.ImageBo;
import com.tomtop.image.models.dto.ProductImage;
import com.tomtop.image.services.IProductImageService;
import com.tomtop.image.services.IProductImageService.Shape;
import com.tomtop.image.services.impl.ImageHelper;

/**
 * 服装网站图片抓取线程
 * 
 * @author lijun
 */
public class ChicuuFetchThread extends Thread {
	private static Logger logger = LoggerFactory.getLogger(FetchThread.class);

	@Autowired
	IProductImageService service;

	@Autowired
	FastdfsSettings setting;

	@Autowired
	HttpRequestFactory httpRequestFactory;

	@Autowired
	ImageHelper imgHelper;

	// 抓取开始页
	private int start;
	private int end;
	// 1长方形 0正方形
	private Shape shape;

	TrackerServer tracker = null;

	/**
	 * 
	 * @param start
	 * @param end
	 * @param categoryIds
	 *            (逗号风格的ids 比如: 1,2,3...)
	 * @param shape
	 *            1长方形 0正方形
	 */
	public ChicuuFetchThread(int start, int end, Shape shape) {
		if (end - start < 0) {
			throw new NullPointerException("endPage - startPage < 0");
		}

		this.start = start;
		this.end = end;
		this.shape = shape;
	}

	@Override
	public void run() {

		logger.debug("{} shape:{} start:{} end:{}", Thread.currentThread()
				.getName(), shape, start, end);
		int next = start;

		boolean isLast = false;

		while (true) {
			List<ProductImage> pImg = service.selectByShapeForChicuu(next, end,
					shape);

			if (pImg == null || pImg.size() == 0) {
				break;
			}
			int lastId = pImg.get(pImg.size() - 1).getIid();
			if (next == lastId) {
				if (isLast) {
					break;
				} else {
					isLast = true;
				}

			}
			next = lastId;

			FluentIterable
					.from(pImg)
					.forEach(
							i -> {
								String imgUrl = i.getCoriginalurl();
								if (StringUtils.isEmpty(imgUrl)) {
									imgUrl = i.getCimageurl();
								}
								if (StringUtils.isNotEmpty(imgUrl)
										&& imgUrl.trim().startsWith("http://")) {
									try {
										GenericUrl url = new GenericUrl(imgUrl);
										HttpRequest request = httpRequestFactory
												.buildGetRequest(url);
										HttpResponse response = null;
										try {
											response = request.execute();
										} catch (HttpResponseException e) {
											int code = e.getStatusCode();

											service.changeClipToTrueById(
													i.getIid(), code);
											return;

										}

										String type = response.getContentType();

										String extName = type.substring(type
												.indexOf("/") + 1);

										// String extName = "jpg";

										ByteArrayOutputStream baos = new ByteArrayOutputStream();
										response.download(baos);

										byte[] img = baos.toByteArray();

										InputStream is = new ByteArrayInputStream(
												img);
										BufferedImage bimg = ImageIO.read(is);

										int w = bimg.getWidth();
										int h = bimg.getHeight();
										// 找到最大值然后补白
										int max = Math.max(w, h);

										byte[] white = this.getWhiteImage(max,
												max);

										// 最终图片
										ByteArrayOutputStream finalImage = new ByteArrayOutputStream();
										// 最终宽度
										int finalWidth;
										if (Shape.oblong == shape) {
											// 最终宽度
											finalWidth = (int) (0.728 * max);
										} else {
											finalWidth = max;
										}

										Thumbnails
												.of(new ByteArrayInputStream(
														white))
												.watermark(Positions.CENTER,
														bimg, 1f)
												.forceSize(finalWidth, max)
												.toOutputStream(finalImage);

										String relativeUrl = i.getCimageurl();

										if (relativeUrl.startsWith("/")) {
											relativeUrl = "clip" + relativeUrl;
										} else {
											relativeUrl = "clip/" + relativeUrl;
										}
										ImageBo image = new ImageBo(finalImage
												.toByteArray(), extName);
										image.setPath(relativeUrl);

										List<ImageBo> images = this
												.getCcompress(image);
										images.add(image);

										boolean succeed = this.imgHelper
												.save(images);
										if (succeed) {
											service.changeClipToTrueById(i
													.getIid());
										}

									} catch (Exception e) {
										logger.error(
												"fetch image error for iid:{}",
												i.getIid(), e);
									}
								}
							});

		}

		logger.debug(Thread.currentThread().getName() + " end");

	}

	/**
	 * 获取原图的各种规格的压缩图
	 * 
	 * @param image
	 * @throws IOException
	 */
	private List<ImageBo> getCcompress(ImageBo srcImage) throws IOException {

		List<ImageBo> result = Lists.newLinkedList();

		JSONArray ja = this.setting.getChicuuJa();
		for (int i = 0; i < ja.size(); i++) {
			JSONObject json = ja.getJSONObject(i);
			Integer x = json.getInteger("x");
			Integer y = null;

			if (Shape.square == this.shape) {
				y = x;
			} else {
				y = json.getInteger("y");
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			Thumbnails.of(new ByteArrayInputStream(srcImage.getData()))
					.outputQuality(1.0).size(x, y).useOriginalFormat()
					.toOutputStream(out);

			byte[] bytes = out.toByteArray();

			ImageBo img = new ImageBo(bytes, srcImage.getType());
			img.setWidth(x);
			img.setHeight(x);
			img.setPath(srcImage.getPath());

			result.add(img);
		}

		return result;
	}

	/**
	 * 生成一张w x h规格的白底图片
	 * 
	 * @param w
	 * @param h
	 * @return
	 * @throws IOException
	 */
	private byte[] getWhiteImage(int w, int h) throws IOException {

		BufferedImage b_img = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = b_img.createGraphics();

		graphics.setPaint(new Color(255, 255, 255));
		graphics.fillRect(0, 0, b_img.getWidth(), b_img.getHeight());

		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

		ImageIO.write(b_img, "jpg", byteOut);

		byte[] bytes = byteOut.toByteArray();

		return bytes;
	}
}
