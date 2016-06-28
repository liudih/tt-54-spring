package com.tomtop.product.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface ProductVideoMapper {

	@Select("select cvideourl from t_product_video where cvideourl is not null and cvideourl != '' and clistingid=#{0} ")
	List<String> getVideoUrlByListingId(String clistingid);

}