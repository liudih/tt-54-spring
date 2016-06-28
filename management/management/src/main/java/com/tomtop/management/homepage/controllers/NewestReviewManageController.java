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
import com.tomtop.management.ebean.homepage.model.NewestReview;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Country;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.ebean.product.model.Product;
import com.tomtop.management.event.EventType;
import com.tomtop.management.event.ProductCacheEvent;
import com.tomtop.management.homepage.services.NewestReviewService;
import com.tomtop.management.service.model.NewestReviewObject;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.services.ProductService;

/**
 * 
 * @ClassName: NewestReviewManageController
 * @Description: TODO(最新评论控制管理类)
 * @author Guozy
 * @date 2015年12月24日
 *
 */
@Controller
@RequestMapping("/homepage/newestreview")
public class NewestReviewManageController {

	@Autowired
	NewestReviewService newestReviewService;
	@Autowired
	CommonService commonService;
	@Autowired
	private EventBus eventBus;
	@Autowired
	ProductService productService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		List<Layout> layoutList = commonService.getAllLayout();
		List<BaseParameter> parameters = commonService.getAllParam();
		List<Country> countryList = commonService.getAllCountry();
		model.put("paramters", parameters);
		model.put("clientList", clientList);
		model.put("languageList", languageList);
		model.put("layoutList", layoutList);
		model.put("countryList", countryList);
		model.put("section", "homemodul/newestReview.ftl");
		return "index";
	}

	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public Page<NewestReviewObject> getList(int pageNo, int pageLimit, NewestReview newestReview, String countrys,
			String clients, String languages) {
		newestReview.setSku(newestReview.getSku() == "" ? null : newestReview.getSku());
		newestReview.setReview_by(newestReview.getReview_by() == "" ? null : newestReview.getReview_by());
		newestReview.setCountry(newestReview.getCountry() == "" ? null : newestReview.getCountry());
		Page<NewestReviewObject> mcPage = newestReviewService.getNewestReviewPage(pageNo, pageLimit, newestReview,
				countrys, clients, languages);
		return mcPage;
	}

	@RequestMapping(value = "/validateCode", method = RequestMethod.POST)
	@ResponseBody
	public String validateCode(String code) {
		String msg = null;
		List<NewestReview> dailyDeals = newestReviewService.validateCode(code);
		if (dailyDeals.size() > 0) {
			msg = "FAIL";
		} else {
			msg = "SUCCESS";
		}
		return msg;
	};

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@ModelAttribute NewestReview newestReview) {
		String msg = null;
		try {
			NewestReview.db().save(newestReview);
			eventBus.post(new ProductCacheEvent("product_review", EventType.Add));
			msg = "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "FAIL";
		}
		return msg;
	};

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@ModelAttribute NewestReview newestReview) {
		String msg = null;
		try {
			NewestReview.db().update(newestReview);
			eventBus.post(new ProductCacheEvent("product_review", EventType.Update));
			msg = "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "FAIL";
		}
		return msg;
	};

	@RequestMapping(value = "/getId/{id}", method = RequestMethod.GET)
	@ResponseBody
	public NewestReview getId(@PathVariable(value = "id") int id) {
		return newestReviewService.getNewestReviewId(id);
	};

	@RequestMapping(value = "/delete/{delIds}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable(value = "delIds") String delIds) {
		String msg = null;
		int deleteid = newestReviewService.delById(delIds);
		if (deleteid > 0) {
			eventBus.post(new ProductCacheEvent("product_review", EventType.Delete));
			msg = "SUCCESS";
		} else {
			msg = "FAIL";
		}
		return msg;
	};

	/**
	 * 
	 * @Title: validatesku
	 * @Description: TODO(验证SKU是否存在)
	 * @param @param
	 *            sku
	 * @param @return
	 *            参数
	 * @return Product 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年1月15日
	 */
	@RequestMapping(value = "/validatesku/{sku}", method = RequestMethod.GET)
	@ResponseBody
	public Product validatesku(@PathVariable(value = "sku") String sku) {
		return productService.getProductBySku(sku);
	}

}
