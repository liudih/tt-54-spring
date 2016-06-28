package com.tomtop.ipn.events;

import java.util.List;

/**
 * 订单交易完成事件
 * 
 * @author lijun
 *
 */
public class PaymentCompletedEvent {
	// 订单号
	//final String orderNum;
	// 交易id
	final String transactionId;
	// 接受付款的账号
	final String receiverAccount;
	// 实付金额,会用该参数和数据库对比,如果不相等则不修改订单状态
	final String amt;
	// 支付工具(eg. paypal)
	final String paymentId;

	final List<String> orderNums;
	
	public PaymentCompletedEvent(List<String> orderNums, String transactionId,
			String receiverAccount, String amt, String paymentId) {
		this.orderNums = orderNums;
		this.transactionId = transactionId;
		this.receiverAccount = receiverAccount;
		this.amt = amt;
		this.paymentId = paymentId;
	}
	
	public String getPaymentId() {
		return paymentId;
	}

	public List<String> getOrderNums() {
		return orderNums;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getReceiverAccount() {
		return receiverAccount;
	}

	public String getAmt() {
		return amt;
	}

}
