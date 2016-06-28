package com.tomtop.image.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.util.Maps;
import com.tomtop.image.dao.IProductImageDao;
import com.tomtop.image.models.dto.ProductImage;
import com.tomtop.image.models.dto.ProductUrl;
import com.tomtop.image.models.dto.Range;
import com.tomtop.product.mappers.ProductImageMapper;

@Service
public class ProductImageDao implements IProductImageDao {

	@Autowired
	ProductImageMapper mapper;

	@Override
	public List<ProductImage> select(int start, int end) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("start", start);
		paras.put("end", end);
		return mapper.select(paras);
	}

	@Override
	public Integer getMaxId() {
		return this.mapper.getMaxId();
	}

	@Override
	public boolean changeFetchToTrueById(int id) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("id", id);
		try {
			mapper.update(paras);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean changeFetchToTrueById(int id, String path) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("id", id);
		paras.put("path", path);
		try {
			mapper.update(paras);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean changeHttpStatusCode404ById(int id) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("id", id);
		paras.put("httpstatuscode", 404);
		try {
			mapper.update(paras);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Integer getMinId() {
		return this.mapper.getMinId();
	}

	@Override
	public boolean changeHttpStatusCodeById(int id, int statusCode) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("id", id);
		paras.put("httpstatuscode", statusCode);
		try {
			mapper.update(paras);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public List<ProductUrl> getProductUrl(int fromId, int size) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("fromId", fromId);
		paras.put("size", size);
		return this.mapper.selectProductUrl(paras);
	}

	@Override
	public List<ProductImage> selectForChicuu(int start, int end, int categoryId) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("start", start);
		paras.put("end", end);
		paras.put("site", 10);
		paras.put("categoryId", categoryId);
		return mapper.selectBySiteCategory(paras);
	}

	@Override
	public Range getMaxMinForChicuu(int categoryId) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("site", 10);
		paras.put("categoryId", categoryId);
		return mapper.selectMaxMinBySiteCategory(paras);
	}

	@Override
	public Range getMaxMinForChicuu(String categoryIds) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("site", 10);
		paras.put("categoryId", categoryIds);
		return mapper.selectMaxMinBySiteCategory(paras);
	}

	@Override
	public List<ProductImage> selectByShapeForChicuu(int start, int end,
			int shape) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("start", start);
		paras.put("end", end);
		paras.put("site", 10);
		paras.put("shape", shape);
		return mapper.selectBySiteCategory(paras);
	}

	@Override
	public Range getMaxMinByShapeForChicuu(int shape) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("site", 10);
		paras.put("shape", shape);
		return mapper.selectMaxMinBySiteCategory(paras);
	}

	@Override
	public boolean changeClipToTrueById(int id, Integer statusCode) {
		Map<String, Object> paras = Maps.newLinkedHashMap();
		paras.put("id", id);
		if (statusCode != null) {
			paras.put("httpstatuscode", statusCode);
		}
		try {
			mapper.updateClip(paras);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
