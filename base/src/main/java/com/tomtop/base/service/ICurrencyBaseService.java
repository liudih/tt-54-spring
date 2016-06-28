package com.tomtop.base.service;

import java.util.List;

import com.tomtop.base.models.bo.CurrencyBo;
import com.tomtop.base.models.dto.CurrencyBase;

public interface ICurrencyBaseService {

	public List<CurrencyBo> getCurrencyList();
	
	public List<CurrencyBo> getCurrencyList(CurrencyBase cb);
	
	public CurrencyBo getCurrencyById(Integer id);
	
	public Double getRate(String ccy);
	
}
