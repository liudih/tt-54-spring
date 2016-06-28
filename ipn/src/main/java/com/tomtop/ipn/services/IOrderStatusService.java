package com.tomtop.ipn.services;

import java.util.List;

import entity.payment.Order;

/**
 * 订单支付状态服务类
 * 
 * @author lijun
 *
 */
public interface IOrderStatusService {

	/**
	 * 改变订单状态为到账状态
	 * 
	 * @param orderNum
	 *            订单号
	 * @param transactionId
	 *            交易id
	 * @param receiverEmail
	 *            收款账号
	 * @return 操作是否成功
	 */
	public boolean changeStatusToPaymentComplete(String orderNum,
			String transactionId, String receiverEmail);

	/**
	 * 批量更新订单状态,当订单是dropship订单等才会调用该方法
	 * 
	 * @param orderNums
	 *            批量订单号
	 * @param transactionId
	 *            交易id
	 * @param receiverEmail
	 *            收款账号
	 * @return
	 */
	public boolean changeStatusToPaymentComplete(List<String> orderNums,
			String transactionId, String receiverEmail);

	/**
	 * 改变订单状态为pending
	 * 
	 * @param orderNum
	 *            订单号
	 * @param transactionId
	 *            交易id
	 * @return 操作是否成功
	 */
	public boolean changeStatusToPaymentPending(String orderNum,
			String transactionId, String receiverEmail);

	/**
	 * 批量更新订单状态,当订单是dropship订单等才会调用该方法
	 * 
	 * @param orderNums
	 *            批量订单号
	 * @param transactionId
	 *            交易id
	 * @return
	 */
	public boolean changeStatusToPaymentPending(List<String> orderNums,
			String transactionId, String receiverEmail);

	/**
	 * 通过订单号获取订单
	 * 
	 * @param orderNum
	 */
	public Order getOrderByOrderNum(String orderNum);

	public String getAccount(Integer siteId, Double price, String paymentId);

	public boolean addOrderStatusHistory(Integer iorderId, Integer status);

	public boolean changeStatusToPaymentFailed(String orderNum,
			String transactionId, String receiverEmail);

	/**
	 * 退款
	 * 
	 * @param orderNum
	 * @param transactionId
	 * @return
	 */
	public boolean changeStatusToPaymentRefunded(String orderNum,
			String transactionId, String receiverEmail);

}
