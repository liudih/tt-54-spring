package com.tomtop.management.base.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMediaType;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.MultipartContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.collect.Maps;
import com.tomtop.management.config.GlobalParameter;

@Controller
@RequestMapping("/image")
public class ImageController {
	@Autowired
	GlobalParameter parameter;

	/**
	 * 
	 * @Title: uploadImage
	 * @Description: TODO(图片上传)
	 * @param @param token
	 * @param @param file
	 * @param @return
	 * @param @throws IOException    
	 * @return Map<String,Object>    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月8日
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadImage(String token, @RequestParam("file") MultipartFile file) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		NetHttpTransport transport = new NetHttpTransport();
		HttpRequestFactory httpRequestFactory = transport.createRequestFactory();
		String fileName = file.getOriginalFilename();
		Map<String, String> parameters = Maps.newHashMap();
		parameters.put("token", token);
		// Add parameters
		MultipartContent content = new MultipartContent()
				.setMediaType(new HttpMediaType("multipart/form-data").setParameter("boundary", "__END_OF_PART__"));
		for (String name : parameters.keySet()) {
			MultipartContent.Part part = new MultipartContent.Part(
					new ByteArrayContent(null, parameters.get(name).getBytes()));
			part.setHeaders(
					new HttpHeaders().set("Content-Disposition", String.format("form-data; name=\"%s\"", name)));
			content.addPart(part);
		}
		// Add file
		HttpContent fileContent = new ByteArrayContent("image/jpeg", file.getBytes());

		MultipartContent.Part part = new MultipartContent.Part(fileContent);
		part.setHeaders(new HttpHeaders().set("Content-Disposition",
				String.format("form-data; name=\"file\"; filename=\"%s\"", fileName)));
		content.addPart(part);
		try {
			GenericUrl url = new GenericUrl(parameter.getImageUploadUrl());
			String response = httpRequestFactory.buildPostRequest(url, content).execute().parseAsString();
			System.out.println(response);
			ObjectMapper om = new ObjectMapper();
			result = om.readValue(response, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
