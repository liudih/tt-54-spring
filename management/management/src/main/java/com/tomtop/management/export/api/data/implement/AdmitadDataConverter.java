package com.tomtop.management.export.api.data.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.tomtop.management.authority.services.serviceImp.CurrentUserService;
import com.tomtop.management.config.GlobalParameter;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.export.api.IData;
import com.tomtop.management.export.api.IDataConverter;
import com.tomtop.management.export.data.model.AdmitadData;
import com.tomtop.management.export.data.model.BaseData;
import com.tomtop.management.export.data.model.CategoryAttrData;
import com.tomtop.management.export.data.model.CategoryData;
import com.tomtop.management.export.data.model.CurrencyData;
import com.tomtop.management.export.data.model.ProductDetailData;
import com.tomtop.management.forms.CurrentUser;

public class AdmitadDataConverter implements IDataConverter {

	@Override
	public Class<?> getDataType() {
		return AdmitadData.class;
	}

	@Override
	public List<IData> getDatas(List<BaseData> datas, GlobalParameter globalParameter) {
		List<IData> list = Lists.newArrayList();
		AdmitadData admitadData = new AdmitadData();
		CategoryData categoryData = new CategoryData();
		List<ProductDetailData> detailDatas = new ArrayList<>();
		List<CurrencyData> currencyDatas = new ArrayList<>();
		List<CategoryAttrData> categoryDatas = new ArrayList<>();
		admitadData.setName("TOMTOP");
		admitadData.setCompany("TOMTOP");
		admitadData.setUrl("http://www.tomtop.com/");
		Integer categoryId = datas.get(0).getParentCategory();
		Category categoryP = Category.db().find(Category.class).where().eq("icategoryid", categoryId)
				.eq("iwebsiteid", getCurrentSiteId()).findUnique();
		CategoryAttrData categoryDatap = new CategoryAttrData();
		categoryDatap.setCategory(categoryP.getCpath());
		categoryDatap.setId(categoryP.getIcategoryid());
		categoryDatap.setParent_id(categoryP.getIparentid());
		categoryDatas.add(categoryDatap);
		if (null != categoryId) {
			List<Category> categories = Category.db().find(Category.class).where().eq("iparentid", categoryId)
					.eq("iwebsiteid", getCurrentSiteId()).findList();
			if (null != categories && categories.size() > 0) {
				for (Category c : categories) {
					CategoryAttrData categoryData1 = new CategoryAttrData();
					categoryData1.setCategory(c.getCpath());
					categoryData1.setId(c.getIcategoryid());
					categoryData1.setParent_id(c.getIparentid());
					categoryDatas.add(categoryData1);
					List<Category> cList = Category.db().find(Category.class).where()
							.eq("iwebsiteid", getCurrentSiteId()).eq("iparentid", c.getIcategoryid()).findList();
					if (null != cList && cList.size() > 0) {
						for (Category cc : cList) {
							CategoryAttrData categoryData2 = new CategoryAttrData();
							categoryData2.setCategory(cc.getCpath());
							categoryData2.setId(cc.getIcategoryid());
							categoryData2.setParent_id(cc.getIparentid());
							categoryDatas.add(categoryData2);
							List<Category> cListt = Category.db().find(Category.class).where()
									.eq("iparentid", cc.getIcategoryid()).eq("iwebsiteid", getCurrentSiteId())
									.findList();
							if (null != cListt && cListt.size() > 0) {
								for (Category ccc : cListt) {
									CategoryAttrData categoryData3 = new CategoryAttrData();
									categoryData3.setCategory(ccc.getCpath());
									categoryData3.setId(ccc.getIcategoryid());
									categoryData3.setParent_id(ccc.getIparentid());
									categoryDatas.add(categoryData3);
								}
							}
						}

					}
				}
			}
		}
		admitadData.setCategory(categoryDatas);
		CurrencyData cData = new CurrencyData();
		cData.setCurrency(datas.get(0).getCurrency_code());
		currencyDatas.add(cData);
		detailDatas = Lists.transform(datas, p -> {
			ProductDetailData detailData = new ProductDetailData();
			detailData.setName(p.getTitle());
			detailData.setSku(p.getSku());
			detailData.setDescription(p.getDescription());
			detailData.setUrl(p.getUrl());
			detailData.setCategoryId(p.getCategoryId());
			detailData.setPictureUrl(p.getPictureUrl());
			detailData.setVendor("TOMTOP");
			detailData.setPrice(p.getPrice());
			detailData.setOldPrice(p.getOldPrice());
			detailData.setTopseller("True");
			detailData.setCurrencyId(p.getCurrency_code());
			return detailData;
		});
		admitadData.setCurrencyDatas(currencyDatas);
		admitadData.setProductDetails(detailDatas);
		list.add(admitadData);
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
