package com.tomtop.image.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tomtop.image.configuration.FastdfsSettings;
import com.tomtop.image.models.dto.Range;
import com.tomtop.image.services.IProductImageService;
import com.tomtop.image.services.IProductImageService.Shape;
import com.tomtop.image.thread.ChicuuFetchThread;
import com.tomtop.image.thread.FetchThread;

/**
 * 图片抓取服务
 * 
 * @author lijun
 *
 */
@Service
public class FetchService {

	private static Logger logger = LoggerFactory
			.getLogger(FileCompressService.class);

	@Autowired
	FastdfsSettings setting;

	@Autowired
	AutowireCapableBeanFactory factory;

	@Autowired
	IProductImageService imgService;

	public void fetch() {

		// 采用多线程处理文件上传到fastdfs服务器
		// 查看数据库中有多少条数据，然后决定每个线程需要处理的数据量
		Integer max = imgService.getMaxId();
		Integer min = imgService.getMinId();

		if (max == null || min == null) {
			return;
		}
		int total = max - min + 1;
		if (total <= 0) {
			return;
		}
		// 当前系统可用核心数
		double ap;
		if (setting.getThreadNum() != null && setting.getThreadNum() > 0) {
			ap = setting.getThreadNum();
		} else {
			ap = Runtime.getRuntime().availableProcessors();
		}

		logger.debug("thread num:{}", ap);

		int size = (int) Math.ceil(total / ap);
		List<FetchThread> threadList = Lists.newLinkedList();
		for (int j = 0; j < ap; j++) {
			if (min - 1 + (j * size + 1) > max) {
				break;
			} else if (min + (j + 1) * size > max) {
				FetchThread thread = new FetchThread(min + j * size, max);

				factory.autowireBean(thread);

				thread.start();
				threadList.add(thread);
			} else {
				FetchThread thread = new FetchThread(min + j * size, min - 1
						+ (j + 1) * size);

				factory.autowireBean(thread);

				thread.start();
				threadList.add(thread);
			}
		}

		for (FetchThread t : threadList) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 服装站图片抓取
	 * 
	 * @author lijun
	 * @param categoryIds
	 *            类目ids(比如：1,2,3....)
	 * @param shape
	 *            形状(1:长方形 0:正方形)
	 */
	public void fetchChicuu(Shape shape) {
		Range range = imgService.getMaxMinForChicuu(shape);
		if (range == null) {
			return;
		}

		Integer max = range.getMax();
		Integer min = range.getMin();

		int total = max - min + 1;
		if (total <= 0) {
			return;
		}
		// 当前系统可用核心数
		double ap;
		if (setting.getThreadNum() != null && setting.getThreadNum() > 0) {
			ap = setting.getThreadNum();
		} else {
			ap = Runtime.getRuntime().availableProcessors();
		}

		logger.debug("thread num:{}", ap);

		int size = (int) Math.ceil(total / ap);
		List<ChicuuFetchThread> threadList = Lists.newLinkedList();
		for (int j = 0; j < ap; j++) {
			if (min - 1 + (j * size + 1) > max) {
				break;
			} else if (min + (j + 1) * size > max) {
				ChicuuFetchThread thread = new ChicuuFetchThread(
						min + j * size, max, shape);

				factory.autowireBean(thread);

				thread.start();
				threadList.add(thread);
			} else {
				ChicuuFetchThread thread = new ChicuuFetchThread(
						min + j * size, min - 1 + (j + 1) * size, shape);

				factory.autowireBean(thread);

				thread.start();
				threadList.add(thread);
			}
		}

		for (ChicuuFetchThread t : threadList) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
