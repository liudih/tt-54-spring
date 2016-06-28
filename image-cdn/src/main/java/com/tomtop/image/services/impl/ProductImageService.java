package com.tomtop.image.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomtop.image.dao.IProductImageDao;
import com.tomtop.image.models.dto.ProductImage;
import com.tomtop.image.models.dto.ProductUrl;
import com.tomtop.image.models.dto.Range;
import com.tomtop.image.services.IProductImageService;

@Service
public class ProductImageService implements IProductImageService {

	@Autowired
	IProductImageDao dao;

	@Override
	public List<ProductImage> get(int start, int end) {
		return this.dao.select(start, end);
	}

	@Override
	public Integer getMaxId() {
		return this.dao.getMaxId();
	}

	@Override
	public boolean changeFetchToTrueById(int id) {
		return this.dao.changeFetchToTrueById(id);
	}

	@Override
	public boolean changeFetchToTrueById(int id, String path) {
		return this.dao.changeFetchToTrueById(id, path);
	}

	@Override
	public boolean changeHttpStatusCode404ById(int id) {
		return this.dao.changeHttpStatusCode404ById(id);
	}

	@Override
	public Integer getMinId() {
		return this.dao.getMinId();
	}

	@Override
	public boolean changeHttpStatusCodeById(int id, int statusCode) {
		return this.dao.changeHttpStatusCodeById(id, statusCode);
	}

	@Override
	public List<ProductUrl> getProductUrl(int fromId, int size) {
		return this.dao.getProductUrl(fromId, size);
	}

	@Override
	public List<ProductImage> selectForChicuu(int start, int end, int categoryId) {
		return this.dao.selectForChicuu(start, end, categoryId);
	}

	@Override
	public Range getMaxMinForChicuu(int categoryId) {
		return this.dao.getMaxMinForChicuu(categoryId);
	}

	@Override
	public Range getMaxMinForChicuu(String categoryIds) {
		return this.dao.getMaxMinForChicuu(categoryIds);
	}

	@Override
	public List<ProductImage> selectByShapeForChicuu(int start, int end,
			Shape shape) {
		int ishape = 1;
		if (Shape.square == shape) {
			ishape = 0;
		}
		return this.dao.selectByShapeForChicuu(start, end, ishape);
	}

	@Override
	public Range getMaxMinForChicuu(Shape shape) {
		int ishape = 1;
		if (Shape.square == shape) {
			ishape = 0;
		}
		return this.dao.getMaxMinByShapeForChicuu(ishape);
	}

	@Override
	public boolean changeClipToTrueById(int id) {
		return this.dao.changeClipToTrueById(id, null);
	}

	@Override
	public boolean changeClipToTrueById(int id, int statusCode) {
		return this.dao.changeClipToTrueById(id, statusCode);
	}

}
