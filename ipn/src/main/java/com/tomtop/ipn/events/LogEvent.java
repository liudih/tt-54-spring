package com.tomtop.ipn.events;

import org.springframework.util.Assert;

/**
 * 日志记录事件
 * 
 * @author lijun
 *
 */
public class LogEvent {
	private final String orderNum;
	private final String content;
	private final String transactionId;

	public LogEvent(String orderNum, String content, String transactionId) {
		Assert.hasLength(orderNum);
		Assert.hasLength(content);
		this.orderNum = orderNum;
		this.content = content;
		this.transactionId = transactionId;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public String getContent() {
		return content;
	}

	public String getTransactionId() {
		return transactionId;
	}

}
