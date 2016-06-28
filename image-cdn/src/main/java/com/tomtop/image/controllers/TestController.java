package com.tomtop.image.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

@RestController
@RequestMapping(value = "/test", method = RequestMethod.GET)
public class TestController {

	@RequestMapping()
	public Map index(){
		Map<String, String> map = Maps.newLinkedHashMap();
		map.put("test", "test");
		return map;
	}
}
