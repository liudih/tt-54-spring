package com.tomtop.management.export.api.data.implement;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.tomtop.management.authority.services.serviceImp.CurrentUserService;
import com.tomtop.management.config.GlobalParameter;
import com.tomtop.management.config.MongoDBUtil;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.export.api.IData;
import com.tomtop.management.export.api.IDataConverter;
import com.tomtop.management.export.data.model.BaseData;
import com.tomtop.management.export.data.model.GoogleData;
import com.tomtop.management.forms.CurrentUser;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class GoogleDataConverter implements IDataConverter {

	@Override
	public Class<?> getDataType() {
		return GoogleData.class;
	}

	@Override
	public List<IData> getDatas(List<BaseData> datas, GlobalParameter globalParameter) {
		MongoCollection<Document> collection = MongoDBUtil.getMongoDBCollection(globalParameter,"shipping");
		List<IData> list = Lists.newArrayList();
		list = Lists.transform(datas, p -> {
			GoogleData gData = new GoogleData();
			gData.setId(p.getSku());
			gData.setAvailability("In stock");
			gData.setBrand(p.getBrand());
			gData.setPrice(p.getPrice());
			gData.setLink(p.getUrl());
			gData.setImgUrl(p.getPictureUrl());
			gData.setMpn(p.getSku());
			gData.setCondition("New");
			if (null != p.getCategoryId()) {
				Category category = Category.db().find(Category.class).where().eq("icategoryid", p.getCategoryId())
						.eq("iwebsiteid", getCurrentSiteId()).findUnique();
				if (null != category) {
					gData.setProductType(category.getCpath());
				}
			}
			if (StringUtils.isNotEmpty(p.getTitle()) && p.getTitle().length() > 150) {
				gData.setTitle(p.getTitle().substring(0, 150));
			}else{
				gData.setTitle(p.getTitle());
			}
			if (StringUtils.isNotEmpty(p.getDescription()) && p.getDescription().length() > 5000) {
				gData.setDescription(p.getDescription().substring(0, 5000));
			}else{
				gData.setDescription(p.getDescription());
			}
			if(p.getIsExportFreight() == 1){
				String id = p.getSku() + "-" + getCurrentSiteId() + "-" + p.getStorageId() + "-" + p.getCountry();
				FindIterable<Document> document = collection.find(Filters.eq("id", id));
				double freight = 0.0;
				Object freightValue = null;
				if (null != document && document.iterator().hasNext()) {
					while(document.iterator().hasNext()){
						freightValue = document.iterator().next().get("freight");
						if(null != freightValue){
							freight = (double) freightValue;
							break;
						}
					}
					String shipping = p.getCountry()+":::"+freight+" "+p.getCurrency_code();
					gData.setShipping(shipping);
				}
				
			}
			return gData;
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
