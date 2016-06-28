package com.tomtop.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tomtop.base.mappers.CurrencyBaseMapper;
import com.tomtop.base.models.bo.CurrencyBo;
import com.tomtop.base.models.dto.CurrencyBase;
import com.tomtop.base.service.ICurrencyBaseService;
import com.tomtop.base.utils.CommonUtils;
import com.tomtop.framework.core.utils.BeanUtils;

@Service
public class CurrencyBaseServiceImpl implements ICurrencyBaseService {

	@Autowired
	CurrencyBaseMapper currencyBaseMapper;
	/**
	 * 获取所有货币信息
	 * 
	 * @return List<CurrencyBo>
	 * @author renyy
	 *
	 */
	@Override
	public List<CurrencyBo> getCurrencyList() {
		List<CurrencyBase> cbList = currencyBaseMapper.getAllCurrencyBase();
		List<CurrencyBo> cboList = Lists.transform(cbList,  cb -> {
			CurrencyBo cbo = new CurrencyBo();
			BeanUtils.copyPropertys(cb, cbo);
				return cbo;
			});
		return cboList;
	}
	
	/**
	 * 获取货币信息列表
	 * 
	 * 	@param CurrencyBo
	 * 			根据设定的对象的条件筛选
	 * 
	 * @return List<CurrencyBo>
	 * @author renyy
	 *
	 */
	@Override
	public List<CurrencyBo> getCurrencyList(CurrencyBase cb) {
		List<CurrencyBase> cbList = currencyBaseMapper.getCurrencyBase(cb);
		List<CurrencyBo> cboList = Lists.transform(cbList,  cbl -> {
			CurrencyBo cbo = new CurrencyBo();
			BeanUtils.copyPropertys(cbl, cbo);
				return cbo;
			});
		return cboList;
	}

	/**
	 * 获取货币信息
	 * 
	 * 	@param id
	 * 		主键Id
	 * 
	 * @return CurrencyBo
	 * @author renyy
	 *
	 */
	@Override
	public CurrencyBo getCurrencyById(Integer id) {
		CurrencyBase cb = new CurrencyBase();
		cb.setId(id);
		List<CurrencyBase> cbList = currencyBaseMapper.getCurrencyBase(cb);
		CurrencyBo cbo = new CurrencyBo();
		if (cbList != null && cbList.size() > 0) {
			cb = cbList.get(0);
			BeanUtils.copyPropertys(cb, cbo);
			cbo.setRes(CommonUtils.SUCCESS_RES);
		}else{
			cbo.setRes(CommonUtils.NOT_DATA_RES);
			cbo.setMsg(CommonUtils.NOT_DATA_MSG_RES);
		}
		return cbo;
	}
	
	/**
	 * 获取汇率
	 * 
	 * 	@param code
	 * 		币种标识
	 * 
	 * @return Double
	 * @author renyy
	 *
	 */
	@Override
	public Double getRate(String ccy) {
			CurrencyBase currency = currencyBaseMapper.getCurrencyBaseByCode(ccy);
			if (currency == null) {
				throw new RuntimeException("Currency Unavailable");
			}
			return currency.getCurrentRate();
	}
}
