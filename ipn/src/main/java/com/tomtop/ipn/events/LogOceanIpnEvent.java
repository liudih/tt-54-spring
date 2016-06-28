package com.tomtop.ipn.events;

import java.util.Map;

/**
 * 
 * @author lijun
 *
 */
public class LogOceanIpnEvent {
	private final String orderNum;
	private final Map<String, String> content;
	private final String paymentID;

	public LogOceanIpnEvent(String orderNum, Map<String, String> content,
			String paymentID) {
		this.orderNum = orderNum;
		this.content = content;
		this.paymentID = paymentID;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public Map<String, String> getContent() {
		return content;
	}

	public String getPaymentID() {
		return paymentID;
	}

}
