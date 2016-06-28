package com.tomtop.management.export.api.data.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.tomtop.management.authority.services.serviceImp.CurrentUserService;
import com.tomtop.management.config.GlobalParameter;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.export.api.IData;
import com.tomtop.management.export.api.IDataConverter;
import com.tomtop.management.export.data.model.BaseData;
import com.tomtop.management.export.data.model.TradetrackerData;
import com.tomtop.management.forms.CurrentUser;
import com.tomtop.management.services.ProductService;

public class TradetrackerDataConverter implements IDataConverter {

	@Autowired
	ProductService pService;

	@Autowired
	GlobalParameter parameter;

	@Override
	public Class<?> getDataType() {
		return TradetrackerData.class;
	}

	@Override
	public List<IData> getDatas(List<BaseData> datas, GlobalParameter globalParameter) {
		List<IData> list = Lists.newArrayList();
		list = Lists.transform(datas, p -> {
			TradetrackerData tradetrackerData = new TradetrackerData();
			tradetrackerData.setSku(p.getSku());
			tradetrackerData.setDescription(p.getDescription());
			tradetrackerData.setCurrency(p.getCurrency_code());
			tradetrackerData.setImageURL(p.getPictureUrl());
			tradetrackerData.setStock("in stock");
			tradetrackerData.setPrice(p.getPrice());
			tradetrackerData.setBrand(p.getBrand());
			tradetrackerData.setProductURL(p.getUrl());
			tradetrackerData.setName(p.getTitle());
			if (null != p.getCategoryId()) {
				Category category = Category.db().find(Category.class).where().eq("icategoryid", p.getCategoryId())
						.eq("iwebsiteid", getCurrentSiteId()).findUnique();
				if (null != category) {
					String[] ids = category.getCpath().split("/");
					for (int i = 0; i < ids.length; i++) {
						if (i == 0) {
							tradetrackerData.setCategories(ids[0]);
						} else if (i == 1) {
							tradetrackerData.setSubcategories(ids[1]);
						}
					}
				}
			}
			return tradetrackerData;
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
