package com.tomtop.ipn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tomtop.ipn.events.EventBroker;

@RequestMapping("/test")
@RestController()
public class TestController {

	@Autowired
	EventBroker eventBroker;

	@RequestMapping()
	public String test() {
		return "test";
	}
}
