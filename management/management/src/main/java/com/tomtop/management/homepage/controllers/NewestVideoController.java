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
import com.tomtop.management.ebean.homepage.model.NewestVideo;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Country;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.event.EventType;
import com.tomtop.management.event.ProductCacheEvent;
import com.tomtop.management.homepage.services.NewestVideoService;
import com.tomtop.management.service.model.NewestVideoObject;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.services.ProductService;

@Controller
@RequestMapping("/homepage/newestVideo")
public class NewestVideoController {

	@Autowired
	CommonService commonService;
	@Autowired
	NewestVideoService NewestVideoService;
	@Autowired
	private EventBus eventBus;
	@Autowired
	ProductService productService;

	/**
	 * 
	 * @Title: view
	 * @Description: TODO(最新视频页面显示)
	 * @param @param model
	 * @param @param section
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Model model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		List<Country> countryList = commonService.getAllCountry();
		model.addAttribute("clientList", clientList);
		model.addAttribute("languageList", languageList);
		model.addAttribute("countryList", countryList);
		model.addAttribute("section", "homemodul/newestVideo.ftl");
		return "/index";
	}

	/**
	 * 
	 * @Title: getNewestVideoPage
	 * @Description: TODO(分页查询最新视频)
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param nv
	 * @param @return    
	 * @return Page<NewestVideoObject>    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public Page<NewestVideoObject> getNewestVideoPage(int pageNo, int pageLimit, NewestVideo nv, String clients, String languages) {
		return NewestVideoService.getNewestVideoPage(pageNo, pageLimit, nv, clients, languages);
	}

	/**
	 * 
	 * @Title: addNewestVideo
	 * @Description: TODO(新增最新视频)
	 * @param @param nv
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String addNewestVideo(NewestVideo nv, String clients, String languages) {
		int addCount = NewestVideoService.addNewestVideo(nv, clients, languages);
		if (addCount > 0) {
			eventBus.post(new ProductCacheEvent("customers_voices", EventType.Add));
			return "success";
		} else {
			return "fail";
		}
	}

	/**
	 * 
	 * @Title: updateNewestVideo
	 * @Description: TODO(修改最新视频)
	 * @param @param nv
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateNewestVideo(NewestVideo nv) {
		int updateCount = NewestVideoService.updateNewestVideo(nv);
		if (updateCount > 0) {
			eventBus.post(new ProductCacheEvent("customers_voices", EventType.Update));
			return "success";
		} else {
			return "fail";
		}
	}

	/**
	 * 
	 * @Title: getNewestVideoById
	 * @Description: TODO(通过id获取最新视频)
	 * @param @param id
	 * @param @return    
	 * @return NewestVideo    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public NewestVideo getNewestVideoById(@PathVariable(value = "id") Integer id) {
		return NewestVideoService.getNewestVideoById(id);
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: TODO(删除最新视频)
	 * @param @param nvIds
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	@RequestMapping(value = "/delete/{nvIds}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable(value = "nvIds") String nvIds) {
		int deleteCount = 0;
		deleteCount = NewestVideoService.delete(nvIds);
		if (deleteCount > 0) {
			eventBus.post(new ProductCacheEvent("customers_voices", EventType.Delete));
			return "success";
		} else {
			return "fail";
		}
	}
	
	/**
	 * 
	 * @Title: skuIsAvailable
	 * @Description: TODO(验证sku是否可用)
	 * @param @param sku
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月16日
	 */
	@RequestMapping(value = "/skuIsAvailable", method = RequestMethod.POST)
	@ResponseBody
	public String skuIsAvailable(String sku){
		int count = productService.getCountBySku(sku);
		if(count > 0){
			return "true";
		}else{
			return "false";
		}
	}
	
	/**
	 * 
	 * @Title: newestVideoUniqueValidate
	 * @Description: TODO(最新视频唯一校验)
	 * @param @param nv
	 * @param @param clients
	 * @param @param languages
	 * @param @return    
	 * @return boolean    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月22日
	 */
	@RequestMapping(value = "/newestVideoUV", method = RequestMethod.POST)
	@ResponseBody
	public boolean newestVideoUniqueValidate(NewestVideo nv, String clients, String languages){
		return NewestVideoService.newestVideoUniqueValidate(nv, clients, languages);
	}
}
