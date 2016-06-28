package com.tomtop.ipn.mappers.paypal;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import entity.payment.PaypalLog;

public interface PaypalReturnLogMapper {
	@Insert("insert into t_payment_paypal_return_log(corderid,ccontent,ctransactionid) values (#{log.corderid},#{log.ccontent},#{log.ctransactionid})")
	int insert(@Param("log") PaypalLog log);

}
