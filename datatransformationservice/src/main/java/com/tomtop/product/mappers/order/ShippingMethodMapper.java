package com.tomtop.product.mappers.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.tomtop.product.models.dto.shipping.ShippingMethodDetail;

public interface ShippingMethodMapper {

	@Select("select count(istorageid) from t_shipping_method where istorageid=#{0} and benabled=true")
	public int getCountByStorageId(Integer storageId);

	List<ShippingMethodDetail> getShippingMethods(
			@Param("storageId") Integer storageId,
			@Param("country") String country, @Param("weight") Double weight,
			@Param("lang") Integer lang, @Param("price") Double price,
			@Param("isSpecial") Boolean isSpecial);

}
