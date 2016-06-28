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
import com.tomtop.management.ebean.homepage.model.SearchComplete;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.service.model.SearchKeyWordObject;
import com.tomtop.management.services.CommonService;

@Service
public class CompleteService {
	
	@Autowired
	CommonService commonService;

	public Page<SearchKeyWordObject> getKeyPage(int pageNo, int pageLimit, SearchComplete keyWord,String clients,String languages) {
		String q = " find SearchComplete where is_deleted = 0 ";
		if (null != clients&&!clients.equals("")) {
			q += " and client_id in (" + clients + ") ";
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
		if (null != languages&&!languages.equals("")) {
			q += " and language_id in (" + languages + ") ";
		}
		if (null != keyWord.getKeyword()) {
			q += " and keyword like '%" + keyWord.getKeyword() + "%'";
		}
		Page<SearchKeyWordObject> sitePage = new Page<SearchKeyWordObject>();
		PagedList<SearchComplete> siteList = SearchComplete.db().createQuery(SearchComplete.class, q)
				.findPagedList(pageNo - 1, pageLimit);
		siteList.loadRowCount();
		sitePage.setCount(siteList.getTotalRowCount());
		sitePage.setPageNo(pageNo);
		sitePage.setLimit(pageLimit);
		List<SearchKeyWordObject> siList = new ArrayList<SearchKeyWordObject>();
		for (SearchComplete s : siteList.getList()) {
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

	public int addKeyWord(SearchComplete keyWord) {
		int count = 0;
		keyWord.setIs_deleted(0);
		SearchComplete.db().save(keyWord);
		count = 1;
		return count;
	}

	public int updateSite(SearchComplete keyWord) {
		int count = 0;
		SearchComplete s = SearchComplete.db().find(SearchComplete.class, keyWord.getId());
		s.setClient_id(keyWord.getClient_id());
		s.setLanguage_id(keyWord.getLanguage_id());
		s.setKeyword(keyWord.getKeyword());
		s.setIs_enabled(keyWord.getIs_enabled());
		SearchComplete.db().update(s);
		count = 1;
		return count;
	}

	public int checkKeyWord(Integer clientId, Integer languageId, Integer id, String keyword) {
		int findCount = 0;
		if (id == 0) {
			findCount = SearchComplete.db().find(SearchComplete.class).where().eq("keyword", keyword)
					.eq("client_id", clientId).eq("language_id", languageId).eq("is_deleted", 0).findRowCount();
		} else {
			SearchComplete searchComplete = SearchComplete.db().find(SearchComplete.class, id);
			if (searchComplete.getClient_id() != Long.valueOf(clientId)
					|| searchComplete.getLanguage_id() != Long.valueOf(languageId)
					|| !searchComplete.getKeyword().equals(keyword)) {
				findCount = SearchComplete.db().find(SearchComplete.class).where().eq("keyword", keyword)
						.eq("is_deleted", 0).findRowCount();
			}
		}
		return findCount;
	}

	/**
	 * 
	 * @Title: getSearchCompletes
	 * @Description: TODO(查询所有的热门关键字的所有信息)
	 * @param @return
	 *            参数
	 * @return List<SearchComplete> 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月30日
	 */
	public List<SearchComplete> getSearchCompletes() {
		return SearchComplete.db().find(SearchComplete.class).where().eq("is_deleted", 0).orderBy("id desc").findList();
	};
}
