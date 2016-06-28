package com.tomtop.ipn.mappers.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

public interface DropShippingMapper {

	@Select("select cordernumber from t_dropshipping_map where cdropshippingid = #{0} "
			+ "and cordernumber is not null")
	List<String> getOrderNumbersByID(String dropShippingID);
	
	
	public void updatePaid(Map<String,Object> paras);
}
