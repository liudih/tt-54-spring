package com.tomtop.ipn.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomtop.ipn.mappers.base.BaseMapper;
import com.tomtop.ipn.services.IBaseService;

import entity.payment.Currency;
import entity.payment.CurrencyRate;
import entity.payment.SystemParameter;

@Service
public class BaseService implements IBaseService {

	@Autowired
	BaseMapper mapper;

	@Override
	public double getRate(String ccy) {
		CurrencyRate rate = mapper.findLatestRateByCode(ccy, new Date());
		if (rate == null) {
			Currency currency = mapper.getCurrencyByCode(ccy);
			if (currency == null) {
				throw new NullPointerException("Currency Unavailable");
			}
			return currency.getFexchangerate();
		}
		return rate.getFexchangerate();

	}

	@Override
	public double exchange(double money, String originalCCY, String targetCCY) {

		if (originalCCY.equals(targetCCY)) {
			return money;
		}
		return money * (getRate(targetCCY) / getRate(originalCCY));
	}

	@Override
	public SystemParameter getSysPara(String key, int siteId) {
		return this.mapper.getParameterValue(key, siteId);
	}
}
