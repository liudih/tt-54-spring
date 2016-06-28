package com.tomtop.management.export.api.data.implement;

import java.util.List;

import com.google.common.collect.Lists;
import com.tomtop.management.authority.services.serviceImp.CurrentUserService;
import com.tomtop.management.config.GlobalParameter;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.export.api.IData;
import com.tomtop.management.export.api.IDataConverter;
import com.tomtop.management.export.data.model.BaseData;
import com.tomtop.management.export.data.model.ClixgaloreData;
import com.tomtop.management.forms.CurrentUser;

public class ClixgaloreDataConverter implements IDataConverter {

	@Override
	public Class<?> getDataType() {
		return ClixgaloreData.class;
	}

	@Override
	public List<IData> getDatas(List<BaseData> datas, GlobalParameter globalParameter) {
		List<IData> list = Lists.newArrayList();
		list = Lists.transform(datas, p -> {
			ClixgaloreData clixgaloreData = new ClixgaloreData();
			clixgaloreData.setTitle(p.getTitle());
			clixgaloreData.setUrl(p.getUrl());
			clixgaloreData.setDescription(p.getDescription());
			clixgaloreData.setBaseprice(p.getPrice());
			clixgaloreData.setImgUrl(p.getPictureUrl());
			clixgaloreData.setCurrency(p.getCurrency_code());
			clixgaloreData.setBrandName(p.getBrand());
			if (null != p.getCategoryId()) {
				Category category = Category.db().find(Category.class).where().eq("icategoryid", p.getCategoryId())
						.eq("iwebsiteid", getCurrentSiteId()).findUnique();
				if (null != category) {
					String[] ids = category.getCpath().split("/");
					for (int i = 0; i < ids.length; i++) {
						if (i == 0) {
							clixgaloreData.setFirstCategory(ids[0]);
						} else if (i == 1) {
							clixgaloreData.setSecoundCategory(ids[1]);
						} else if (i == 2) {
							clixgaloreData.setThirdCategory(ids[2]);
						}
					}
				}
			}

			return clixgaloreData;
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
