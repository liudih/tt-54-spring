package com.tomtop.image.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.util.Maps;
import com.tomtop.image.dao.IFileRouteDao;
import com.tomtop.image.mappers.FileRouteMapper;
import com.tomtop.image.models.dto.Image;
import com.tomtop.image.models.dto.Range;

@Service
public class FileRouteDao implements IFileRouteDao {

	@Autowired
	FileRouteMapper mapper;

	@Override
	public boolean insert(Map<String, Object> paras) {
		return this.mapper.insert(paras) > 0 ? true : false;
	}

	@Override
	public List<Image> select(Map<String, Object> paras) {
		return this.mapper.select(paras);
	}

	@Override
	public boolean update(Map<String, Object> paras) {
		return this.mapper.update(paras) > 0 ? true : false;
	}

	/**
	 * 该方法是临时方法,为了跑压缩图用
	 * 
	 * @param paras
	 * @return
	 */
	public List<Image> selectXy(Map<String, Object> paras) {
		return this.mapper.selectXy(paras);
	}

	@Override
	public int getOriginalMaxId() {
		return this.mapper.getMaxId();
	}

	@Override
	public int count(Map<String, Object> paras) {
		return this.mapper.count(paras);
	}

	@Override
	public List<Image> selectRoute(Map<String, Object> paras) {
		return this.mapper.selectRoute(paras);
	}

	@Override
	public Range getMaxMin(Map<String, Object> paras) {
		return this.mapper.selectMaxMin(paras);
	}

	@Override
	public boolean updateBamazon(List<Integer> ids) {

		if (ids == null || ids.size() == 0) {
			return true;
		}

		StringBuilder succeedIid = new StringBuilder(ids.size());

		ids.forEach(id -> {
			if (succeedIid.length() == 0) {
				succeedIid.append(id);
			} else {
				succeedIid.append(",");
				succeedIid.append(id);
			}
		});

		Map<String, Object> paras = Maps.newHashMap();
		paras.put("ids", succeedIid.toString());
		return this.mapper.updateBamazon(paras) > 0 ? true : false;
	}

	@Override
	public boolean updateBamazon(int id) {
		Map<String, Object> paras = Maps.newHashMap();
		paras.put("ids", id);
		return this.mapper.updateBamazon(paras) > 0 ? true : false;
	}
}
