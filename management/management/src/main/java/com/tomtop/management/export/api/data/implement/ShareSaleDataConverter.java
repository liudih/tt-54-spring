package com.tomtop.management.export.api.data.implement;

import java.util.List;

import com.google.common.collect.Lists;
import com.tomtop.management.authority.services.serviceImp.CurrentUserService;
import com.tomtop.management.config.GlobalParameter;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.export.api.IData;
import com.tomtop.management.export.api.IDataConverter;
import com.tomtop.management.export.data.model.BaseData;
import com.tomtop.management.export.data.model.ShareSaleData;
import com.tomtop.management.forms.CurrentUser;

public class ShareSaleDataConverter implements IDataConverter {

	@Override
	public Class<?> getDataType() {
		return ShareSaleData.class;
	}

	@Override
	public List<IData> getDatas(List<BaseData> datas, GlobalParameter globalParameter) {
		List<IData> list = Lists.newArrayList();
		list = Lists.transform(datas, p -> {
			ShareSaleData saleData = new ShareSaleData();
			saleData.setDescription(p.getDescription());
			saleData.setProductUrl(p.getUrl());
			saleData.setImgUrl(p.getPictureUrl());
			if ("".equals(p.getSearchTerms())) {
				saleData.setSearchTerms(p.getTitle());
			} else {
				saleData.setSearchTerms(p.getSearchTerms());
			}
			saleData.setMerchantID("27868");
			saleData.setStatus("In stock");
			saleData.setPrice(p.getOldPrice());
			saleData.setRetailPrice(p.getPrice());
			saleData.setCommission((Double.valueOf(p.getPrice()) * 0.07));
			saleData.setThumbnailImageUrl("http://img.tomtop-cdn.com/product/xy/168/168/" + p.getPictureUrl());
			saleData.setImgUrl(p.getPictureUrl());
			saleData.setName(p.getTitle());
			saleData.setSku(p.getSku());
			if (null != p.getCategoryId()) {
				Category category = Category.db().find(Category.class).where().eq("icategoryid", p.getCategoryId())
						.eq("iwebsiteid", getCurrentSiteId()).findUnique();
				if (null != category) {
					String[] ids = category.getCpath().split("/");
					for (int i = 0; i < ids.length; i++) {
						if (i == 0) {
							saleData.setFirstCategory(ids[0]);
						} else if (i == 1) {
							saleData.setSecoundCategory(ids[1]);
						}
					}
				}
			}
			return saleData;
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
