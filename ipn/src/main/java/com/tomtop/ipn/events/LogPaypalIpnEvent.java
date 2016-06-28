package com.tomtop.ipn.events;

import java.util.Map;

import org.springframework.util.Assert;

/**
 * 
 * @author lijun
 *
 */
public class LogPaypalIpnEvent {
	private final String orderNum;
	private final Map<String, String> content;
	private final String transactionId;

	public LogPaypalIpnEvent(String orderNum, Map<String, String> content,
			String transactionId) {
		Assert.hasLength(orderNum);
		this.orderNum = orderNum;
		this.content = content;
		this.transactionId = transactionId;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public Map<String, String> getContent() {
		return content;
	}

	public String getTransactionId() {
		return transactionId;
	}

}
