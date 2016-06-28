package com.tomtop.ipn.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomtop.ipn.mappers.order.PaymentCallbackMapper;

import entity.payment.paymentCallback;

@Service
public class PaymentCallbackService {

	@Autowired
	PaymentCallbackMapper mapper;

	public boolean insert(String orderNumber, String content, String paymentID) {

		paymentCallback log = new paymentCallback();
		log.setCordernumber(orderNumber);
		log.setCcontent(content);
		log.setCpaymentid(paymentID);
		return mapper.insert(log) > 0 ? true
				: false;
	}

}
