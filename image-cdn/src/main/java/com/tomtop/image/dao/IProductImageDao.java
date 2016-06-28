package com.tomtop.image.dao;

import java.util.List;

import com.tomtop.image.models.dto.ProductImage;
import com.tomtop.image.models.dto.ProductUrl;
import com.tomtop.image.models.dto.Range;

public interface IProductImageDao {

	List<ProductImage> select(int start, int end);

	List<ProductImage> selectForChicuu(int start, int end, int categoryId);

	/**
	 * 
	 * @param start
	 * @param end
	 * @param categoryIds
	 *            (比如：1,2,3...)
	 * @return
	 */
	List<ProductImage> selectByShapeForChicuu(int start, int end, int shape);

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

	public Range getMaxMinForChicuu(String categoryIds);

	public Range getMaxMinByShapeForChicuu(int shape);

	public boolean changeClipToTrueById(int id,Integer statusCode);
}
