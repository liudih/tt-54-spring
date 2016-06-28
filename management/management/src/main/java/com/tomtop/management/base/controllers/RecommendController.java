package com.tomtop.management.base.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.eventbus.EventBus;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.ebean.manage.model.LoyoutModul;
import com.tomtop.management.ebean.manage.model.ModulContent;
import com.tomtop.management.event.EventType;
import com.tomtop.management.event.ProductCacheEvent;
import com.tomtop.management.homepage.service.values.RecommendValue;
import com.tomtop.management.service.model.LayoutModelObject;
import com.tomtop.management.service.model.RecommendObject;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.services.ProductService;
import com.tomtop.management.services.RecommendService;

@Controller
@RequestMapping("/base/layoutModule")
public class RecommendController {

	@Autowired
	ProductService productService;

	@Autowired
	CommonService commonService;

	@Autowired
	RecommendService recommendService;

	@Autowired
	private EventBus eventBus;

	/**
	 * 
	 * @Title: view
	 * @Description: 推荐管理
	 * @param @param
	 *            model
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2016年1月13日
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		List<Layout> layoutList = commonService.getAllLayout();
		model.put("clientList", clientList);
		model.put("languageList", languageList);
		model.put("layoutList", layoutList);
		model.put("type", "add");
		model.put("section", "baseModul/recommend_manage.ftl");
		return "index";
	}

	@RequestMapping(value = "/operation/{id}", method = RequestMethod.GET)
	public String view(@PathVariable(value = "id") String id, Map<String, Object> model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		List<Layout> layoutList = commonService.getAllLayout();
		List<BaseParameter> parameters = commonService.getAllParam();
		model.put("paramters", parameters);
		model.put("clientList", clientList);
		model.put("languageList", languageList);
		model.put("layoutList", layoutList);
		model.put("id", id);
		model.put("type", "add");
		model.put("section", "baseModul/recommend2.ftl");
		return "index";
	}

	@RequestMapping("/list")
	@ResponseBody
	public Page<LayoutModelObject> getModuleCount(int pageNo, int pageLimit, RecommendObject recommend) {
		Page<LayoutModelObject> lPage = recommendService.getLoyoutModulPage(pageNo, pageLimit, recommend);
		return lPage;
	}

	@RequestMapping(value = "/delete/{delIds}", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean deleteByIds(@PathVariable(value = "delIds") String delIds) {
		LoyoutModul modul = null;
		int delCount = 0;
		boolean result = true;
		String[] ids = delIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			modul = LoyoutModul.db().find(LoyoutModul.class, Integer.parseInt(ids[i]));
			modul.setIs_deleted(1);
			LoyoutModul.db().update(modul);
			delCount++;
		}
		if (delCount > 0) {
			result = true;
			eventBus.post(new ProductCacheEvent("base_layout_module_content", EventType.Delete));
		} else {
			result = false;
		}
		return result;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public boolean add(@RequestBody RecommendValue recommend) {
		boolean result = false;
		try {
			result = recommendService.Insert(recommend);
			eventBus.post(new ProductCacheEvent("base_layout_module_content", EventType.Add));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	};

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public boolean update(@RequestBody RecommendValue recommend) {
		boolean result = false;
		try {
			result = recommendService.update(recommend);
			eventBus.post(new ProductCacheEvent("base_layout_module_content", EventType.Update));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/validateName", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkName(@RequestBody RecommendValue recommend) {
		boolean re = true;
		List<LoyoutModul> list = recommendService.checkModuleName(recommend);
		if (list.size() > 0) {
			re = true;
		} else {
			re = false;
		}
		return re;
	}

	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String view(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		List<Layout> layoutList = commonService.getAllLayout();
		List<BaseParameter> parameters = commonService.getAllParam();
		model.put("paramters", parameters);
		model.put("clientList", clientList);
		model.put("languageList", languageList);
		model.put("layoutList", layoutList);
		model.put("id", id);
		model.put("type", "update");
		model.put("section", "baseModul/recommend2.ftl");
		return "index";
	}

	@RequestMapping(value = "/getModuleByid/{id}", method = RequestMethod.GET)
	@ResponseBody
	public LoyoutModul getById(@PathVariable(value = "id") Long id) {
		LoyoutModul loyoutModul = LoyoutModul.db().find(LoyoutModul.class, id);
		return loyoutModul;
	}

	@RequestMapping(value = "/getModuleContentByid/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<ModulContent> getById(ModulContent modulContent) {
		List<ModulContent> modulContents = recommendService.getById(modulContent);
		return modulContents;
	}

	@RequestMapping(value = "/validateskuid/{sku}", method = RequestMethod.GET)
	@ResponseBody
	public String validateskuid(@PathVariable(value = "sku") String sku) {
		return productService.getListingIdBySku(sku);
	};

}
