package com.tomtop.image.services;

import java.util.List;

import com.tomtop.image.models.dto.ProductImage;
import com.tomtop.image.models.dto.ProductUrl;
import com.tomtop.image.models.dto.Range;

public interface IProductImageService {

	/**
	 * 获取iid从start到end的图片数据
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ProductImage> get(int start, int end);

	public List<ProductImage> selectForChicuu(int start, int end, int categoryId);

	/**
	 * 
	 * @param start
	 * @param end
	 * @param categoryIds
	 *            (比如：1,2,3...)
	 * @return
	 */
	public List<ProductImage> selectByShapeForChicuu(int start, int end,
			Shape shape);

	public Integer getMaxId();

	public boolean changeFetchToTrueById(int id);

	/**
	 * 该方法会更新 bfetch cimageurl 字段
	 * 
	 * @param id
	 * @param path
	 * @return
	 */
	public boolean changeFetchToTrueById(int id, String path);

	public boolean changeHttpStatusCode404ById(int id);

	public Integer getMinId();

	public boolean changeHttpStatusCodeById(int id, int statusCode);

	public List<ProductUrl> getProductUrl(int fromId, int size);

	public Range getMaxMinForChicuu(int categoryId);

	/**
	 * 
	 * @param categoryIds
	 *            (比如: 1,2,3...)
	 * @return
	 */
	public Range getMaxMinForChicuu(String categoryIds);

	public Range getMaxMinForChicuu(Shape shape);

	public boolean changeClipToTrueById(int id);

	public boolean changeClipToTrueById(int id, int statusCode);

	public static enum Shape {
		oblong, square
	}
}
