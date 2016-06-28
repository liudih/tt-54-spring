package com.tomtop.product.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.tomtop.product.models.dto.ProductLabel;

public interface ProductLabelMapper {

	@Select("<script>select clistingid from t_product_label where clistingid in "
			+ "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'>#{item}</foreach> "
			+ " and ctype = #{type}</script>")
	List<String> getBatchProductLabelByType(
			@Param("list") List<String> clistingids, @Param("type") String type);

	@Select("select * from t_product_label where clistingid = #{0} and iwebsiteid=#{1}")
	List<ProductLabel> getLabelListingId(String listingId, Integer client);

	@Select("select clistingid from t_product_label where iwebsiteid = #{siteId} and ctype = #{type} order by dcreatedate desc limit #{pageSize} offset #{startIndex}")
	List<String> getProductLabelPageByTypeAndWebsite(
			@Param("siteId") int siteId, @Param("type") String type,
			@Param("pageSize") int pageSize, @Param("startIndex") int startIndex);

}
