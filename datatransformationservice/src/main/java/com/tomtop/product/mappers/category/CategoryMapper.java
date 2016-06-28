package com.tomtop.product.mappers.category;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.tomtop.product.models.dto.category.CategoryLable;

public interface CategoryMapper {

	@Select("select cn.icategoryid,cn.cname,cba.ilevel,cba.iparentid,cn.ilanguageid "
			+ "from t_category_name cn INNER JOIN t_category_website cba "
			+ "on cn.icategoryid=cba.icategoryid where cn.ilanguageid=#{1} and "
			+ "cba.iparentid=#{0} and cba.iwebsiteid=#{2}")
	public List<CategoryLable> getCategoryLableByParenId(Integer parenId,
			Integer langId, Integer client);

	@Select("select cn.icategoryid,cn.cname,cba.ilevel,cba.iparentid "
			+ "from t_category_name cn INNER JOIN t_category_base cba "
			+ "on cn.icategoryid=cba.iid where cn.ilanguageid=#{1} and "
			+ "cn.icategoryid=#{0}")
	public CategoryLable getCategoryLable(Integer categoryId, Integer langId);

	@Select("select cn.icategoryid,cn.cname,cba.ilevel from t_product_category_mapper pcm "
			+ "LEFT JOIN t_category_name cn on pcm.icategory=cn.icategoryid "
			+ "LEFT JOIN t_category_base cba on cn.icategoryid=cba.iid "
			+ "where cn.ilanguageid=#{1} and pcm.clistingid=#{0}")
	public List<CategoryLable> getCategoryLableByListingId(String listingId,
			Integer langId);

	@Select("select cn.icategoryid,cn.cname,cba.cpath,cba.ilevel,cba.iparentid,cn.ilanguageid from t_product_category_mapper pcm "
			+"LEFT JOIN t_category_name cn on pcm.icategory=cn.icategoryid "
			+"LEFT JOIN t_category_website cba on cn.icategoryid=cba.icategoryid "
			+"where pcm.clistingid=#{0} and cn.ilanguageid=#{1} and cba.iwebsiteid=#{2}")
	public List<CategoryLable> getCategoryLableInfoByListingId(
			String listingId, Integer langId, Integer client);


	@Select("select cn.icategoryid,cn.cname,cba.cpath,cba.ilevel,cba.iparentid,cn.ilanguageid from  "
			+ "  t_product_category_mapper  pcm "
			+ "LEFT JOIN t_category_name cn on pcm.icategory=cn.icategoryid "
			+ "LEFT JOIN t_category_website cba on cn.icategoryid=cba.icategoryid "
			+ "where pcm.clistingid=#{0} and cba.iwebsiteid=#{1}")
	public List<CategoryLable> getCategoryLableInfoByListingIdClient(
			String listingId, Integer client);

	@Select("select cn.icategoryid,cn.cname,cba.cpath,cba.ilevel,cba.iparentid,cn.ilanguageid from "
			+ " t_product_category_mapper pcm "
			+ "LEFT JOIN t_category_name cn on pcm.icategory=cn.icategoryid "
			+ "LEFT JOIN t_category_website cba on cn.icategoryid=cba.icategoryid "
			+ "where pcm.clistingid=#{0} and cn.ilanguageid=#{1} and cba.iwebsiteid=#{2} and cba.ilevel=#{3}")
	public List<CategoryLable> getCategoryLableInfoByListingIdLevel(
			String listingId, Integer langId, Integer client, Integer level);
}
