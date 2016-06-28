package com.tomtop.ipn.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.Subscribe;
import com.tomtop.ipn.Application;
import com.tomtop.ipn.events.LogEvent;
import com.tomtop.ipn.services.impl.PaypalService;

/**
 * 记录ipn日志
 * 
 * @author lijun
 *
 */
@Service
public class LogEventHandler implements IEventHandler {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LogEventHandler.class);

	@Autowired
	PaypalService service;

	@Subscribe
	public void log(LogEvent payload) {
		service.insertLog(payload.getOrderNum(), payload.getContent(),
				payload.getTransactionId());
	}
}
