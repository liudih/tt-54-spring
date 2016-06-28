package com.tomtop.image.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tomtop.image.configuration.FastdfsSettings;
import com.tomtop.image.services.IFileRouteService;
import com.tomtop.image.thread.CompressThread;

/**
 * 图片压缩服务
 * 
 * @author lijun
 *
 */
@Service
public class FileCompressService {

	private static Logger logger = LoggerFactory
			.getLogger(FileCompressService.class);

	@Autowired
	IFileRouteService service;

	@Autowired
	FastdfsSettings setting;

	@Autowired
	AutowireCapableBeanFactory factory;

	public void compress() {

		// 采用多线程处理文件上传到fastdfs服务器
		// 查看数据库中有多少条数据，然后决定每个线程需要处理的数据量
		int max = service.getOriginalMaxId();

		// 当前系统可用核心数
		double ap;
		if (setting.getThreadNum() != null && setting.getThreadNum() > 0) {
			ap = setting.getThreadNum();
		} else {
			ap = Runtime.getRuntime().availableProcessors();
		}

		logger.debug("thread num:{}", ap);

		int size = (int) Math.ceil(max / ap);
		List<CompressThread> threadList = Lists.newLinkedList();
		for (int j = 0; j < ap; j++) {
			if ((j * size + 1) > max) {
				break;
			} else if ((j + 1) * size > max) {
				CompressThread thread = new CompressThread(j * size + 1, max);

				factory.autowireBean(thread);

				thread.start();
				threadList.add(thread);
			} else {
				CompressThread thread = new CompressThread(j * size + 1,
						(j + 1) * size);

				factory.autowireBean(thread);

				thread.start();
				threadList.add(thread);
			}
		}

		for (CompressThread t : threadList) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
