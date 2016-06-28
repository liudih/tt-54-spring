/**
 * 
 */
package com.tomtop.image.thread;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import com.google.common.collect.FluentIterable;
import com.tomtop.image.models.dto.Image;
import com.tomtop.image.services.IFileRouteService;
import com.tomtop.image.services.IImageService;

/**
 * 文件上传线程
 * 
 * @author lijun
 */
public class UploadThread extends Thread {

	IImageService service;

	IFileRouteService routeService;

	TrackerClient tClient;

	private static Logger logger = Logger.getLogger(UploadThread.class);

	// 抓取开始页
	private int start;
	private int end;

	private boolean isCacheImage;

	TrackerServer tracker = null;

	public UploadThread(int start, int end, IImageService service,
			IFileRouteService routeService, TrackerClient tClient,
			boolean isCacheImage) {
		if (end - start < 0) {
			throw new NullPointerException("endPage - startPage < 0");
		}

		this.start = start;
		this.end = end;
		this.service = service;
		this.routeService = routeService;
		this.tClient = tClient;
		this.isCacheImage = isCacheImage;
	}

	@Override
	public void run() {
		logger.debug(Thread.currentThread().getName() + " start:" + start
				+ " end:" + end);

		while (true) {
			List<Image> images = null;
			if (isCacheImage) {
				images = service.getImageCacheByLimit(start, end);
			} else {
				images = service.getImageByLimit(start, end);
			}

			if (images == null || images.size() == 0) {
				break;
			}
			FluentIterable.from(images).forEach(i -> {
				this.upload(i);
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
		if (type == null || type.length() == 0) {
			return;
		}
		// if(!type.startsWith("image")){
		// return;
		// }
		String[] types = type.split("/");
		if (types.length < 2) {
			return;
		}
		String ext_name = "javascript".endsWith(types[1]) ? "js" : types[1];

		String fastUrl = image.getCfastdfsurl();

		if (StringUtils.isNotEmpty(fastUrl)) {
			String cpath = image.getCpath();
			Integer iwidth = image.getIwidth();
			Integer iheight = image.getIheight();
			boolean existed;
			if(iwidth == null){
				existed = routeService.existed(cpath);
			}else{
				existed = routeService.existed(cpath, iwidth, iheight);
			}
			if (!existed) {
				routeService.insert(cpath, fastUrl, ext_name, iwidth, iheight,
						image.getCmd5(), false);
			}

			return;
		}
		NameValuePair[] meta_list = null;

		try {
			if (this.tracker == null) {
				this.tracker = tClient.getConnection();
			}

			// StorageServer storage = tClient.getStoreStorage(tracker);

			StorageClient sClient = new StorageClient(this.tracker, null);
			String[] feedback = sClient.upload_file(file_buff, ext_name,
					meta_list);

			String fullDir = feedback[0] + "/" + feedback[1];
			if (this.isCacheImage) {
				service.updateCacheFastdfsUrl(fullDir, image.getIid());
			} else {
				service.updateFastdfsUrl(fullDir, image.getIid());
			}

			routeService.insert(image.getCpath(), fullDir, ext_name,
					image.getIwidth(), image.getIheight(), image.getCmd5(),
					false);
			logger.debug(fullDir);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
