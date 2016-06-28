package com.tomtop.management.homepage.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.eventbus.EventBus;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.homepage.model.DailyDeal;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.ebean.product.model.Product;
import com.tomtop.management.event.ProductCacheEvent;
import com.tomtop.management.event.EventType;
import com.tomtop.management.event.SalePriceCacheEvent;
import com.tomtop.management.homepage.services.DailyDealService;
import com.tomtop.management.service.model.DailyDealObject;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.services.ProductService;

/**
 * 
 * @ClassName: HomeDailyDealManage
 * @Description: TODO(日常促销服务控制类)
 * @author Administrator
 * @date 2015年12月24日
 *
 */
@Controller
@RequestMapping("/homepage/dailydeal")
public class DailyDealManageController {

	@Autowired
	ProductService productService;
	@Autowired
	DailyDealService dailyDealService;
	@Autowired
	CommonService commonService;
	@Autowired
	private EventBus eventBus;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		List<Layout> layoutList = commonService.getAllLayout();
		List<BaseParameter> parameters = commonService.getAllParam();
		model.put("paramters", parameters);
		model.put("clientList", clientList);
		model.put("languageList", languageList);
		model.put("layoutList", layoutList);
		model.put("section", "homemodul/dailyDealManage.ftl");
		return "index";
	}

	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public Page<DailyDealObject> getList(int pageNo, int pageLimit, DailyDealObject dailyDeal, String clients,
			String languages) {
		dailyDeal.setSku(dailyDeal.getSku() == "" ? null : dailyDeal.getSku());
		dailyDeal.setStartTime(dailyDeal.getStartTime() == "" ? null : dailyDeal.getStartTime());
		Page<DailyDealObject> mcPage = dailyDealService.getDailyDealPage(pageNo, pageLimit, dailyDeal, clients,
				languages);
		return mcPage;
	}

	@RequestMapping(value = "/validateSku/{sku}", method = RequestMethod.GET)
	@ResponseBody
	public Product validateSku(@PathVariable(value = "sku") String sku) {
		return productService.getProductBySku(sku);
	};

	@RequestMapping(value = "/validateSkuAndStartDate", method = RequestMethod.POST)
	@ResponseBody
	public String validateSkuAndStartDate(DailyDealObject dailyDeal) {
		String msg = null;
		List<DailyDeal> dailyDeals = dailyDealService.validateSkuAndStartDate(dailyDeal);
		if (dailyDeals.size() > 0) {
			msg = "FAIL";
		} else {
			msg = "SUCCESS";
		}
		return msg;
	};

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@ModelAttribute DailyDealObject dailyDealObject) {
		String msg = null;
		List<DailyDeal> dailyDeals = dailyDealService.validateSkuAndStartDate(dailyDealObject);
		if (dailyDeals != null && dailyDeals.size() > 0) {
			msg = "FAIL";
		} else {
			int result = dailyDealService.add(dailyDealObject);
			if (result > 0) {
				eventBus.post(new ProductCacheEvent("daily_deal", EventType.Add));
				if (dailyDealObject.getIs_enabled() == 0) {
					eventBus.post(
							new SalePriceCacheEvent(dailyDealObject.getListing_id(), dailyDealObject.getfCostPrice(),
									dailyDealObject.getStart_date(), dailyDealObject.getWhenModified(), EventType.Add));
				}
				msg = "SUCCESS";
			} else {
				msg = "FAIL";
			}
		}
		return msg;
	};

	@RequestMapping(value = "/getId/{id}", method = RequestMethod.GET)
	@ResponseBody
	public DailyDealObject getId(@PathVariable(value = "id") int id) {
		return dailyDealService.getDailyDealId(id);
	};

	@RequestMapping(value = "/delete/{delIds}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable(value = "delIds") String delIds) {
		String msg = null;
		int deleteid = dailyDealService.delById(delIds);
		if (deleteid > 0) {
			eventBus.post(new ProductCacheEvent("daily_deal", EventType.Delete));
			msg = "SUCCESS";
		} else {
			msg = "FAIL";
		}
		return msg;
	};

}
