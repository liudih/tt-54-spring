package com.tomtop.ipn.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.util.Maps;
import com.tomtop.ipn.dao.IOrderStatusDao;
import com.tomtop.ipn.mappers.order.OrderMapper;

import entity.payment.Order;

/**
 * 
 * @author lijun
 *
 */
@Service
public class OrderStatusDao implements IOrderStatusDao {

	private static final int BATCH_SIZE = 50;

	private static Logger Logger = LoggerFactory
			.getLogger(OrderStatusDao.class);

	@Autowired
	private OrderMapper mapper;

	@Override
	public boolean changeStatus(String orderNum, String transactionId,
			int paymentStatus,String receiverEmail) {
		if (StringUtils.isEmpty(orderNum)) {
			throw new NullPointerException("orderNum is null");
		}
		if (StringUtils.isEmpty(transactionId)) {
			throw new NullPointerException("transactionId is null");
		}

		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("orderNum", orderNum);
		paras.put("transactionId", transactionId);
		paras.put("receiverEmail", receiverEmail);
		if (paymentStatus == 1) {
			paras.put("orderStatus", 2);
			paras.put("paymentStatus", 1);
		} else if (paymentStatus == 2) {
			paras.put("orderStatus", 1);
			paras.put("paymentStatus", 2);
		} else if (paymentStatus == 3) {
			paras.put("orderStatus", 1);
			paras.put("paymentStatus", 3);
		} else if (paymentStatus == 4) {
			paras.put("orderStatus", 8);
			paras.put("paymentStatus", 4);
		} else {
			paras.put("paymentStatus", paymentStatus);
		}

		try {
			this.mapper.update(paras);
		} catch (Exception e) {
			Logger.error("changeStatus order:{} error", orderNum, e);
			return false;
		}
		return true;
	}

	@Override
	public boolean changeStatus(List<String> orderNums, String transactionId,
			int paymentStatus,String receiverEmail) {

		try {
			// 批量更新
			int loopSize = (int) Math.ceil(orderNums.size()
					/ (double) BATCH_SIZE);
			for (int i = 0; i < loopSize; i++) {
				StringBuilder sb = new StringBuilder();
				for (int j = BATCH_SIZE * i; j < BATCH_SIZE
						&& j < orderNums.size(); j++) {
					if (sb.length() == 0) {
						sb.append("'");
						sb.append(orderNums.get(j));
						sb.append("'");
					} else {
						sb.append(",'");
						sb.append(orderNums.get(j));
						sb.append("'");
					}
				}

				Map<String, Object> paras = Maps.newLinkedHashMap();
				paras.put("orderNums", sb.toString());
				paras.put("transactionId", transactionId);
				paras.put("receiverEmail", receiverEmail);
				if (paymentStatus == 1) {
					paras.put("orderStatus", 2);
					paras.put("paymentStatus", 1);
				} else if (paymentStatus == 2) {
					paras.put("orderStatus", 1);
					paras.put("paymentStatus", 2);
				} else if (paymentStatus == 3) {
					paras.put("orderStatus", 1);
					paras.put("paymentStatus", 3);
				} else if (paymentStatus == 4) {
					paras.put("orderStatus", 8);
					paras.put("paymentStatus", 4);
				} else {
					paras.put("paymentStatus", paymentStatus);
				}

				this.mapper.update(paras);
			}
		} catch (Exception e) {
			Logger.error("batch update order error", e);
			return false;
		}

		return true;
	}

	@Override
	public Order getOrderByOrderNum(String orderNum) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("orderNum", orderNum);
		return this.mapper.select(paras);
	}

	@Override
	public String getAccount(Integer siteId, Double price, String paymentId) {
		return this.mapper.getAccount(siteId, price, paymentId);
	}

	@Override
	public boolean addOrderStatusHistory(Integer iorderId, Integer status) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("orderId", iorderId);
		paras.put("status", status);
		try {
			this.mapper.insertOrderStatusHistory(paras);
		} catch (Exception e) {
			Logger.error("addOrderStatusHistory error", e);
			return false;
		}
		return true;
	}
}
