package com.tomtop.product.mappers;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CategoryProductRecommendMapper {

	@Select("select sequence from t_category_product_recommend "
			+ "where status=#{status} and iwebsiteid=#{siteid} and categoryid=#{categoryid} and sku=#{sku} limit 1")
	Integer getObj(@Param("status") Integer status,
			@Param("categoryid") Integer categoryid, @Param("sku") String sku,
			@Param("siteid") Integer siteid);
}
