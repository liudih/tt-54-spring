package com.tomtop.product.mappers;

import java.util.List;
import java.util.Map;

import com.tomtop.image.models.dto.ProductImage;
import com.tomtop.image.models.dto.ProductUrl;
import com.tomtop.image.models.dto.Range;

public interface ProductImageMapper {

	List<ProductImage> select(Map<String, Object> paras);

	public Integer getMaxId();

	public void update(Map<String, Object> paras);

	public Integer getMinId();

	public List<ProductImage> selectBySiteCategory(Map<String, Object> paras);

	public Range selectMaxMinBySiteCategory(Map<String, Object> paras);

	public List<ProductUrl> selectProductUrl(Map<String, Object> paras);

	public void updateClip(Map<String, Object> paras);

}
