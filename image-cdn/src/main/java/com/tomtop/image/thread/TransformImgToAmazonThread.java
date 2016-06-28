package com.tomtop.image.thread;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.common.collect.FluentIterable;
import com.tomtop.image.models.dto.Image;
import com.tomtop.image.services.IFileRouteService;
import com.tomtop.image.services.impl.AmazonawsService;

/**
 * 转移图片到亚马逊s3
 * 
 * @author lijun
 */
public class TransformImgToAmazonThread extends Thread {
	private static Logger logger = LoggerFactory
			.getLogger(TransformImgToAmazonThread.class);

	private static final String TYPE = "image/jpg";
	private static final String HOST = "http://resource.tomtop-cdn.com/";

	@Autowired
	IFileRouteService routeService;

	@Autowired
	AmazonawsService s3;

	@Autowired
	HttpRequestFactory httpRequestFactory;

	// 抓取开始页
	private int start;
	private int end;

	TrackerServer tracker = null;

	public TransformImgToAmazonThread(int start, int end) {
		if (end - start < 0) {
			throw new NullPointerException("endPage - startPage < 0");
		}

		this.start = start;
		this.end = end;
	}

	@Override
	public void run() {
		logger.debug(Thread.currentThread().getName() + " start:" + start
				+ " end:" + end);
		try {

			while (true) {
				List<Image> images = routeService.getImagesByLimit(start, end);

				if (images == null || images.size() == 0) {
					break;
				}
				FluentIterable.from(images).forEach(i -> {
					this.upload(i);
				});

			}

		} catch (Exception e) {
			logger.error(e.toString());
		}

		logger.debug(Thread.currentThread().getName() + " end");
	}

	private void upload(Image image) {
		if (image.getCpath() == null || image.getCfastdfsurl() == null) {
			return;
		}
		try {
			StringBuilder sb = new StringBuilder();
			if (image.getIwidth() == null) {
				sb.append("product/original/");
			} else {
				sb.append("product/xy/");
				sb.append(image.getIwidth());
				sb.append("/");
				sb.append(image.getIheight());
				sb.append("/");
			}

			sb.append(image.getCpath());
			String relativePath = sb.toString();

			byte[] bytes = this.download(relativePath);
			if (bytes == null) {
				return;
			}
			s3.upload(bytes, TYPE, relativePath);
			this.routeService.changeBamazonToTrueById(image.getIid());
			logger.debug("iid:{} upload succeed", image.getIid());
		} catch (Exception e) {
			logger.error("upload img error", e);
		}
	}

	private byte[] download(String relativePath) {
		try {
			GenericUrl url = new GenericUrl(HOST + relativePath);
			HttpRequest request = httpRequestFactory.buildGetRequest(url);
			HttpResponse response = null;

			response = request.execute();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			response.download(baos);

			byte[] img = baos.toByteArray();
			return img;
		} catch (Exception e) {
			logger.error("get fastdfs error {}", relativePath, e);
			return null;
		}
	}
}
