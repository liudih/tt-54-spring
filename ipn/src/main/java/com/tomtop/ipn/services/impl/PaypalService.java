package com.tomtop.ipn.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomtop.ipn.mappers.paypal.PaypalReturnLogMapper;

import entity.payment.PaypalLog;

@Service
public class PaypalService {

	@Autowired
	PaypalReturnLogMapper paypalReturnLogMapper;

	/**
	 * @author lijun
	 * @param iwebsiteid
	 * @param orderNum
	 * @param content
	 *            日志内容
	 * @param transactionId
	 *            交易号
	 */
	public void insertLog(String orderNum, String content, String transactionId) {
		PaypalLog log = new PaypalLog();
		log.setCorderid(orderNum);
		log.setCcontent(content);
		log.setCtransactionid(transactionId);
		paypalReturnLogMapper.insert(log);
	}

}
