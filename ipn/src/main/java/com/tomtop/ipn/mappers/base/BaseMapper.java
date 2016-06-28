package com.tomtop.ipn.mappers.base;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import entity.payment.Currency;
import entity.payment.CurrencyRate;
import entity.payment.SystemParameter;

public interface BaseMapper {

	@Select("select * from t_currency_rate "
			+ "where buse=true and ccode = #{0} and dcreatedate < #{1} "
			+ "order by dcreatedate limit 1")
	CurrencyRate findLatestRateByCode(String code, Date now);

	@Select("select * from t_currency where ccode = #{0} ")
	Currency getCurrencyByCode(String code);

	@Select("select * from t_system_parameter where iwebsiteid=${siteId} "
			+ "and (ilanguageid=0 or ilanguageid is null) "
			+ "and cparameterkey=#{key} limit 1")
	SystemParameter getParameterValue(@Param("key")String key,@Param("siteId") int siteId);
}
