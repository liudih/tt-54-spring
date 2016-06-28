package com.tomtop.ipn.handlers;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.google.common.eventbus.Subscribe;
import com.tomtop.ipn.events.LogPaypalIpnEvent;
import com.tomtop.ipn.services.impl.PaypalService;

/**
 * 记录ipn日志 该类已经丢弃
 * 
 * @see LogEventHandler
 * @author lijun
 *
 */

public class LogPaypalIpnEventHandler implements IEventHandler {

	@Autowired
	PaypalService service;

	@Subscribe
	public void log(LogPaypalIpnEvent payload) {
		JSONObject contentJson = new JSONObject();
		contentJson.putAll(payload.getContent());
		service.insertLog(payload.getOrderNum(), contentJson.toJSONString(),
				payload.getTransactionId());
	}
}
