package com.tomtop.image.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.amazonaws.services.cloudfront.model.Paths;
import com.google.api.client.util.Maps;
import com.google.common.collect.Lists;
import com.tomtop.image.models.bo.AmazonStatusEnum;
import com.tomtop.image.models.dto.Range;
import com.tomtop.image.services.IFileRouteService;
import com.tomtop.image.services.impl.AmazonawsService;
import com.tomtop.image.thread.ChicuuFetchThread;
import com.tomtop.image.thread.TransformImgToAmazonThread;

/**
 * 亚马逊对接接口
 * 
 * @author lijun
 *
 */
@RestController
@RequestMapping("/amazon")
public class AmazonawsController {
	private static Logger logger = LoggerFactory
			.getLogger(AmazonawsController.class);

	private static boolean started = false;

	@Autowired
	AmazonawsService service;

	@Value("${amazon.authorization}")
	String authorization;

	@Value("${amazon.threadNum}")
	Double threadNum;

	@Autowired
	IFileRouteService fileRouteService;

	@Autowired
	AutowireCapableBeanFactory factory;

	/**
	 * invalidation
	 */
	@RequestMapping(value = "/invalidation", method = RequestMethod.POST)
	public Map<String, Object> invalidation(@RequestBody String raw,
			HttpServletRequest request) {

		Map<String, Object> feedback = Maps.newHashMap();

		String auth = request.getHeader("Authorization");
		if (auth == null || !authorization.equals(auth)) {
			feedback.put("succeed", false);
			feedback.put("error", "no permission");
			return feedback;
		}
		JSONArray array = null;
		try {
			array = JSONArray.parseArray(raw);
		} catch (Exception e) {
			feedback.put("succeed", false);
			feedback.put("error", "request body must JSONArray");
			return feedback;
		}

		List<String> items = Lists.newLinkedList();

		array.forEach(a -> {
			items.add(a.toString());
		});

		Paths paths = new Paths();
		paths.setItems(items);
		paths.setQuantity(items.size());
		AmazonStatusEnum state = service.invalidation(paths);
		if (AmazonStatusEnum.error == state) {
			feedback.put("succeed", false);
		} else {
			feedback.put("succeed", state.toString());
		}

		return feedback;
	}

	@RequestMapping(value = "/upload-test")
	public String uploadTest() throws IOException {
		Path path = java.nio.file.Paths.get("d:/G2019PU-L-1-efcd-0Yz8.jpg");
		byte[] originalBytes = Files.readAllBytes(path);
		service.upload(originalBytes, "image/jpg",
				"test/G2019PU-L-1-efcd-0Yz8.jpg");
		return "ok";
	}

	@RequestMapping(value = "/upload")
	public Map<String, Object> upload(@RequestParam boolean q)
			throws IOException {

		Map<String, Object> feedback = Maps.newHashMap();
		feedback.put("succeed", true);

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

		Range range = fileRouteService.getMaxMinNoAmazon();

		if (range == null) {
			return feedback;
		}

		Integer max = range.getMax();
		Integer min = range.getMin();

		int total = max - min + 1;
		if (total <= 0) {
			return feedback;
		}
		// 当前线程数
		double ap = threadNum;

		logger.debug("thread num:{}", ap);

		int size = (int) Math.ceil(total / ap);
		List<TransformImgToAmazonThread> threadList = Lists.newLinkedList();
		for (int j = 0; j < ap; j++) {
			if (min - 1 + (j * size + 1) > max) {
				break;
			} else if (min + (j + 1) * size > max) {
				TransformImgToAmazonThread thread = new TransformImgToAmazonThread(
						min + j * size, max);

				factory.autowireBean(thread);

				thread.start();
				threadList.add(thread);
			} else {
				TransformImgToAmazonThread thread = new TransformImgToAmazonThread(
						min + j * size, min - 1 + (j + 1) * size);

				factory.autowireBean(thread);

				thread.start();
				threadList.add(thread);
			}
		}

		for (TransformImgToAmazonThread t : threadList) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		started = false;
		return feedback;
	}

	private synchronized static boolean canStart() {
		if (started) {
			return false;
		}
		started = true;
		return true;
	}
}
