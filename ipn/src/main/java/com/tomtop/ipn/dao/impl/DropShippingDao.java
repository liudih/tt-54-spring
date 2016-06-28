package com.tomtop.ipn.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.util.Maps;
import com.tomtop.ipn.dao.IDropShippingDao;
import com.tomtop.ipn.mappers.order.DropShippingMapper;

@Service
public class DropShippingDao implements IDropShippingDao {
	private static Logger Logger = LoggerFactory
			.getLogger(DropShippingDao.class);
	
	@Autowired
	private DropShippingMapper mapper;

	@Override
	public List<String> getOrderNumbersByID(String dropShippingID) {
		return mapper.getOrderNumbersByID(dropShippingID);
	}

	@Override
	public boolean updatePaidToTrue(String dropShippingID) {
		try {
			Map<String,Object> paras = Maps.newLinkedHashMap();
			paras.put("paid", true);
			paras.put("dropShippingID", dropShippingID);
			mapper.updatePaid(paras);
		} catch (Exception e) {
			Logger.error("updatePaidToTrue error",e);
			return false;
		}
		return true;
	}
}
