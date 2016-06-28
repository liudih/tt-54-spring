package com.tomtop.image.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.util.Maps;
import com.tomtop.image.configuration.FastdfsSettings;
import com.tomtop.image.services.IProductImageService;
import com.tomtop.image.services.IProductImageService.Shape;
import com.tomtop.image.services.impl.FetchService;
import com.tomtop.product.mappers.CategoryBaseMapper;

@RestController
@RequestMapping("/chicuu")
public class ChicuuController {
	private static boolean started = false;

	private static Logger logger = LoggerFactory
			.getLogger(ChicuuController.class);

	@Autowired
	IProductImageService service;

	@Autowired
	AutowireCapableBeanFactory factory;

	@Autowired
	FastdfsSettings setting;

	@Autowired
	CategoryBaseMapper categoryMapper;

	@Autowired
	FetchService fetchService;

	@RequestMapping("/fetch")
	public Map<String, Object> fetch(@RequestParam boolean q) {
		Map<String, Object> feedback = Maps.newHashMap();

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

		// 抓取长方形
		fetchService.fetchChicuu(Shape.oblong);
		// 抓取正方形
		fetchService.fetchChicuu(Shape.square);

		feedback.put("succeed", true);

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
