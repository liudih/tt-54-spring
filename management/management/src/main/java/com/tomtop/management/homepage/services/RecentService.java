package com.tomtop.management.homepage.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomtop.management.common.DateUtil;
import com.tomtop.management.ebean.homepage.model.RecentOCountry;
import com.tomtop.management.ebean.homepage.model.RecentOsku;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Country;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.service.model.RecentCountryObject;
import com.tomtop.management.service.model.RecentObject;
import com.tomtop.management.service.model.RecentSkuObject;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.services.ProductService;

@Service
public class RecentService {

	@Autowired
	ProductService productService;
	@Autowired
	CommonService commonService;
	
	public RecentObject getAll(RecentOsku reOsku, String clients,
			String languages) {
		String skuq = " find RecentOsku where is_deleted = 0";
		String countryq = " find RecentOCountry where is_deleted = 0";
		if (null != clients && !clients.equals("")) {
			skuq += " and client_id in (" + clients + ")";
			countryq += " and client_id in (" + clients + ")";
		}else{
			List<Client> clientlists = commonService.getAllClient();
			String cList = "";
			if (null != clientlists && clientlists.size() > 0) {
				for (int i = 0; i < clientlists.size(); i++) {
					if (i == 0) {
						cList = clientlists.get(0).getId().toString();
					} else {
						cList += "," + clientlists.get(i).getId();
					}
				}
				skuq += " and client_id in (" + cList + ")";
				countryq += " and client_id in (" + cList + ")";
			}
		}
		if (null != languages && !languages.equals("")) {
			skuq += " and language_id in (" + languages + ")";
			countryq += " and language_id in (" + languages + ")";
		}
		RecentObject reObject = new RecentObject();
		List<RecentSkuObject> reList = new ArrayList<RecentSkuObject>();
		List<RecentCountryObject> recentCounList = new ArrayList<RecentCountryObject>();
		List<RecentOsku> liOskus = RecentOsku.db().createQuery(RecentOsku.class, skuq).order().desc("whenModified")
				.findList();
		List<RecentOCountry> liOCountries = RecentOCountry.db().createQuery(RecentOCountry.class, countryq).order()
				.desc("whenModified").findList();
		if (null != liOskus) {
			for (RecentOsku rOsku : liOskus) {
				RecentSkuObject skuObject = new RecentSkuObject();
				try {
					BeanUtils.copyProperties(skuObject, rOsku);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				reList.add(skuObject);
			}
			for (RecentSkuObject rSkuObject : reList) {
				if (null != rSkuObject.getClient_id()) {
					rSkuObject.setClient(Client.db().find(Client.class, rSkuObject.getClient_id()));
				}
				if (null != rSkuObject.getLanguage_id()) {
					rSkuObject.setLanguage(Language.db().find(Language.class, rSkuObject.getLanguage_id()));
				}
				rSkuObject.setCreateTime(DateUtil.dateFormat(rSkuObject.getWhenCreated()));
				rSkuObject.setUpdateTime(DateUtil.dateFormat(rSkuObject.getWhenModified()));
			}
		}
		if (null != liOCountries) {
			for (RecentOCountry country : liOCountries) {
				RecentCountryObject countryObject = new RecentCountryObject();
				try {
					BeanUtils.copyProperties(countryObject, country);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				recentCounList.add(countryObject);
			}
			for (RecentCountryObject countryObject : recentCounList) {
				if (null != countryObject.getClient_id()) {
					countryObject.setClient(Client.db().find(Client.class, countryObject.getClient_id()));
				}
				if (null != countryObject.getLanguage_id()) {
					countryObject.setLanguage(Language.db().find(Language.class, countryObject.getLanguage_id()));
				}
				if (null != countryObject.getCountry_id()) {
					countryObject.setCountry(Country.db().find(Country.class, countryObject.getCountry_id()));
				}
				countryObject.setCreateTime(DateUtil.dateFormat(countryObject.getWhenCreated()));
				countryObject.setUpdateTime(DateUtil.dateFormat(countryObject.getWhenModified()));
			}
		}
		reObject.setLiOskus(reList);
		reObject.setLiOCountries(recentCounList);
		return reObject;
	}

	public int addSku(RecentOsku sku) {
		int count = 0;
		sku.setIs_deleted(0);
		sku.setListing_id(productService.getListingIdBySku(sku.getSku()));
		try {
			RecentOsku.db().save(sku);
			count = 1;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int updateSku(RecentOsku sku) {
		int count = 0;
		RecentOsku s = RecentOsku.db().find(RecentOsku.class, sku.getId());
		s.setClient_id(sku.getClient_id());
		s.setLanguage_id(sku.getLanguage_id());
		s.setSku(sku.getSku());
		s.setIs_enabled(sku.getIs_enabled());
		s.setListing_id(productService.getListingIdBySku(sku.getSku()));
		try {
			RecentOsku.db().update(s);
			count = 1;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int addCountry(RecentOCountry country) {
		int count = 0;
		country.setIs_deleted(0);
		RecentOCountry.db().save(country);
		count = 1;
		return count;
	}

	public int updateCountry(RecentOCountry country) {
		int count = 0;
		RecentOCountry s = RecentOCountry.db().find(RecentOCountry.class, country.getId());
		s.setClient_id(country.getClient_id());
		s.setLanguage_id(country.getLanguage_id());
		s.setCountry_id(country.getCountry_id());
		s.setIs_enabled(country.getIs_enabled());
		RecentOCountry.db().update(s);
		count = 1;
		return count;
	}

	public int checkCountry(Integer id,Integer country_id,  Integer clientId, Integer languageId) {
		int findCount = 0;
		if (id == 0) {
			findCount = RecentOCountry.db().find(RecentOCountry.class).where().eq("client_id", clientId)
					.eq("language_id", languageId).eq("country_id", country_id).eq("is_deleted", 0).findRowCount();
		} else {
			RecentOCountry recentOCountry = RecentOCountry.db().find(RecentOCountry.class, id);
			if (recentOCountry.getCountry_id() != country_id || recentOCountry.getClient_id() != clientId
					|| recentOCountry.getLanguage_id() != languageId) {
				findCount = RecentOCountry.db().find(RecentOCountry.class).where().eq("country_id", country_id)
						.eq("client_id", clientId).eq("language_id", languageId).eq("is_deleted", 0).findRowCount();
			}
		}
		return findCount;
	}

	public int checkSku(Integer id, Integer clientId, Integer languageId, String sku) {
		int findCount = 0;
		if (id == 0) {
			findCount = RecentOsku.db().find(RecentOsku.class).where().eq("client_id", clientId)
					.eq("language_id", languageId).eq("sku", sku).eq("is_deleted", 0).findRowCount();
		} else {
			RecentOsku recentOsku = RecentOsku.db().find(RecentOsku.class, id);
			if (recentOsku.getClient_id() != clientId || recentOsku.getLanguage_id() != languageId
					|| !recentOsku.getSku().equals(sku)) {
				findCount = RecentOsku.db().find(RecentOsku.class).where().eq("client_id", clientId)
						.eq("language_id", languageId).eq("sku", sku).eq("is_deleted", 0).findRowCount();
			}
		}
		return findCount;
	}

}
