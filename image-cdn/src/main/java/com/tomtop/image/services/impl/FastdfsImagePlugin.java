package com.tomtop.image.services.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.tomtop.image.configuration.FastdfsSettings;
import com.tomtop.image.models.bo.ImageBo;
import com.tomtop.image.models.dto.Image;
import com.tomtop.image.services.IFileRouteService;
import com.tomtop.image.services.IImagePlugin;

/**
 * fastdfs 服务器取图片
 * 
 * @author lijun
 *
 */
@Service("fastdfs")
public class FastdfsImagePlugin implements IImagePlugin {

	private static Logger Logger = LoggerFactory
			.getLogger(FastdfsImagePlugin.class);

	private static int ogX = 2000;
	private static int ogY = 2000;

	@Autowired
	TrackerClient tClient;

	@Autowired
	IFileRouteService routeService;

	@Autowired
	EventBus eventBus;

	@Autowired
	FastdfsSettings setting;

	@Override
	public int getPriority() {
		return 1;
	}

	public FastdfsImagePlugin() {

	}

	@Override
	public ImageBo getImage(String dir) {
		Logger.debug("*************get image from fastdfs*************");
		Image img = routeService.getImageByPath(dir);
		if (img == null || img.getCfastdfsurl() == null) {
			Logger.debug("fastdfsurl is null");
			return null;
		}
		TrackerServer tracker = null;
		try {
			tracker = tClient.getConnection();
			StorageClient1 sClient = new StorageClient1(tracker, null);

			String url = img.getCfastdfsurl();

			byte[] ib = sClient.download_file1(url);
			Logger.debug("get image succeed:{}", dir);

			ImageBo bo = new ImageBo(ib, img.getCcontenttype());

			return bo;
		} catch (Exception e) {
			Logger.debug("get image from fastdfs failed:{}", dir, e);
		} finally {
			try {
				if (tracker != null) {
					tracker.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	public ImageBo getImage(String dir, Integer width, Integer height) {
		Image img = routeService.getImageByPath(dir, width, height);

		if (img == null || img.getCfastdfsurl() == null) {
			Logger.debug("fastdfsurl is null");
			// 取原图生成缩小图
			Image originalImg = routeService.getImageByPath(dir);
			if (originalImg != null) {
				String fastdfsUrl = originalImg.getCfastdfsurl();

				TrackerServer tracker = null;

				try {
					tracker = tClient.getConnection();
					StorageClient1 sClient = new StorageClient1(tracker, null);

					byte[] ib = sClient.download_file1(fastdfsUrl);
					Logger.debug("get image succeed:{}", dir);

					ByteArrayOutputStream out = new ByteArrayOutputStream();

					if (2000 == width && 2000 == height) {
						Thumbnails.of(new ByteArrayInputStream(ib))
								.outputQuality(0.9).useOriginalFormat()
								.scale(1.0).toOutputStream(out);

					} else {
						Thumbnails.of(new ByteArrayInputStream(ib))
								.size(width, height).keepAspectRatio(true)
								.useOriginalFormat().toOutputStream(out);
					}

					byte[] bytes = out.toByteArray();

					ImageBo bo = new ImageBo(bytes, "jpg");

					// 保存图片到fastdfs
					originalImg.setBcontent(out.toByteArray());
					originalImg.setIwidth(width);
					originalImg.setIheight(height);
					originalImg.setCcontenttype("jpg");
					// SaveImageEvent event = new SaveImageEvent(originalImg);
					// eventBus.post(event);

					return bo;
				} catch (Exception e) {
					Logger.error("{} xy:{} {} error", dir, width, height, e);
					return null;
				}

			}
			return null;
		}
		TrackerServer tracker = null;
		try {
			tracker = tClient.getConnection();
			StorageClient1 sClient = new StorageClient1(tracker, null);

			String url = img.getCfastdfsurl();

			byte[] ib = sClient.download_file1(url);
			Logger.debug("get image succeed:{}", dir);

			ImageBo bo = new ImageBo(ib, img.getCcontenttype());
			return bo;
		} catch (Exception e) {
			Logger.debug("get image from fastdfs failed:{}", dir, e);
		} finally {
			if (tracker != null) {
				try {
					tracker.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	@Override
	public String save(ImageBo img) {
		Logger.debug("****************start to handle SaveImageEvent****************");

		if (img == null) {
			Logger.error("img is null so igonre upload");
			return null;
		}

		byte[] file_buff = img.getData();
		if (file_buff == null || file_buff.length == 0) {
			Logger.error("image byte is null so igonre upload");
			return null;
		}
		String type = img.getType();
		if (type == null || type.length() == 0) {
			Logger.error("image type is null so igonre upload");
			return null;
		}
		String ext_name = type;
		NameValuePair[] meta_list = null;

		TrackerServer tracker = null;
		try {

			tracker = tClient.getConnection();

			StorageClient sClient = new StorageClient(tracker, null);
			String[] feedback = sClient.upload_file(file_buff, ext_name,
					meta_list);

			String fullDir = feedback[0] + "/" + feedback[1];

			return fullDir;

		} catch (Exception e) {
			Logger.error("upload image error", e);
		} finally {
			if (tracker != null) {
				try {
					tracker.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public byte[] getFile(String dir) {
		if (StringUtils.isEmpty(dir)) {
			throw new NullPointerException("dir is null");
		}
		TrackerServer tracker = null;
		try {
			tracker = tClient.getConnection();
			StorageClient1 sClient = new StorageClient1(tracker, null);

			byte[] ib = sClient.download_file1(dir);
			Logger.debug("get image succeed:{}", dir);

			return ib;
		} catch (Exception e) {
			Logger.debug("get image from fastdfs failed:{}", dir, e);
		} finally {
			try {
				if (tracker != null) {
					tracker.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public boolean saveProductOriginalImage(ImageBo img) {

		String path = img.getPath();
		String type = img.getType();
		if (StringUtils.isEmpty(path)) {
			Logger.error("img path is null");
			return false;
		}
		if (StringUtils.isEmpty(type)) {
			Logger.error("img type is null");
			return false;
		}

		type = type.toLowerCase();

		List<ImageBo> compressImgs = null;
		// 生成压缩图片
		if ("jpeg".equals(type) || "jpg".equals(type)) {
			compressImgs = compress(img);
		} else {
			compressImgs = Lists.newLinkedList();
		}
		compressImgs.add(img);

		return save(compressImgs);
	}

	private boolean delete(String route) {
		TrackerServer tracker = null;
		try {
			tracker = tClient.getConnection();
			StorageClient1 sClient = new StorageClient1(tracker, null);

			int succeed = sClient.delete_file1(route);
			Logger.debug("delete image({}) feedback:{}", route, succeed);

			return succeed == 0 ? true : false;
		} catch (Exception e) {
			Logger.error("delete image from fastdfs failed:{}", route, e);
			return false;
		} finally {
			try {
				if (tracker != null) {
					tracker.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 压缩原图的各种尺寸
	 * 
	 * @param original
	 *            原图
	 * @return
	 */
	private List<ImageBo> compress(ImageBo original) {
		String path = original.getPath();
		if (StringUtils.isEmpty(path)) {
			throw new NullPointerException("path is null");
		}

		List<ImageBo> feedback = Lists.newLinkedList();

		List<Image> xyImg = this.routeService.selectXy(path);
		if (xyImg == null) {
			xyImg = new ArrayList<Image>();
		}
		List<String> xys = Lists.transform(xyImg, xy -> xy.getIwidth() + "x"
				+ xy.getIheight());
		// 判断所有的尺寸图片是否都已经存在了
		List<String> filterXy = FluentIterable.from(this.setting.getXyList())
				.filter(xy -> !xys.contains(xy)).toList();

		String ogxy = ogX + "x" + ogY;
		boolean isOgxy = true;
		if (xys.contains(ogxy)) {
			isOgxy = false;
		}
		// 后缀名称
		String type = original.getType();
		if (filterXy.size() > 0 || isOgxy) {
			List<Integer[]> cxy = Lists.transform(filterXy, xy -> {
				String[] sa = xy.split("x");
				Integer x = Integer.parseInt(sa[0]);
				Integer y = Integer.parseInt(sa[1]);
				Integer[] ret = { x, y };
				return ret;
			});

			try {
				byte[] originalBytes = original.getData();

				if (isOgxy) {

					ByteArrayOutputStream ogOut = new ByteArrayOutputStream();

					Thumbnails.of(new ByteArrayInputStream(originalBytes))
							.outputQuality(0.9).useOriginalFormat().scale(1.0)
							.toOutputStream(ogOut);

					byte[] ogBytes = ogOut.toByteArray();

					ImageBo cImage = new ImageBo(ogBytes, type);
					cImage.setPath(path);
					cImage.setWidth(this.ogX);
					cImage.setHeight(this.ogY);

					feedback.add(cImage);
				}
				for (Integer[] xy : cxy) {
					Integer x = xy[0];
					Integer y = xy[1];
					if (x > 0 && y > 0) {
						ByteArrayOutputStream out = new ByteArrayOutputStream();

						Thumbnails.of(new ByteArrayInputStream(originalBytes))
								.size(x, y).keepAspectRatio(true)
								.useOriginalFormat().toOutputStream(out);
						byte[] bytes = out.toByteArray();

						ImageBo cImage = new ImageBo(bytes, type);
						cImage.setPath(path);
						cImage.setWidth(x);
						cImage.setHeight(y);

						feedback.add(cImage);
					}
				}
			} catch (Exception e) {
				Logger.error("{}  error", path, e);
			}
		}
		return feedback;

	}

	@Override
	public boolean save(List<ImageBo> imgs) {
		if (imgs == null) {
			throw new NullPointerException("imgs is null");
		}
		TrackerServer tracker = null;
		try {

			tracker = tClient.getConnection();
//			String hostName = tracker.getInetSocketAddress().getHostName();
//			int port = tracker.getInetSocketAddress().getPort();
//			Logger.info("hostName:{}:{}",hostName,port);
			StorageClient1 sClient = new StorageClient1(tracker, null);
			
			imgs.forEach(i -> {

				String path = i.getPath();
				String type = i.getType();
				Integer width = i.getWidth();
				Integer height = i.getHeight();

				try {
					// 如果已经存在该图片,则先删除
					Image routeImg = null;
					if (i.getWidth() == null) {
						routeImg = routeService.selectRoute(i.getPath());
					} else {
						routeImg = routeService.selectRoute(i.getPath(),
								i.getWidth(), i.getHeight());
					}

					String md5 = Hex.encodeHexString(MessageDigest.getInstance(
							"MD5").digest(i.getData()));

					if (routeImg != null
							&& StringUtils.isNotEmpty(routeImg.getCfastdfsurl())) {
						sClient.delete_file1(routeImg.getCfastdfsurl());

						String route = sClient.upload_file1(i.getData(), type,
								null);
						routeService.updateById(routeImg.getIid(), route, type,
								md5);

					} else {
						String route = sClient.upload_file1(i.getData(), type,
								null);
						routeService.insert(path, route, type, width, height,
								md5);
					}

				} catch (Exception e) {
					Logger.error(
							"batch save img error,path:{} width:{} height{}",
							path, width, height, e);
				}
			});

			return true;

		} catch (Exception e) {
			Logger.error("batch save image error", e);
			return false;
		} finally {
			if (tracker != null) {
				try {
					tracker.close();
				} catch (IOException e) {
					Logger.error("close tracker error", e);
				}
			}
		}
	}
}
