package com.tomtop.product.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.tomtop.product.models.dto.ProductViewCountDto;

public interface ProductViewCountMapper {

	@Update("UPDATE t_product_viewcount SET iviewcount = iviewcount + 1 "
			+ "WHERE iwebsiteid=#{0} AND clistingid=#{1}")
	int alterViewCount(int siteId, String listingID);

	@Insert("INSERT INTO t_product_viewcount (iwebsiteid, clistingid, iviewcount) "
			+ "VALUES (#{0}, #{1}, #{2})")
	void addViewCount(int siteID, String listingID, int initialCount);

	@Select({ "select * from t_product_viewcount ",
			"where clistingid=#{0} and iwebsiteid=#{1} limit 1", })
	ProductViewCountDto getViewCountByListingId(String listingId, Integer client);
}
