/**
 * 
 */
package com.tomtop.image.thread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.codec.binary.Hex;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.tomtop.image.configuration.FastdfsSettings;
import com.tomtop.image.models.dto.Image;
import com.tomtop.image.services.IFileRouteService;

/**
 * 文件压缩线程
 * 
 * @author lijun
 */
public class CompressThread extends Thread {
	private static Logger logger = LoggerFactory
			.getLogger(CompressThread.class);

	private static int ogX = 2000;
	private static int ogY = 2000;
	private static String JPG = "jpg";

	@Autowired
	IFileRouteService service;

	@Autowired
	TrackerClient tClient;

	@Autowired
	FastdfsSettings setting;

	// 抓取开始页
	private int start;
	private int end;

	TrackerServer tracker = null;

	public CompressThread(int start, int end) {
		if (end - start < 0) {
			throw new NullPointerException("endPage - startPage < 0");
		}

		this.start = start;
		this.end = end;
	}

	@Override
	public void run() {
		if (this.tracker == null) {
			try {
				this.tracker = tClient.getConnection();
			} catch (IOException e) {
				logger.error("get fastdfs connection error");
				return;
			}
		}

		StorageClient1 sClient = new StorageClient1(this.tracker, null);

		logger.debug(Thread.currentThread().getName() + " start:" + start
				+ " end:" + end);
		while (true) {
			List<Image> images = service.getOriginalImageByLimit(start, end);

			if (images == null || images.size() == 0) {
				break;
			}
			FluentIterable
					.from(images)
					.forEach(
							img -> {
								String path = img.getCpath();

								List<Image> xyImg = this.service.selectXy(path);
								if (xyImg == null) {
									xyImg = new ArrayList();
								}
								List<String> xys = Lists.transform(
										xyImg,
										xy -> xy.getIwidth() + "x"
												+ xy.getIheight());
								// 判断所有的尺寸图片是否都已经存在了
								List<String> filterXy = FluentIterable
										.from(this.setting.getXyList())
										.filter(xy -> !xys.contains(xy))
										.toList();

								String ogxy = ogX + "x" + ogY;
								boolean isOgxy = true;
								if (xys.contains(ogxy)) {
									isOgxy = false;
								}
								if (filterXy.size() > 0 || isOgxy) {
									List<Integer[]> cxy = Lists.transform(
											filterXy,
											xy -> {
												String[] sa = xy.split("x");
												Integer x = Integer
														.parseInt(sa[0]);
												Integer y = Integer
														.parseInt(sa[1]);
												Integer[] ret = { x, y };
												return ret;
											});
									// 原图
									Image original = img;

									String fastdfsUrl = original
											.getCfastdfsurl();
									try {
										byte[] originalBytes = sClient
												.download_file1(fastdfsUrl);

										if (isOgxy) {

											ByteArrayOutputStream ogOut = new ByteArrayOutputStream();

											Thumbnails
													.of(new ByteArrayInputStream(
															originalBytes))
													.outputQuality(0.9)
													.useOriginalFormat()
													.scale(1.0)
													.toOutputStream(ogOut);

											byte[] ogBytes = ogOut
													.toByteArray();

											String md5 = Hex
													.encodeHexString(MessageDigest
															.getInstance("MD5")
															.digest(ogBytes));

											Image cImage = new Image();
											cImage.setBcontent(ogBytes);
											cImage.setCpath(path);
											cImage.setIwidth(this.ogX);
											cImage.setIheight(this.ogY);
											cImage.setCcontenttype(JPG);
											cImage.setCmd5(md5);
											this.upload(cImage);

										}
										for (Integer[] xy : cxy) {
											Integer x = xy[0];
											Integer y = xy[1];
											if (x > 0 && y > 0) {
												ByteArrayOutputStream out = new ByteArrayOutputStream();

												Thumbnails
														.of(new ByteArrayInputStream(
																originalBytes))
														.size(x, y)
														.keepAspectRatio(true)
														.useOriginalFormat()
														.toOutputStream(out);
												byte[] bytes = out
														.toByteArray();

												String md5 = Hex
														.encodeHexString(MessageDigest
																.getInstance(
																		"MD5")
																.digest(bytes));

												Image cImage = new Image();
												cImage.setBcontent(bytes);
												cImage.setCpath(path);
												cImage.setIwidth(x);
												cImage.setIheight(y);
												cImage.setCcontenttype(JPG);
												cImage.setCmd5(md5);
												this.upload(cImage);

											}
										}
										
										service.changeCompressToTrueById(img.getIid());
									} catch (Exception e) {
										logger.error("{}  error", path, e);
									}
								}
							});
		}

		try {
			if (this.tracker != null) {
				this.tracker.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.debug(Thread.currentThread().getName() + " end");
	}

	public void upload(Image image) {
		byte[] file_buff = image.getBcontent();
		if (file_buff == null || file_buff.length == 0) {
			return;
		}
		String type = image.getCcontenttype();

		String ext_name = type;
		NameValuePair[] meta_list = null;

		try {
			if (this.tracker == null) {
				this.tracker = tClient.getConnection();
			}

			StorageClient sClient = new StorageClient(this.tracker, null);
			String[] feedback = sClient.upload_file(file_buff, ext_name,
					meta_list);

			String fullDir = feedback[0] + "/" + feedback[1];

			service.insert(image.getCpath(), fullDir, ext_name,
					image.getIwidth(), image.getIheight(), image.getCmd5());
			logger.debug(fullDir);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
