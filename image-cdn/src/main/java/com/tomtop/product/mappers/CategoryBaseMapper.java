package com.tomtop.product.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.tomtop.image.models.dto.CategoryBase;

/**
 * 
 * @author lijun
 *
 */
public interface CategoryBaseMapper {
	@Select("select a.icategoryid,a.ishape from t_category_website a where a.ishape is not null and iwebsiteid=10")
	public List<CategoryBase> getShapes();
}
