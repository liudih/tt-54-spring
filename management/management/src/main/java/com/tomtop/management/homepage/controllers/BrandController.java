package com.tomtop.management.homepage.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.eventbus.EventBus;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.homepage.model.Brand;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.event.BaseCacheEvent;
import com.tomtop.management.event.EventType;
import com.tomtop.management.event.ProductCacheEvent;
import com.tomtop.management.homepage.services.BrandService;
import com.tomtop.management.service.model.BrandObject;
import com.tomtop.management.services.CommonService;

@Controller
@RequestMapping("/homepage/brand")
public class BrandController {

	@Autowired
	CommonService commonService;
	@Autowired
	BrandService brandService;
	@Autowired
	private EventBus eventBus;

	/**
	 * 
	 * @Title: view
	 * @Description: TODO(品牌信息页面显示)
	 * @param @param model
	 * @param @param section
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月24日
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Model model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		model.addAttribute("clientList", clientList);
		model.addAttribute("languageList", languageList);
		model.addAttribute("section", "homemodul/brandManage.ftl");
		return "index";
	}

	/**
	 * 
	 * @Title: getBrandPage
	 * @Description: TODO(分页查询品牌信息)
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param b
	 * @param @return    
	 * @return Page<BrandObject>    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月24日
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public Page<BrandObject> getBrandPage(int pageNo, int pageLimit, Brand b, String clients, String languages) {
		return brandService.getBrandPage(pageNo, pageLimit, b, clients, languages);
	}

	/**
	 * 
	 * @Title: addBrandContent
	 * @Description: TODO(新增品牌)
	 * @param @param b
	 * @param @param file
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月24日
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String addBrandContent(Brand b, String clients, String languages) {
		int addCount = brandService.addBrand(b, clients, languages);
		if (addCount > 0) {
			eventBus.post(new ProductCacheEvent("product_brand", EventType.Add));
			return "success";
		} else {
			return "fail";
		}
	}

	/**
	 * 
	 * @Title: updateBrandContent
	 * @Description: TODO(修改品牌)
	 * @param @param b
	 * @param @param file
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月24日
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateBrandContent(Brand b) {
		int updateCount = brandService.updateBrand(b);
		if (updateCount > 0) {
			eventBus.post(new ProductCacheEvent("product_brand", EventType.Update));
			return "success";
		} else {
			return "fail";
		}
	}

	/**
	 * 
	 * @Title: getBrandById
	 * @Description: TODO(通过id查询品牌)
	 * @param @param id
	 * @param @return    
	 * @return Brand    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月24日
	 */
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Brand getBrandById(@PathVariable(value = "id") Integer id) {
		return brandService.getBrandById(id);
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: TODO(删除品牌)
	 * @param @param bIds
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月24日
	 */
	@RequestMapping(value = "/delete/{bcIds}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable(value = "bcIds") String bIds) {
		int deleteCount = 0;
		deleteCount = brandService.delete(bIds);
		if (deleteCount > 0) {
			eventBus.post(new ProductCacheEvent("product_brand", EventType.Delete));
			return "success";
		} else {
			return "fail";
		}
	}

	/**
	 * 
	 * @Title: brandNameUniqueValidate
	 * @Description: TODO(验证品牌名称是否重复)
	 * @param @param id
	 * @param @param brandName
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月24日
	 */
	@RequestMapping(value = "/brandNameUV", method = RequestMethod.POST)
	@ResponseBody
	public String brandNameUniqueValidate(Integer id, String brandName) {
		int findCount = brandService.brandNameUniqueValidate(id, brandName);
		if (findCount > 0) {
			return "false";
		} else {
			return "true";
		}
	}
	
	/**
	 * 
	 * @Title: brandUniqueValidate
	 * @Description: TODO(品牌重复校验)
	 * @param @param b
	 * @param @param clients
	 * @param @param languages
	 * @param @return    
	 * @return boolean    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月22日
	 */
	@RequestMapping(value = "/brandUV", method = RequestMethod.POST)
	@ResponseBody
	public boolean brandUniqueValidate(Brand b, String clients, String languages){
		return brandService.brandUniqueValidate(b, clients, languages);
	}
}
