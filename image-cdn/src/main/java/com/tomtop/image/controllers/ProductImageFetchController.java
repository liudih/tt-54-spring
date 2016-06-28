package com.tomtop.image.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tomtop.image.configuration.FastdfsSettings;
import com.tomtop.image.services.impl.FetchService;
import com.tomtop.image.thread.FetchThread;
import com.tomtop.image.thread.FetchXyThread;

/**
 * 产品图片抓取任务
 * 
 * @author lijun
 *
 */
@RestController
@RequestMapping("/fetch")
public class ProductImageFetchController {
	private static boolean started = false;
	private static boolean fetchXy = false;

	@Autowired
	FetchService service;

	@Autowired
	FastdfsSettings config;

	@Autowired
	AutowireCapableBeanFactory factory;

	@RequestMapping()
	public Map<String, Object> fetch(@RequestParam boolean q) {
		Map<String, Object> feedback = Maps.newLinkedHashMap();
		if (q) {
			if (!started) {
				feedback.put("succeed", true);
			} else {
				feedback.put("succeed", false);
			}
			return feedback;
		}
		boolean canStart = canStart();
		if (!canStart) {
			feedback.put("succeed", false);
			feedback.put("error", "job have started");
			return feedback;
		}

		service.fetch();

		feedback.put("succeed", true);

		started = false;

		return feedback;
	}

	@RequestMapping("/xy/{width}/{height}")
	public Map<String, Object> fetchXy(@PathVariable("width") Integer width,
			@PathVariable("height") Integer height, @RequestParam Integer start,
			@RequestParam Integer end, @RequestParam Integer t,
			@RequestParam boolean q) {

		Map<String, Object> feedback = Maps.newLinkedHashMap();
		if (q) {
			feedback.put("succeed", !fetchXy);
			return feedback;
		}

		boolean valid = config.isValidImg(width, height);

		if (!valid) {
			feedback.put("succeed", false);
			feedback.put("error", "invalid size");
			return feedback;
		}
		if (end == null || end <= 0) {
			feedback.put("succeed", false);
			feedback.put("error", "end < 0");
			return feedback;
		}

		if (start == null || start <= 0) {
			feedback.put("succeed", false);
			feedback.put("error", "start < 0");
			return feedback;
		}

		boolean canFetchXy = canFetchXy();
		if (!canFetchXy) {
			feedback.put("succeed", false);
			feedback.put("error", "job have started");
			return feedback;
		}

		int max = end;

		// 当前系统可用核心数
		double ap = t;

		int size = (int) Math.ceil(max / ap);
		List<FetchXyThread> threadList = Lists.newLinkedList();
		for (int j = 0; j < ap; j++) {
			if ((j * size + 1) > max) {
				break;
			} else if ((j + 1) * size > max) {
				FetchXyThread thread = new FetchXyThread(j * size + 1, max,
						width, height);

				factory.autowireBean(thread);

				thread.start();
				threadList.add(thread);
			} else {
				FetchXyThread thread = new FetchXyThread(j * size + 1, (j + 1)
						* size, width, height);

				factory.autowireBean(thread);

				thread.start();
				threadList.add(thread);
			}
		}

		feedback.put("succeed", true);

		for (FetchXyThread thread : threadList) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		fetchXy = false;

		return feedback;

	}

	@RequestMapping(value = "/ids",method = RequestMethod.POST )
	public Map<String, Object> fetchByIds(@RequestBody String idStr) {
		Map<String, Object> feedback = Maps.newLinkedHashMap();
		
		JSONArray array = JSONArray.parseArray(idStr);
		
		if(array == null){
			feedback.put("succeed", false);
			feedback.put("error", "JSONArray is null");
			return feedback;
		}
		
		List<FetchThread> threadList = Lists.newLinkedList();
		
		for(int i = 0; i < array.size(); i++){
			int id = array.getIntValue(i);
			FetchThread thread = new FetchThread(id, id);

			factory.autowireBean(thread);
			threadList.add(thread);
			thread.start();
			
		}
		
		
		for (FetchThread t : threadList) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		feedback.put("succeed", true);
		return feedback;
	}
	
	private synchronized static boolean canStart() {
		if (started) {
			return false;
		}
		started = true;
		return true;
	}

	private synchronized static boolean canFetchXy() {
		if (fetchXy) {
			return false;
		}
		fetchXy = true;
		return true;
	}
	
	
	
}
