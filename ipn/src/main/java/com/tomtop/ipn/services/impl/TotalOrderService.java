package com.tomtop.ipn.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomtop.ipn.mappers.order.TotalOrderMapper;

@Service
public class TotalOrderService {

	@Autowired
	TotalOrderMapper mapper;

	public List<String> getOrderNumsByTotalCID(String cid) {
		return this.mapper.getOrderNumsByTotalID(cid);
	}

}
