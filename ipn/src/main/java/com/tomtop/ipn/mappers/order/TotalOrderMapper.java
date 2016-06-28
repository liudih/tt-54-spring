package com.tomtop.ipn.mappers.order;

import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface TotalOrderMapper {
	@Select("select c.cordernumber from t_total_order a "
			+ " left join t_total_order_map b "
			+ " on a.iid = b.itotalid "
			+ " left join t_order c "
			+ " on b.iorderid = c.iid "
			+ " where a.cid=#{0}")
	List<String> getOrderNumsByTotalID(String cid);

	
}
