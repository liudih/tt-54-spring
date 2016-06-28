package com.tomtop.management.homepage.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.eventbus.EventBus;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.homepage.model.SearchKeyWord;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.event.EventType;
import com.tomtop.management.event.ProductCacheEvent;
import com.tomtop.management.homepage.services.SearchKeyWordService;
import com.tomtop.management.service.model.SearchKeyWordObject;
import com.tomtop.management.services.CategoryService;
import com.tomtop.management.services.CommonService;

@Controller
@RequestMapping("/homepage/hotsearch")
public class HotSearchController {

	@Autowired
	CommonService commonService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	SearchKeyWordService keywordService;

	@Autowired
	private EventBus eventBus;

	/**
	 * 
	 * @Title: view
	 * @Description: 进入关键字界面
	 * @param @param
	 *            model
	 * @param @param
	 *            section
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月22日
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		List<Category> rootCategoryList = categoryService.getCategoryByLevel(1);
		model.put("clientList", clientList);
		model.put("languageList", languageList);
		model.put("rootCategoryList", rootCategoryList);
		model.put("section", "homemodul/hot_search.ftl");
		return "index";
	}

	/**
	 * 
	 * @Title: getCliPage
	 * @Description: 查询
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
	@RequestMapping("/list")
	@ResponseBody
	public Page<SearchKeyWordObject> getCliPage(int pageNo, int pageLimit, SearchKeyWord keyWord, String clients,
			String languages) {
		Page<SearchKeyWordObject> pagel = keywordService.getKeyPage(pageNo, pageLimit, keyWord, clients, languages);
		return pagel;
	}

	/**
	 * 
	 * @Title: add
	 * @Description: 添加
	 * @param @param
	 *            keyWord
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月22日
	 */
	@RequestMapping("/add")
	@ResponseBody
	public boolean add(SearchKeyWord keyWord) {
		boolean result = true;
		int count = keywordService.addKeyWord(keyWord);
		if (count <= 0) {
			result = false;
		} else {
			eventBus.post(new ProductCacheEvent("home_keyword", EventType.Add));
		}
		return result;
	}

	/**
	 * 
	 * @Title: update
	 * @Description: 修改
	 * @param @param
	 *            keyWord
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月22日
	 */
	@RequestMapping("/update")
	@ResponseBody
	public boolean update(SearchKeyWord keyWord) {
		boolean result = true;
		int count = keywordService.updateSite(keyWord);
		if (count <= 0) {
			result = false;
		} else {
			eventBus.post(new ProductCacheEvent("home_keyword", EventType.Update));
		}
		return result;
	}

	/**
	 * 
	 * @Title: getById
	 * @Description: 根据主键获取信息
	 * @param @param
	 *            id
	 * @param @return
	 *            参数
	 * @return SearchKeyWord 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月22日
	 */
	@RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public SearchKeyWord getById(@PathVariable(value = "id") int id) {
		return SearchKeyWord.db().find(SearchKeyWord.class, id);
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: 删除
	 * @param @param
	 *            delIds
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月22日
	 */
	@RequestMapping(value = "/delete/{delIds}", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean delete(@PathVariable(value = "delIds") String delIds) {
		SearchKeyWord keyWord = null;
		int count = 0;
		boolean result = true;
		String[] ids = delIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			keyWord = SearchKeyWord.db().find(SearchKeyWord.class, ids[i]);
			keyWord.setIs_deleted(1);
			SearchKeyWord.db().update(keyWord);
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

	/**
	 * 
	 * @Title: checkKeyWord
	 * @Description: 验证关键字
	 * @param @param
	 *            keyword
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月22日
	 */
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkKeyWord(@RequestParam("id") Integer id, @RequestParam("client_id") Integer clientId,
			@RequestParam("language_id") Integer languageId, @RequestParam("keyword") String keyword) {
		boolean re = true;
		int sList = keywordService.checkKeyWord(id, clientId, languageId, keyword);
		if (sList > 0) {
			re = false;
		}
		return re;
	}

	/**
	 * 
	 * @Title: getCategoryById
	 * @Description: TODO(通过id查询品类)
	 * @param @param
	 *            id
	 * @param @return
	 * @return Category
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "/getCategory/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Category getCategoryById(@PathVariable(value = "id") Integer id) {
		return categoryService.getCategoryById(id);
	}

}
