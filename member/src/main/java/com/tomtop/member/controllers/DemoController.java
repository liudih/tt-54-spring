package com.tomtop.member.controllers;

import java.util.Map;

import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

/**
 * spring boot mvc demo
 */
@RestController
@RequestMapping("/")
public class DemoController{

	
	MapperFactoryBean<?> a;

	MapperScannerConfigurer b;
	
	DataSourceAutoConfiguration c;
	
	@RequestMapping("v1/jjjj")
	public Map<String,String> index() {
		Map<String,String> map = Maps.newLinkedHashMap();
		map.put("test1", "test1");
		map.put("test2", "test2");
		return map;
	}
	
	@RequestMapping("v2/jjjj")
	public Map<String,String> indexV2() {
		Map<String,String> map = Maps.newLinkedHashMap();
		map.put("test1", "test1");
		map.put("test2", "test2");
		return map;
	}

	@RequestMapping()
	public Map<String,String> indexV3() {
		Map<String,String> map = Maps.newLinkedHashMap();
		map.put("member", "This is Spring member project");
		return map;
	}
}
