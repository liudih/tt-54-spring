package com.tomtop.ipn.services.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomtop.ipn.dao.IDropShippingDao;

@Service
public class DropShippingService {
	@Autowired
	private IDropShippingDao dao;

	public List<String> getOrderNumbersByID(String dropShippingID) {
		return this.dao.getOrderNumbersByID(dropShippingID);
	}

	/**
	 * 修改状态为已经支付完成
	 * 
	 * @param dropShippingID
	 * @return
	 */
	public boolean changeStatusToPaymentComplete(String dropShippingID) {
		if (StringUtils.isEmpty(dropShippingID)) {
			throw new NullPointerException("dropShippingID is null");
		}
		return this.dao.updatePaidToTrue(dropShippingID);
	}

}
