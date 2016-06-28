package com.tomtop.management.service.model;

import java.util.List;

/**
 * 
 * @ClassName: RecentObject
 * @Description: 最近卖出品业务实体
 * @author Administrator
 * @date 2015年12月28日
 *
 */
public class RecentObject {
	private List<RecentSkuObject> liOskus;
	private List<RecentCountryObject> liOCountries;

	public List<RecentSkuObject> getLiOskus() {
		return liOskus;
	}

	public void setLiOskus(List<RecentSkuObject> liOskus) {
		this.liOskus = liOskus;
	}

	public List<RecentCountryObject> getLiOCountries() {
		return liOCountries;
	}

	public void setLiOCountries(List<RecentCountryObject> liOCountries) {
		this.liOCountries = liOCountries;
	}
}
