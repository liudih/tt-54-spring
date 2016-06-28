package com.tomtop.base.controllers;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取系统时间
 * 
 * @author liulj
 *
 */
@RestController
public class SystemTimeController {

	@RequestMapping(method = RequestMethod.GET, value = "/base/systemTime/v1")
	public String getTime() {
		return DateFormatUtils.formatUTC(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
}
