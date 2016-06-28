package com.tomtop.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomtop.base.models.dto.VisitLogDto;
import com.tomtop.base.service.IVisitLogService;
import com.tomtop.base.tracking.mappers.VisitLogMapper;

/**
 * 访问记录
 * 
 * @author liulj
 *
 */
@Service("visitLogService")
public class VisitLogServiceImpl implements IVisitLogService {

	@Autowired
	private VisitLogMapper mapper;

	@Override
	public int insert(VisitLogDto visitLog) {
		// TODO Auto-generated method stub
		return mapper.insert(visitLog);
	}
}
