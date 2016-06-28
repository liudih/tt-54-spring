package com.tomtop.management.homepage.controllers;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.eventbus.EventBus;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.homepage.model.FeaturedCategory;
import com.tomtop.management.ebean.homepage.model.FeaturedCategoryBanner;
import com.tomtop.management.ebean.homepage.model.FeaturedCategorySku;
import com.tomtop.management.ebean.homepage.model.FeaturedCategoryKey;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.ebean.product.model.Product;
import com.tomtop.management.event.EventType;
import com.tomtop.management.event.ProductCacheEvent;
import com.tomtop.management.homepage.service.values.FeatureCategoryValue;
import com.tomtop.management.homepage.services.FeaturedCategoryBannerService;
import com.tomtop.management.homepage.services.FeaturedCategoryKeyService;
import com.tomtop.management.homepage.services.FeaturedCategoryService;
import com.tomtop.management.homepage.services.FeaturedCategorySkuService;
import com.tomtop.management.service.model.FeaturedCategoryBannerObject;
import com.tomtop.management.service.model.FeaturedCategoryObject;
import com.tomtop.management.services.BaseParameterService;
import com.tomtop.management.services.CategoryService;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.services.ProductService;

/**
 * 
 * @ClassName: FeaturedCategoryManageController
 * @Description: TODO(特色类目管理控制类)
 * @author Guozy
 * @date 2015年12月25日
 *
 */
@Controller
@RequestMapping("/homepage/featurecategory")
public class FeaturedCategoryManageController {

	@Autowired
	ProductService productService;
	@Autowired
	FeaturedCategoryService featuredCategoryService;
	@Autowired
	CommonService commonService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	FeaturedCategorySkuService featuredCategorySkuService;
	@Autowired
	FeaturedCategoryKeyService featuredCategoryKeyService;
	@Autowired
	FeaturedCategoryBannerService featuredCategoryBannerService;
	@Autowired
	private EventBus eventBus;
	@Autowired
	BaseParameterService parameterService;

	private final Integer LEVEL_ID = 1;

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		List<Layout> layoutList = commonService.getAllLayout();
		List<BaseParameter> parameters = commonService.getAllParam();
		List<Category> rootCategoryList = categoryService.getCategoryByLevel(LEVEL_ID);
		model.put("parameters", parameters);
		model.put("clientList", clientList);
		model.put("languageList", languageList);
		model.put("layoutList", layoutList);
		model.put("rootCategoryList", rootCategoryList);
		model.put("section", "homemodul/featuredCategoryManage.ftl");
		return "index";
	}

	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public Page<FeaturedCategoryObject> getList(int pageNo, int pageLimit, FeaturedCategory featuredCategory,
			String clients, String languages, String categorys) {
		Page<FeaturedCategoryObject> featuredCategoryPage = featuredCategoryService.getFeaturedCategoryPage(pageNo,
				pageLimit, featuredCategory, clients, languages, categorys);
		return featuredCategoryPage;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@RequestBody FeatureCategoryValue featuredCategory) {
		String msg = null;
		logger.info("-featuredCategory->{}", featuredCategory.getLanguage_id());
		try {
			featuredCategoryService.insert(featuredCategory);
			eventBus.post(new ProductCacheEvent("fcategory", EventType.Add));
			msg = "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "FAIL";
		}
		return msg;
	};

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@RequestBody FeatureCategoryValue featuredCategory) {
		String msg = null;
		try {
			featuredCategoryService.update(featuredCategory);
			eventBus.post(new ProductCacheEvent("fcategory", EventType.Update));
			msg = "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "FAIL";
		}
		return msg;
	};

	@RequestMapping(value = "/getId/{id}", method = RequestMethod.GET)
	@ResponseBody
	public FeatureCategoryValue getId(@PathVariable(value = "id") int id) {
		return featuredCategoryService.getFeaturedCategorylId(id);
	};

	@RequestMapping(value = "/validateskuid/{sku}", method = RequestMethod.GET)
	@ResponseBody
	public Product validateskuid(@PathVariable(value = "sku") String sku) {
		return productService.getProductBySku(sku);
	};

	@RequestMapping(value = "/validateskudataid/{sku}", method = RequestMethod.GET)
	@ResponseBody
	public String validateskudataid(@PathVariable(value = "sku") String sku) {
		String msg;
		List<FeaturedCategorySku> skuList = featuredCategorySkuService.validateSkuExist(sku);
		if (skuList != null && skuList.size() > 0) {
			msg = "FAIL";
		} else {
			msg = "SUCCESS";
		}
		return msg;
	};

	@RequestMapping(value = "/delete/{delIds}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable(value = "delIds") String delIds) {
		String msg = null;
		int deleteid = featuredCategoryService.delById(delIds);
		if (deleteid > 0) {
			eventBus.post(new ProductCacheEvent("fcategory", EventType.Delete));
			msg = "SUCCESS";
		} else {
			msg = "FAIL";
		}
		return msg;
	};

	/**
	 * 
	 * @Title: edit
	 * @Description: TODO(展示详情信息)
	 * @param @param
	 *            model
	 * @param @param
	 *            section
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             Administrator
	 * @date 2015年12月30日
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") int id, Map<String, Object> model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		List<Layout> layoutList = commonService.getAllLayout();
		List<BaseParameter> parameters = parameterService.getAllParamBybanner();
		List<Category> rootCategoryList = categoryService.getCategoryByLevel(LEVEL_ID);
		List<FeaturedCategory> featuredCategories = featuredCategoryService.getFeaturedCategorys();
		List<FeaturedCategoryKey> keyList = featuredCategoryKeyService.getFeaturedCategoryKeys();
		List<FeaturedCategorySku> skuList = featuredCategorySkuService.getFeaturedCategorySkus();

		model.put("parameters", parameters);
		model.put("clientList", clientList);
		model.put("languageList", languageList);
		model.put("layoutList", layoutList);
		model.put("rootCategoryList", rootCategoryList);
		model.put("skuList", skuList);
		model.put("featuredCategories", featuredCategories);
		model.put("keyList", keyList);
		if (id > 0) {
			model.put("edit", "edit");
		} else {
			model.put("edit", "add");
		}
		model.put("iid", id);
		model.put("section", "homemodul/editFeaturedCategoryManage.ftl");
		return "index";
	}

	/**
	 * 
	 * @Title: validateFeaturedCategory
	 * @Description: TODO(验证语言和客户单和类目)
	 * @param @param
	 *            FeaturedCategory
	 * @param @return
	 *            参数
	 * @return FeaturedCategory 返回类型
	 * @throws @author
	 *             Administrator
	 * @date 2016年1月14日
	 */
	@RequestMapping(value = "/validateFeaturedCategory", method = RequestMethod.POST)
	@ResponseBody
	public String validateFeaturedCategory(FeaturedCategory featuredCategory, String clients) {
		String ms;
		List<FeaturedCategory> FeaturedCategorys = featuredCategoryService.validateFeaturedCategory(featuredCategory,
				clients);
		if (FeaturedCategorys.size() > 0) {
			ms = "FAIL";
		} else {
			ms = "SUCCESS";
		}
		return ms;
	}
}
