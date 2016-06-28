package com.tomtop.management.homepage.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.tomtop.management.common.ExcelUtils;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.homepage.model.SearchComplete;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.search.model.KeywordSuggest;
import com.tomtop.management.event.EventType;
import com.tomtop.management.event.ProductCacheEvent;
import com.tomtop.management.homepage.services.CompleteService;
import com.tomtop.management.service.model.SearchKeyWordObject;
import com.tomtop.management.services.CommonService;

/**
 * 
    * @ClassName: SearchCompleteController
    * @Description:自动补全信息管理
    * @author liuxin
    * @date 2015年12月29日
    *
 */
@Controller
@RequestMapping("/homepage/searchcomplete")
public class SearchCompleteController {

	@Autowired
	CommonService commonService;

	@Autowired
	CompleteService completeService;
	
	@Autowired
	private EventBus eventBus;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		model.put("clientList", clientList);
		model.put("languageList", languageList);
		model.put("section", "homemodul/search_complete.ftl");
		return "index";
	}

	@RequestMapping("/list")
	@ResponseBody
	public Page<SearchKeyWordObject> getCliPage(int pageNo, int pageLimit,
			SearchComplete keyWord,String clients,String languages) {
		Page<SearchKeyWordObject> pagel = completeService.getKeyPage(pageNo,
				pageLimit, keyWord,clients,languages);
		return pagel;
	}

	@RequestMapping("/add")
	@ResponseBody
	public String add(SearchComplete keyWord) {
		String result = "fail";
		int count = completeService.addKeyWord(keyWord);
		if (count <= 0) {
			result = "fail";
		}else{
			result = "success";
			eventBus.post(new ProductCacheEvent("home_keyword", EventType.Add));
		}
		return result;
	}

	@RequestMapping("/update")
	@ResponseBody
	public String update(SearchComplete keyWord) {
		String result = "fail";
		int count = completeService.updateSite(keyWord);
		if (count <= 0) {
			result = "fail";
		}else{
			result = "success";
			eventBus.post(new ProductCacheEvent("home_keyword", EventType.Update));
		}
		return result;
	}

	@RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public SearchComplete getById(@PathVariable(value = "id") int id) {
		return SearchComplete.db().find(SearchComplete.class, id);
	}

	@RequestMapping(value = "/delete/{delIds}", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean delete(@PathVariable(value = "delIds") String delIds) {
		SearchComplete keyWord = null;
		int count = 0;
		boolean result = true;
		String[] ids = delIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			keyWord = SearchComplete.db().find(SearchComplete.class, ids[i]);
			keyWord.setIs_deleted(1);
			SearchComplete.db().update(keyWord);
			count++;
		}
		if (count > 0) {
			eventBus.post(new ProductCacheEvent("home_keyword", EventType.Delete));
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkKeyWord(@RequestParam("client_id") Integer clientId,@RequestParam("language_id") Integer languageId,@RequestParam("id") Integer id,@RequestParam("keyword") String keyword) {
		boolean re = true;
		int sList = completeService.checkKeyWord(clientId,languageId,id,keyword);
		if (sList <= 0) {
			re = false;
		}
		return re;
	}

	@RequestMapping(value = "/synchronized", method = RequestMethod.GET)
	@ResponseBody
	public boolean originedKeyword() {
		boolean result = true;
		List<KeywordSuggest> kSuggests = KeywordSuggest.db()
				.find(KeywordSuggest.class).findList();
		List<SearchComplete> sList = SearchComplete.db()
				.find(SearchComplete.class).where().eq("is_deleted", 0)
				.findList();
		List<String> searchKey = Lists.newArrayList();
		if (null != kSuggests && kSuggests.size() != 0) {
			if (null != sList && sList.size() != 0) {
				for (SearchComplete sComplete : sList) {
					searchKey.add(sComplete.getKeyword());
				}
				for (int i = 0; i < kSuggests.size(); i++) {
					if (searchKey.contains(kSuggests.get(i).getCkeyword())) {
						kSuggests.remove(i);
					}
				}
			}

			for (int i = 0; i < kSuggests.size(); i++) {
				SearchComplete searchComplete = new SearchComplete();
				searchComplete.setKeyword(kSuggests.get(i).getCkeyword());
				searchComplete.setClient_id(Long.valueOf(kSuggests.get(i)
						.getIwebsiteid()));
				searchComplete.setLanguage_id(Long.valueOf(kSuggests.get(i)
						.getIlanguageid()));
				searchComplete.setIs_deleted(0);
				searchComplete.setIs_enabled(1);
				SearchComplete.db().save(searchComplete);
				result = true;
			}
		} else {
			result = false;
		}
		return result;
	}

	@RequestMapping("/upload")
	@ResponseBody
	public boolean upload(@RequestParam("excel") MultipartFile file)
			throws IOException {
		try {
			List<List<String>> list = new ExcelUtils().read(
					file.getInputStream(), file.getOriginalFilename());
			if (null != list && list.size() > 0) {
				list.remove(0);
				for (List<String> l : list) {
					SearchComplete sComplete = new SearchComplete();
					String client = l.get(0);
					String language = l.get(1);
					String key = l.get(2);
					Client clients = Client.db().find(Client.class).where()
							.eq("name", client).eq("is_deleted", 0)
							.findUnique();
					Language languages = Language.db().find(Language.class)
							.where().eq("code", language).eq("is_deleted", 0)
							.findUnique();
					if (clients != null && languages != null) {
						List<SearchComplete> searchCompletes = SearchComplete
								.db().find(SearchComplete.class).where()
								.eq("client_id", clients.getId())
								.eq("language_id", languages.getId())
								.eq("keyword", key).eq("is_deleted", 0)
								.findList();
						if (null == searchCompletes
								|| searchCompletes.size() <= 0) {
							sComplete.setKeyword(key);
							sComplete.setLanguage_id(languages.getId());
							sComplete.setClient_id(clients.getId());
							sComplete.setIs_deleted(0);
							sComplete.setIs_enabled(1);
							SearchComplete.db().save(sComplete);
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			LoggerFactory.getLogger(this.getClass()).error(
					"error,msg：" + e.getMessage());
		}
		return false;
	}
}
