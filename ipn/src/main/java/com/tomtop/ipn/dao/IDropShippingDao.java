package com.tomtop.ipn.dao;

import java.util.List;

public interface IDropShippingDao {

	List<String> getOrderNumbersByID(String dropShippingID);

	/**
	 * 修改状态为已经支付完成
	 * 
	 * @param dropShippingID
	 * @return
	 */
	public boolean updatePaidToTrue(String dropShippingID);
}
