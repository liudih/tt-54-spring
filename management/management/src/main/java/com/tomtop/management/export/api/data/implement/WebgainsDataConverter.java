package com.tomtop.management.export.api.data.implement;

import java.util.List;

import com.google.common.collect.Lists;
import com.tomtop.management.authority.services.serviceImp.CurrentUserService;
import com.tomtop.management.config.GlobalParameter;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.export.api.IData;
import com.tomtop.management.export.api.IDataConverter;
import com.tomtop.management.export.data.model.BaseData;
import com.tomtop.management.export.data.model.WebgainsData;
import com.tomtop.management.forms.CurrentUser;

public class WebgainsDataConverter implements IDataConverter {

	@Override
	public Class<?> getDataType() {
		return WebgainsData.class;
	}

	@Override
	public List<IData> getDatas(List<BaseData> datas, GlobalParameter globalParameter) {
		List<IData> list = Lists.newArrayList();
		list = Lists.transform(datas, p -> {
			WebgainsData wData = new WebgainsData();
			wData.setBrand(p.getBrand());
			wData.setImageUrl(p.getPictureUrl());
			wData.setInStock("Y");
			wData.setCurrency(p.getCurrency_code());
			wData.setTopSellers("1");
			wData.setSku(p.getSku());
			wData.setDeeplink(p.getUrl());
			wData.setDescription(p.getDescription());
			wData.setShortDescription(p.getShortDescription());
			wData.setPrice(p.getPrice());
			wData.setProductID(p.getSku());
			wData.setProductName(p.getTitle());
			if (null != p.getCategoryId()) {
				Category category = Category.db().find(Category.class).where().eq("icategoryid", p.getCategoryId())
						.eq("iwebsiteid", getCurrentSiteId()).findUnique();
				if (null != category) {
					String[] ids = category.getCpath().split("/");
					for (int i = 0; i < ids.length; i++) {
						if (null != ids[1]) {
							wData.setCategory(ids[1]);
						} else {
							wData.setCategory(ids[0]);
						}
					}
				}
			}
			return wData;
		});
		return list;
	}

	private Long getCurrentSiteId() {
		CurrentUserService currentUserService = new CurrentUserService();
		CurrentUser currentUser = currentUserService.getUserDetails();
		Integer currentSite = currentUser.getSiteMap().get("siteId");
		if (null == currentSite) {
			currentSite = 1;
		}
		return Long.valueOf(currentSite);
	}

}
