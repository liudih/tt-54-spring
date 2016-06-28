/**
 * 
 */
package com.tomtop.image.thread;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.List;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.codec.binary.Hex;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.FluentIterable;
import com.tomtop.image.models.dto.Image;
import com.tomtop.image.services.IFileRouteService;

/**
 * 图片抓取线程
 * 
 * @author lijun
 */
public class FetchXyThread extends Thread {
	private static Logger logger = LoggerFactory.getLogger(FetchXyThread.class);

	// 抓取开始页
	private int start;
	private int end;
	private int width;
	private int height;

	@Autowired
	IFileRouteService routeService;

	@Autowired
	TrackerClient tClient;

	TrackerServer tracker = null;

	public FetchXyThread(int start, int end, Integer width, Integer height) {
		if (end - start < 0) {
			throw new NullPointerException("endPage - startPage < 0");
		}

		this.start = start;
		this.end = end;
		this.width = width;
		this.height = height;
	}

	@Override
	public void run() {

		logger.debug(Thread.currentThread().getName() + " start:" + start
				+ " end:" + end);

		try {
			tracker = tClient.getConnection();
		} catch (IOException e1) {
			logger.error("get tracker error", e1);
			return;
		}
		StorageClient1 sClient = new StorageClient1(tracker, null);

		while (true) {
			List<Image> pImg = routeService.getImage(start, end, width, height,
					false);

			if (pImg == null || pImg.size() == 0) {
				break;
			}

			
			FluentIterable
					.from(pImg)
					.forEach(
							i -> {
								if(width != i.getIwidth() || height != i.getIheight()){
									return;
								}
								
								String path = i.getCpath();

								Image ogImg = routeService.getImageByPath(path);
								if (ogImg == null
										|| ogImg.getCfastdfsurl() == null) {
									return;
								}
								String url = ogImg.getCfastdfsurl();

								try {

									byte[] ib = sClient.download_file1(url);
									logger.debug("get image succeed:{}", url);

									ByteArrayOutputStream ogOut = new ByteArrayOutputStream();

									if (2000 == width && 2000 == height) {
										Thumbnails
												.of(new ByteArrayInputStream(ib))
												.outputQuality(0.9)
												.useOriginalFormat().scale(1.0)
												.toOutputStream(ogOut);

									} else {
										Thumbnails
												.of(new ByteArrayInputStream(ib))
												.size(width, height)
												.outputQuality(0.9)
												.useOriginalFormat().scale(1.0)
												.toOutputStream(ogOut);
									}

									byte[] resultBytes = ogOut.toByteArray();

									sClient.delete_file1(i.getCfastdfsurl());
									String route = sClient.upload_file1(
											resultBytes, i.getCcontenttype(),
											null);

									String md5 = Hex
											.encodeHexString(MessageDigest
													.getInstance("MD5").digest(
															resultBytes));

									routeService.updateById(i.getIid(), route,
											md5);

								} catch (Exception e) {
									logger.error("modify img:{} error",
											i.getIid(), e);
								}

							});

		}

		try {
			tracker.close();
		} catch (IOException e) {
			logger.error("close tracker error", e);
		}

		logger.debug(Thread.currentThread().getName() + " end");

	}

}
