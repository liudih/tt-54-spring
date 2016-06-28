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
import com.tomtop.management.ebean.homepage.model.SearchKeyWord;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.service.model.SearchKeyWordObject;
import com.tomtop.management.services.CommonService;

@Service
public class SearchKeyWordService {

	@Autowired
	CommonService commonService;

	/**
	 * 
	 * @Title: getKeyPage
	 * @Description: 查询关键字信息
	 * @param @param
	 *            pageNo
	 * @param @param
	 *            pageLimit
	 * @param @param
	 *            keyWord
	 * @param @return
	 *            参数
	 * @return Page<SearchKeyWordObject> 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月22日
	 */
	public Page<SearchKeyWordObject> getKeyPage(int pageNo, int pageLimit, SearchKeyWord keyWord, String clients,
			String languages) {
		String q = " find SearchKeyWord where is_deleted = 0 ";
		if (null != clients && !clients.equals("")) {
			q += " and client_id in (" + clients + ")";
		} else {
			List<Client> cls = commonService.getAllClient();
			String cList = "";
			if (null != cls && cls.size() > 0) {
				for (int i = 0; i < cls.size(); i++) {
					if (i == 0) {
						cList = cls.get(0).getId().toString();
					} else {
						cList += "," + cls.get(i).getId();
					}
				}
				q += " and client_id in (" + cList + ")";
			}
		}
		if (null != languages && !languages.equals("")) {
			q += " and language_id in (" + languages + ")";
		}
		if (null != keyWord.getCategory_id()) {
			q += " and category_id = " + keyWord.getCategory_id();
		}
		if (null != keyWord.getKeyword()) {
			q += " and keyword like '%" + keyWord.getKeyword() + "%'";
		}
		Page<SearchKeyWordObject> sitePage = new Page<SearchKeyWordObject>();
		PagedList<SearchKeyWord> siteList = SearchKeyWord.db().createQuery(SearchKeyWord.class, q).order()
				.desc("whenModified").findPagedList(pageNo - 1, pageLimit);
		siteList.loadRowCount();
		sitePage.setCount(siteList.getTotalRowCount());
		sitePage.setPageNo(pageNo);
		sitePage.setLimit(pageLimit);
		List<SearchKeyWordObject> siList = new ArrayList<SearchKeyWordObject>();
		for (SearchKeyWord s : siteList.getList()) {
			SearchKeyWordObject siteObject = new SearchKeyWordObject();
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
		for (SearchKeyWordObject sObject : siList) {

			if (null != sObject.getCategory_id()) {
				sObject.setCategory(Category.db().find(Category.class, sObject.getCategory_id()));
			}
			if (null != sObject.getClient_id()) {
				sObject.setClient(Client.db().find(Client.class, sObject.getClient_id()));
			}
			if (null != sObject.getLanguage_id()) {
				sObject.setLanguage(Language.db().find(Language.class, sObject.getLanguage_id()));
			}

			sObject.setCreateTime(DateUtil.dateFormat(sObject.getWhenCreated()));
			sObject.setUpdateTime(DateUtil.dateFormat(sObject.getWhenModified()));
		}
		sitePage.setList(siList);
		return sitePage;
	}

	/**
	 * 
	 * @Title: addKeyWord
	 * @Description: 添加信息
	 * @param @param
	 *            keyWord
	 * @param @return
	 *            参数
	 * @return int 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月22日
	 */
	public int addKeyWord(SearchKeyWord keyWord) {
		int count = 0;
		keyWord.setIs_deleted(0);
		SearchKeyWord.db().save(keyWord);
		count = 1;
		return count;
	}

	public int updateSite(SearchKeyWord keyWord) {
		int count = 0;
		SearchKeyWord s = SearchKeyWord.db().find(SearchKeyWord.class, keyWord.getId());
		s.setClient_id(keyWord.getClient_id());
		s.setLanguage_id(keyWord.getLanguage_id());
		s.setSort(keyWord.getSort());
		s.setKeyword(keyWord.getKeyword());
		s.setIs_enabled(keyWord.getIs_enabled());
		s.setCategory_id(keyWord.getCategory_id());
		SearchKeyWord.db().update(s);
		count = 1;
		return count;
	}

	/**
	 * 
	 * @Title: checkKeyWord
	 * @Description: 判断关键字唯一性
	 * @param @param
	 *            keyword
	 * @param @return
	 *            参数
	 * @return List<SearchKeyWord> 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月22日
	 */
	public int checkKeyWord(Integer id, Integer clientId, Integer languageId, String keyword) {
		int findCount = 0;
		findCount = SearchKeyWord.db().find(SearchKeyWord.class).where().eq("keyword", keyword).eq("is_deleted", 0)
				.findRowCount();
		return findCount;
	}

}
