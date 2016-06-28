package com.tomtop.ipn.dao;

import java.util.List;

import entity.payment.Order;

/**
 * 订单状态DAO
 * 
 * @author lijun
 *
 */
public interface IOrderStatusDao {

	/**
	 * 改变订单状态为到账状态
	 * 
	 * @param orderNum
	 *            订单号
	 * @param transactionId
	 *            交易id
	 * @param paymentStatus
	 *            支付状态状态 0:未支付 1:已经支付 2:pending
	 * @param receiverEmail
	 *            收款账号
	 * @return 跟新是否成功
	 */
	public boolean changeStatus(String orderNum, String transactionId,
			int paymentStatus, String receiverEmail);

	/**
	 * 批量更新操作
	 * 
	 * @param orderNums
	 * @param transactionId
	 * @param paymentStatus
	 * @param receiverEmail
	 *            收款账号
	 * @return
	 */
	public boolean changeStatus(List<String> orderNums, String transactionId,
			int paymentStatus, String receiverEmail);

	/**
	 * 通过订单号获取订单
	 * 
	 * @param orderNum
	 * @return
	 */
	public Order getOrderByOrderNum(String orderNum);

	public String getAccount(Integer siteId, Double price, String paymentId);

	public boolean addOrderStatusHistory(Integer iorderId, Integer status);
}
