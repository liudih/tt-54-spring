package com.tomtop.ipn.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.eventbus.Subscribe;
import com.tomtop.ipn.events.LogOceanIpnEvent;
import com.tomtop.ipn.services.impl.PaymentCallbackService;

/**
 * 记录ipn日志 该类已经丢弃
 * 
 * @see LogEventHandler
 * @author lijun
 *
 */

public class LogOceanIpnEventHandler implements IEventHandler {

	@Autowired
	PaymentCallbackService service;

	@Subscribe
	public void log(LogOceanIpnEvent payload) {
		JSONObject contentJson = new JSONObject();
		contentJson.putAll(payload.getContent());
		service.insert(payload.getOrderNum(), contentJson.toJSONString(),
				payload.getPaymentID());
	}
}
