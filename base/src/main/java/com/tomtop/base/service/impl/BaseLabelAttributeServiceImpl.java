package com.tomtop.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tomtop.base.mappers.BaseLabelAttributeMapper;
import com.tomtop.base.models.bo.BaseLabelAttributeBo;
import com.tomtop.base.models.dto.BaseLabelAttributeDto;
import com.tomtop.base.service.IBaseLabelAttributeService;
import com.tomtop.framework.core.utils.BeanUtils;

/**
 * 标签的属性
 * 
 * @author liulj
 *
 */
@Service("baseLabelAttributeService")
public class BaseLabelAttributeServiceImpl implements
		IBaseLabelAttributeService {

	@Autowired
	BaseLabelAttributeMapper mapper;

	@Override
	public List<BaseLabelAttributeBo> getListByClientLang(Integer client,
			Integer lang) {
		// TODO Auto-generated method stub
		List<BaseLabelAttributeDto> dto = mapper.getListByClientLang(client,
				lang);
		if (dto == null || dto.size() <= 0) {
			dto = mapper.getListByClientLang(client, 1);
		}
		return Lists.transform(dto,
				p -> BeanUtils.mapFromClass(p, BaseLabelAttributeBo.class));
	}

	@Override
	public List<BaseLabelAttributeBo> getListByCategoryId(Integer categoryId) {
		// TODO Auto-generated method stub
		return Lists.transform(mapper.getListByCategoryId(categoryId),
				p -> BeanUtils.mapFromClass(p, BaseLabelAttributeBo.class));
	}

	@Override
	public List<BaseLabelAttributeBo> getListByKey(String key) {
		// TODO Auto-generated method stub
		return Lists.transform(mapper.getListByKey(key),
				p -> BeanUtils.mapFromClass(p, BaseLabelAttributeBo.class));
	}
}