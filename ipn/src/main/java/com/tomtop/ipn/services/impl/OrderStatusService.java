package com.tomtop.ipn.services.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.tomtop.ipn.dao.IOrderStatusDao;
import com.tomtop.ipn.events.EventBroker;
import com.tomtop.ipn.events.PaymentCompletedEvent;
import com.tomtop.ipn.services.IOrderStatusService;

import entity.payment.Order;

/**
 * 
 * @author lijun
 *
 */
@Service
public class OrderStatusService implements IOrderStatusService {

	private static Logger Logger = LoggerFactory
			.getLogger(OrderStatusService.class);

	@Autowired
	IOrderStatusDao dao;

	@Autowired
	DropShippingService dsService;

	@Autowired
	EventBroker eventBroker;

	@Autowired
	TotalOrderService totalOrderService;

	private List<String> getAllOrderNums(String orderNum) {
		Assert.hasLength(orderNum, "orderNum is null");
		List<String> orderNums = null;
		// DropShipping 订单
		if (orderNum.endsWith("-DS")) {
			orderNums = dsService.getOrderNumbersByID(orderNum);
		} else if (orderNum.endsWith("-TT")) {
			orderNums = totalOrderService.getOrderNumsByTotalCID(orderNum);
			if (orderNums.isEmpty()) {
				Logger.debug(
						"PayPalPayment updateOrderStatus can not found order with orderID: {}",
						orderNum);
			}
		} else {
			orderNums = Lists.newLinkedList();
			orderNums.add(orderNum);
		}

		return orderNums;

	}

	@Override
	public boolean changeStatusToPaymentComplete(String orderNum,
			String transactionId, String receiverEmail) {
		List<String> orderNums = this.getAllOrderNums(orderNum);
		boolean result;
		Assert.notEmpty(orderNums);
		if (orderNums.size() == 1) {
			result = this.dao.changeStatus(orderNums.get(0), transactionId, 1,receiverEmail);
		} else {
			result = this.dao.changeStatus(orderNums, transactionId, 1,receiverEmail);
		}

		if (orderNum.endsWith("-DS")) {
			dsService.changeStatusToPaymentComplete(orderNum);
		}
		// 发支付完成事件
		PaymentCompletedEvent et = new PaymentCompletedEvent(orderNums,
				transactionId, null, null, null);
		eventBroker.post(et);
		return result;
	}

	@Override
	public boolean changeStatusToPaymentComplete(List<String> orderNums,
			String transactionId,String receiverEmail) {

		return this.dao.changeStatus(orderNums, transactionId, 1,receiverEmail);
	}

	@Override
	public boolean changeStatusToPaymentPending(String orderNum,
			String transactionId,String receiverEmail) {
		List<String> orderNums = this.getAllOrderNums(orderNum);

		Assert.notEmpty(orderNums);
		if (orderNums.size() == 1) {
			return this.dao.changeStatus(orderNums.get(0), transactionId, 2,receiverEmail);
		}

		return this.dao.changeStatus(orderNums, transactionId, 2,receiverEmail);
	}

	@Override
	public boolean changeStatusToPaymentPending(List<String> orderNums,
			String transactionId,String receiverEmail) {
		return this.dao.changeStatus(orderNums, transactionId, 2,receiverEmail);
	}

	@Override
	public Order getOrderByOrderNum(String orderNum) {
		if (StringUtils.isEmpty(orderNum)) {
			return null;
		}
		return this.dao.getOrderByOrderNum(orderNum);
	}

	@Override
	public String getAccount(Integer siteId, Double price, String paymentId) {
		return this.dao.getAccount(siteId, price, paymentId);
	}

	@Override
	public boolean addOrderStatusHistory(Integer iorderId, Integer status) {
		return this.dao.addOrderStatusHistory(iorderId, status);
	}

	@Override
	public boolean changeStatusToPaymentFailed(String orderNum,
			String transactionId,String receiverEmail) {
		List<String> orderNums = this.getAllOrderNums(orderNum);
		Assert.notEmpty(orderNums);
		if (orderNums.size() == 1) {
			return this.dao.changeStatus(orderNums.get(0), transactionId, 3,receiverEmail);
		}

		return this.dao.changeStatus(orderNums, transactionId, 3,receiverEmail);
	}

	@Override
	public boolean changeStatusToPaymentRefunded(String orderNum,
			String transactionId,String receiverEmail) {
		List<String> orderNums = this.getAllOrderNums(orderNum);
		Assert.notEmpty(orderNums);
		if (orderNums.size() == 1) {
			return this.dao.changeStatus(orderNums.get(0), transactionId, 4,receiverEmail);
		}
		return this.dao.changeStatus(orderNums, transactionId, 4,receiverEmail);
	}

}
