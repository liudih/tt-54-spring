package com.tomtop.image.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tomtop.image.services.impl.FileUploadService;

/**
 * file
 * 
 * @author lijun
 *
 */
@RestController
@RequestMapping(value = "/fc/v1/cache/fastdfs", method = RequestMethod.GET)
public class ImageCacheController {

	@Autowired
	FileUploadService upload;

	@RequestMapping
	public int index() {
		upload.upload(true);
		return 2;
	}
}
