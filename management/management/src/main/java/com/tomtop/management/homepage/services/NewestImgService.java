package com.tomtop.management.homepage.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaje.ebean.PagedList;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.homepage.model.NewestImage;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Country;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.service.model.NewestImageObject;
import com.tomtop.management.services.CommonService;

@Service
public class NewestImgService {
	
	@Autowired
	CommonService commonService;

	public Page<NewestImageObject> getKeyPage(int pageNo, int pageLimit,
			NewestImage img,String clients,String languages) {
		String q = " find NewestImage where is_deleted = 0 ";
		if (null != clients && !clients.equals("")) {
			q += " and client_id in (" + clients + ")";
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
				q += " and client_id in (" + cList + ")";
			}
		}
		if (null != languages && !languages.equals("")) {
			q += " and language_id in (" + languages + ") ";
		}
		if (null != img.getImg_by() && !img.getImg_by().equals("")) {
			q += " and img_by = " + img.getImg_by();
		}
		if (null != img.getCountry() && !img.getCountry().equals("")) {
			q += " and country = '" + img.getCountry()+"'";
		}
		if (null != img.getSku() && !img.getSku().equals("")) {
			q += " and sku =" + img.getSku();
		}
		Page<NewestImageObject> sitePage = new Page<NewestImageObject>();
		PagedList<NewestImage> siteList = NewestImage.db()
				.createQuery(NewestImage.class, q).order().desc("whenModified")
				.findPagedList(pageNo - 1, pageLimit);
		siteList.loadRowCount();
		sitePage.setCount(siteList.getTotalRowCount());
		sitePage.setPageNo(pageNo);
		sitePage.setLimit(pageLimit);
		List<NewestImageObject> siList = new ArrayList<NewestImageObject>();
		for (NewestImage s : siteList.getList()) {
			NewestImageObject siteObject = new NewestImageObject();
			try {
				BeanUtils.copyProperties(siteObject, s);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			siList.add(siteObject);
		}
		for (NewestImageObject sObject : siList) {

			if (null != sObject.getClient_id()) {
				sObject.setClient(Client.db().find(Client.class,
						sObject.getClient_id()));
			}
			if (null != sObject.getLanguage_id()) {
				sObject.setLanguage(Language.db().find(Language.class,
						sObject.getLanguage_id()));
			}
			if (null != sObject.getCountry()) {
				List<Country> countries = Country.db().find(Country.class)
						.where().eq("name", sObject.getCountry()).findList();
				if (null != countries && countries.size() > 0) {
					sObject.setCountryy(countries.get(0));
				}
			}

			sObject.setCreateTime(DateUtil.dateFormat(sObject.getWhenCreated()));
			sObject.setUpdateTime(DateUtil.dateFormat(sObject.getWhenModified()));
		}
		sitePage.setList(siList);
		return sitePage;
	}

	public int addKeyWord(NewestImage img) {
		int count = 0;
		img.setIs_deleted(0);
		NewestImage.db().save(img);
		count = 1;
		return count;
	}

	public int updateSite(NewestImage img) {
		int count = 0;
		NewestImage s = NewestImage.db().find(NewestImage.class, img.getId());
		s.setClient_id(img.getClient_id());
		s.setLanguage_id(img.getLanguage_id());
		s.setSku(img.getSku());
		s.setImg_by(img.getImg_by());
		s.setImg_url(img.getImg_url());
		s.setCountry(img.getCountry());
		s.setIs_enabled(img.getIs_enabled());
		NewestImage.db().update(s);
		count = 1;
		return count;
	}

}
