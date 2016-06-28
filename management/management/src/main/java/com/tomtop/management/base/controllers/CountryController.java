package com.tomtop.management.base.controllers;

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
import com.tomtop.management.ebean.manage.model.Country;
import com.tomtop.management.ebean.manage.model.Currency;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.event.BaseCacheEvent;
import com.tomtop.management.event.EventType;
import com.tomtop.management.service.model.CountryObject;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.services.CountryService;

@Controller
@RequestMapping("/base/country")
public class CountryController {

	@Autowired
	CommonService commonService;
	@Autowired
	CountryService countryService;
	@Autowired
	private EventBus eventBus;

	/**
	 * 
	 * @Title: view
	 * @Description: TODO(国家页面显示)
	 * @param @param model
	 * @param @param section
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月22日
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Model model) {
		List<Language> languageList = commonService.getAllLanguage();
		List<Currency> currencyList = commonService.getAllCurrency();
		model.addAttribute("languageList", languageList);
		model.addAttribute("currencyList", currencyList);
		model.addAttribute("section", "baseModul/countryManage.ftl");
		return "index";
	}

	/**
	 * 
	 * @Title: getCountryPage
	 * @Description: TODO(分页查询国家)
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param c
	 * @param @return    
	 * @return Page<CountryObject>    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月22日
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public Page<CountryObject> getCountryPage(int pageNo, int pageLimit, Country c) {
		return countryService.getCountryPage(pageNo, pageLimit, c);
	}

	/**
	 * 
	 * @Title: addCountry
	 * @Description: TODO(新增国家)
	 * @param @param c
	 * @param @param file
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月22日
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String addCountry(Country c) {
		int addCount = countryService.addCountry(c);
		if (addCount > 0) {
			eventBus.post(new BaseCacheEvent("country", EventType.Add));
			return "success";
		} else {
			return "fail";
		}
	}

	/**
	 * 
	 * @Title: getCountryById
	 * @Description: TODO(通过id查询国家)
	 * @param @param id
	 * @param @return    
	 * @return Country    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月22日
	 */
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Country getCountryById(@PathVariable(value = "id") Integer id) {
		return countryService.getCountryById(id);
	}

	/**
	 * 
	 * @Title: updateCountry
	 * @Description: TODO(修改国家)
	 * @param @param c
	 * @param @param file
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月22日
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateCountry(Country c) {
		int updateCount = countryService.updateCountry(c);
		if (updateCount > 0) {
			eventBus.post(new BaseCacheEvent("country", EventType.Update));
			return "success";
		} else {
			return "fail";
		}
	}

	/**
	 * 
	 * @Title: deleteCountry
	 * @Description: TODO(删除国家)
	 * @param @param cIds
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月22日
	 */
	@RequestMapping(value = "/delete/{cIds}", method = RequestMethod.GET)
	@ResponseBody
	public String deleteCountry(@PathVariable(value = "cIds") String cIds) {
		int deleteCount = countryService.deleteCountry(cIds);
		if (deleteCount > 0) {
			eventBus.post(new BaseCacheEvent("country", EventType.Delete));
			return "success";
		} else {
			return "fail";
		}
	}

	/**
	 * 
	 * @Title: countryNameUniqueValidate
	 * @Description: TODO(国家名称唯一校验)
	 * @param @param id
	 * @param @param countryName
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月22日
	 */
	@RequestMapping(value = "/countryNameUV", method = RequestMethod.POST)
	@ResponseBody
	public String countryNameUniqueValidate(Integer id, String countryName) {
		int findCount = countryService.countryNameUniqueValidate(id, countryName);
		if (findCount > 0) {
			return "false";
		} else {
			return "true";
		}
	}
}
