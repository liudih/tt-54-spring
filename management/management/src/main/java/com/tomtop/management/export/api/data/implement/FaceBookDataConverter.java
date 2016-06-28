package com.tomtop.management.export.api.data.implement;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.tomtop.management.authority.services.serviceImp.CurrentUserService;
import com.tomtop.management.config.GlobalParameter;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.export.api.IData;
import com.tomtop.management.export.api.IDataConverter;
import com.tomtop.management.export.data.model.BaseData;
import com.tomtop.management.export.data.model.FaceBookData;
import com.tomtop.management.forms.CurrentUser;

public class FaceBookDataConverter implements IDataConverter {

	@Override
	public Class<?> getDataType() {
		return FaceBookData.class;
	}

	@Override
	public List<IData> getDatas(List<BaseData> datas, GlobalParameter globalParameter) {
		List<IData> list = Lists.newArrayList();
		list = Lists.transform(datas, p -> {
			FaceBookData fBookData = new FaceBookData();
			fBookData.setAvailability("In stock");
			fBookData.setBrand(p.getBrand());
			fBookData.setImgUrl(p.getPictureUrl());
			fBookData.setProductUrl(p.getUrl());
			fBookData.setMpn(p.getSku());
			fBookData.setSku(p.getSku());
			fBookData.setPrice(p.getPrice());
			fBookData.setCondition("New");
			if (null != p.getCategoryId()) {
				Category category = Category.db().find(Category.class).where().eq("icategoryid", p.getCategoryId())
						.eq("iwebsiteid", getCurrentSiteId()).findUnique();
				if (null != category) {
					fBookData.setProductType(category.getCpath());
				}
			}
			if (StringUtils.isNotEmpty(p.getTitle()) && p.getTitle().length() > 150) {
				fBookData.setName(p.getTitle().substring(0, 150));
			} else {
				fBookData.setName(p.getTitle());
			}
			if (StringUtils.isNotEmpty(p.getDescription()) && p.getDescription().length() > 5000) {
				fBookData.setDescription(p.getDescription().substring(0, 5000));
			} else {
				fBookData.setDescription(p.getDescription());
			}
			return fBookData;
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
