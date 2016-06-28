package com.tomtop.management.export.api.data.implement;

import java.util.List;

import com.google.common.collect.Lists;
import com.tomtop.management.authority.services.serviceImp.CurrentUserService;
import com.tomtop.management.config.GlobalParameter;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.export.api.IData;
import com.tomtop.management.export.api.IDataConverter;
import com.tomtop.management.export.data.model.BaseData;
import com.tomtop.management.export.data.model.LinkShareData;
import com.tomtop.management.forms.CurrentUser;

public class LinkShareDataConverter implements IDataConverter {

	@Override
	public Class<?> getDataType() {
		return LinkShareData.class;
	}

	@Override
	public List<IData> getDatas(List<BaseData> datas, GlobalParameter globalParameter) {
		List<IData> list = Lists.newArrayList();
		list = Lists.transform(datas, p -> {
			LinkShareData linkShareData = new LinkShareData();
			linkShareData.setId(p.getSku());
			linkShareData.setBrand(p.getBrand());
			linkShareData.setDescription(p.getDescription());
			linkShareData.setTitle(p.getTitle());
			linkShareData.setPrice(p.getPrice());
			linkShareData.setLink(p.getUrl());
			linkShareData.setImageLink(p.getPictureUrl());
			linkShareData.setAvailability("in stock");
			linkShareData.setMpn(p.getSku());
			linkShareData.setCondition("New");
			if (null != p.getCategoryId()) {
				Category category = Category.db().find(Category.class).where().eq("icategoryid", p.getCategoryId())
						.eq("iwebsiteid", getCurrentSiteId()).findUnique();
				if (null != category) {
					linkShareData.setProductType(category.getCpath());
				}
			}
			return linkShareData;
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
