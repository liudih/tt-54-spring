package com.tomtop.image.controllers;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.util.Maps;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.tomtop.image.services.IImageService;
import com.tomtop.image.services.impl.FileCompressService;
import com.tomtop.image.services.impl.FileUploadService;
import com.tomtop.image.thread.RefetchThread;

/**
 * file
 * 
 * @author lijun
 *
 */
@RestController
@RequestMapping(value = "/original", method = RequestMethod.GET)
public class ImageController {

	@Autowired
	IImageService service;

	@Autowired
	FileUploadService upload;

	@Autowired
	FileCompressService compressService;

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	AutowireCapableBeanFactory factory;

	private static boolean move = false;
	private static boolean compress = false;
	private static boolean refetch = false;

	@RequestMapping("/move")
	public Map<String, Object> index(@RequestParam boolean q) {
		Map<String, Object> feedback = Maps.newHashMap();

		if (q) {
			feedback.put("qsucceed", !move);
			return feedback;
		}
		boolean canMove = canMove();
		if (canMove) {
			upload.upload(false);
			move = false;
			feedback.put("succeed", true);
		} else {
			feedback.put("succeed", false);
			feedback.put("error", "job already started");
		}

		return feedback;
	}

	@RequestMapping("/compress")
	public Map<String, Object> compress(@RequestParam boolean q) {
		Map<String, Object> feedback = Maps.newHashMap();
		if (q) {
			feedback.put("qsucceed", !compress);
			return feedback;
		}

		boolean canCompress = canCompress();
		if (canCompress) {
			compressService.compress();
			compress = false;
			feedback.put("succeed", true);
		} else {
			feedback.put("succeed", false);
			feedback.put("error", "job already started");
		}

		return feedback;
	}

	@RequestMapping("/refetch")
	public Map<String, Object> refetch(Integer nums, boolean q) {
		Map<String, Object> feedback = Maps.newHashMap();
		if (q) {
			feedback.put("qsucceed", !refetch);
			return feedback;
		}

		boolean canRefetch = canRefetch();
		if (!canRefetch) {
			feedback.put("succeed", false);
			feedback.put("error", "job already started");
			return feedback;
		}

		try {
			List<RefetchThread> threads = Lists.newLinkedList();
			for (int i = 1; i <= nums; i++) {
				Resource txt = resourceLoader
						.getResource("classpath:refetch/refetch" + i + ".txt");
				File file = txt.getFile();

				List<String> lines = Files.readLines(file,
						Charset.forName("UTF-8"));

				RefetchThread thread = new RefetchThread(lines);

				factory.autowireBean(thread);

				thread.start();
				threads.add(thread);
			}

			for (RefetchThread t : threads) {
				t.join();
			}

			refetch = false;

			feedback.put("succeed", true);
			return feedback;

		} catch (Exception e) {
			e.printStackTrace();
			feedback.put("succeed", false);
			feedback.put("error", e.getMessage());
		}
		return feedback;
	}

	private static synchronized boolean canMove() {
		if (move) {
			return false;
		}

		move = true;
		return true;
	}

	private static synchronized boolean canCompress() {
		if (compress) {
			return false;
		}

		compress = true;
		return true;
	}

	private static synchronized boolean canRefetch() {
		if (refetch) {
			return false;
		}

		refetch = true;
		return true;
	}
}
