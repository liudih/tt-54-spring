package com.tomtop.management.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.avaje.ebean.PagedList;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.service.model.LanguageObject;

@Service
public class LanguageService {

	public Page<LanguageObject> getLanguagePage(int pageNo, int pageLimit, Language language) {
		String q = " find Language where is_deleted = 0 ";
		if (null != language.getName() && !language.getName().equals("")) {
			q += " and name like '%" + language.getName() + "%'";
		}
		if (null != language.getCode() && !language.getCode().equals("")) {
			q += " and code like '%" + language.getCode() + "%'";
		}
		Page<LanguageObject> lanPage = new Page<LanguageObject>();
		PagedList<Language> siteList = Language.db().createQuery(Language.class, q).order().desc("whenModified")
				.findPagedList(pageNo - 1, pageLimit);
		siteList.loadRowCount();
		lanPage.setCount(siteList.getTotalRowCount());
		lanPage.setPageNo(pageNo);
		lanPage.setLimit(pageLimit);
		List<LanguageObject> siList = new ArrayList<LanguageObject>();
		for (Language s : siteList.getList()) {
			LanguageObject siteObject = new LanguageObject();
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
		for (LanguageObject sObject : siList) {
			sObject.setCreateTime(DateUtil.dateFormat(sObject.getWhenCreated()));
			sObject.setUpdateTime(DateUtil.dateFormat(sObject.getWhenModified()));
		}
		lanPage.setList(siList);
		return lanPage;
	}

	public int addLanguage(Language language) {
		int count = 0;
		language.setIs_deleted(0);
		Language.db().save(language);
		count = 1;
		return count;
	}

	public int updateLanguage(Language language) {
		int count = 0;
		Language lan = Language.db().find(Language.class, language.getId());
		lan.setName(language.getName());
		lan.setCode(language.getCode());
		lan.setIs_enabled(language.getIs_enabled());
		lan.setSort(language.getSort());
		Language.db().update(lan);
		count = 1;
		return count;
	}

	public int checkName(Integer id, String name) {
		int findCount = 0;
		if (id == 0) {
			findCount = Language.db().find(Language.class).where().eq("name", name).findRowCount();
		} else {
			Language language = Language.db().find(Language.class, id);
			if (!language.getName().equals(name)) {
				findCount = Language.db().find(Language.class).where().eq("name", name).findRowCount();
			}
		}
		return findCount;
	}

	public int checkCode(Integer id, String name) {
		int findCount = 0;
		if (id == 0) {
			findCount = Language.db().find(Language.class).where().eq("code", name).findRowCount();
		} else {
			Language language = Language.db().find(Language.class, id);
			if (!language.getCode().equals(name)) {
				findCount = Language.db().find(Language.class).where().eq("code", name).findRowCount();
			}
		}
		return findCount;
	}
}
