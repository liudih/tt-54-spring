package com.tomtop.image.services.impl;

import java.util.List;

import org.csource.fastdfs.TrackerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tomtop.image.configuration.FastdfsSettings;
import com.tomtop.image.services.IFileRouteService;
import com.tomtop.image.services.IImageService;
import com.tomtop.image.thread.UploadThread;

/**
 * 文件上传服务
 * 
 * @author lijun
 *
 */
@Service
public class FileUploadService {

	private static Logger logger = LoggerFactory
			.getLogger(FileUploadService.class);

	@Autowired
	IImageService service;

	@Autowired
	IFileRouteService routeService;

	@Autowired
	FastdfsSettings setting;

	@Autowired
	TrackerClient tClient;

	public void upload(boolean isCacheImage) {

		// 采用多线程处理文件上传到fastdfs服务器
		// 查看数据库中有多少条数据，然后决定每个线程需要处理的数据量
		int max;
		if (isCacheImage) {
			max = service.getCacheMaxId();
		} else {
			max = service.getMaxId();
		}

		// 当前系统可用核心数
		double ap;
		if (setting.getThreadNum() != null && setting.getThreadNum() > 0) {
			ap = setting.getThreadNum();
		} else {
			ap = Runtime.getRuntime().availableProcessors();
		}

		logger.debug("thread num:{}", ap);

		int size = (int) Math.ceil(max / ap);
		List<UploadThread> threadList = Lists.newLinkedList();
		for (int j = 0; j < ap; j++) {
			if ((j * size + 1) > max) {
				break;
			} else if ((j + 1) * size > max) {
				UploadThread thread = new UploadThread(j * size + 1, max,
						service, routeService, tClient,isCacheImage);
				thread.start();
				threadList.add(thread);
			} else {
				UploadThread thread = new UploadThread(j * size + 1, (j + 1)
						* size, service, routeService, tClient,isCacheImage);
				thread.start();
				threadList.add(thread);
			}
		}

		for (UploadThread t : threadList) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
