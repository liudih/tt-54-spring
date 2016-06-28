package com.tomtop.ipn.mappers.order;

import java.util.Map;

import org.apache.ibatis.annotations.Select;

import entity.payment.Order;

public interface OrderMapper {

	public void update(Map<String, Object> paras);

	public Order select(Map<String, Object> paras);

	@Select("select cbusiness from t_payment_account where iwebsiteid = #{0} and "
			+ "((fbeginprice is null or fbeginprice <= #{1}) and (fendprice is null or fendprice > #{1})) "
			+ "and cpaymentid = #{2} limit 1")
	String getAccount(Integer siteId, Double price, String paymentId);

	public void insertOrderStatusHistory(Map<String, Object> paras);
}
