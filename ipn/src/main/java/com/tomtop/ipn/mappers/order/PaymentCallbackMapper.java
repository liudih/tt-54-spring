package com.tomtop.ipn.mappers.order;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import entity.payment.paymentCallback;

public interface PaymentCallbackMapper {
	@Insert("insert into t_payment_callback (cordernumber, ccontent, cpaymentid) "
			+ "values (#{log.cordernumber}, #{log.ccontent}, #{log.cpaymentid})")
	int insert(@Param("log")paymentCallback log);

}
