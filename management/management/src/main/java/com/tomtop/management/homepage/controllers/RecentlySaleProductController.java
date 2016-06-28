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
import com.tomtop.management.ebean.homepage.model.RecentOCountry;
import com.tomtop.management.ebean.homepage.model.RecentOsku;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Country;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.event.EventType;
import com.tomtop.management.event.ProductCacheEvent;
import com.tomtop.management.homepage.services.RecentService;
import com.tomtop.management.service.model.RecentObject;
import com.tomtop.management.services.CommonService;

/**
 * 
 * @ClassName: RecentlySaleProductController
 * @Description: 最近卖出品信息管理
 * @author liuxin
 * @date 2015年12月29日
 *
 */
@Controller
@RequestMapping("/homepage/recent")
public class RecentlySaleProductController {

	@Autowired
	CommonService commonService;

	@Autowired
	RecentService recentService;

	@Autowired
	private EventBus eventBus;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		List<Country> countries = commonService.getAllCountry();
		model.put("clientList", clientList);
		model.put("languageList", languageList);
		model.put("countries", countries);
		model.put("section", "homemodul/recent_sale_productmessage.ftl");
		return "index";
	}

	@RequestMapping("/list")
	@ResponseBody
	public RecentObject getCliPage(RecentOsku reOsku, String clients,
			String languages) {
		RecentObject rObject = recentService.getAll(reOsku,clients,languages);
		return rObject;
	}

	@RequestMapping("/addS")
	@ResponseBody
	public boolean add(RecentOsku sku) {
		boolean result = true;
		int count = recentService.addSku(sku);
		if (count <= 0) {
			result = false;
		} else {
			eventBus.post(new ProductCacheEvent("recent_orders", EventType.Add));
		}
		return result;
	}

	@RequestMapping("/addC")
	@ResponseBody
	public boolean addCountry(RecentOCountry country) {
		boolean result = true;
		int count = recentService.addCountry(country);
		if (count <= 0) {
			result = false;
		} else {
			eventBus.post(new ProductCacheEvent("recent_orders", EventType.Add));
		}
		return result;
	}

	@RequestMapping("/updateS")
	@ResponseBody
	public boolean update(RecentOsku sku) {
		boolean result = true;
		int count = recentService.updateSku(sku);
		if (count <= 0) {
			result = false;
		} else {
			eventBus.post(new ProductCacheEvent("recent_orders", EventType.Update));
		}
		return result;
	}

	@RequestMapping("/updateC")
	@ResponseBody
	public boolean updateCountry(RecentOCountry country) {
		boolean result = true;
		int count = recentService.updateCountry(country);
		if (count <= 0) {
			result = false;
		} else {
			eventBus.post(new ProductCacheEvent("recent_orders", EventType.Update));
		}
		return result;
	}

	@RequestMapping(value = "/getSById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public RecentOsku getById(@PathVariable(value = "id") int id) {
		return RecentOsku.db().find(RecentOsku.class, id);
	}

	@RequestMapping(value = "/getCById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public RecentOCountry getCById(@PathVariable(value = "id") int id) {
		return RecentOCountry.db().find(RecentOCountry.class, id);
	}

	@RequestMapping(value = "/delSById/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean delete(@PathVariable(value = "id") int id) {
		RecentOsku sku = null;
		int count = 0;
		boolean result = true;
		sku = RecentOsku.db().find(RecentOsku.class, id);
		sku.setIs_deleted(1);
		RecentOsku.db().update(sku);
		count = 1;
		if (count > 0) {
			eventBus.post(new ProductCacheEvent("recent_orders", EventType.Delete));
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	@RequestMapping(value = "/delCById/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean deleteCountry(@PathVariable(value = "id") int id) {
		RecentOCountry sku = null;
		int count = 0;
		boolean result = true;
		sku = RecentOCountry.db().find(RecentOCountry.class, id);
		sku.setIs_deleted(1);
		RecentOCountry.db().update(sku);
		count = 1;
		if (count > 0) {
			eventBus.post(new ProductCacheEvent("recent_orders", EventType.Delete));
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkCountry(@RequestParam("country_id") Integer country_id,
			@RequestParam("client_id") Integer client_id, @RequestParam("language_id") Integer language_id,
			@RequestParam("id") Integer id) {
		boolean re = true;
		int sList = recentService.checkCountry(id, country_id, client_id, language_id);
		if (sList <= 0) {
			re = false;
		}
		return re;
	}

	@RequestMapping(value = "/validateSku", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkSku(@RequestParam("id") Integer id, @RequestParam("client_id") Integer clientId,
			@RequestParam("language_id") Integer languageId, @RequestParam("sku") String sku) {
		boolean re = true;
		int sList = recentService.checkSku(id, clientId, languageId, sku);
		if (sList <= 0) {
			re = false;
		}
		return re;
	}
}
