package com.tomtop.management.base.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.eventbus.EventBus;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.ebean.manage.model.LoyoutModul;
import com.tomtop.management.ebean.manage.model.ModulContent;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.event.EventType;
import com.tomtop.management.event.ProductCacheEvent;
import com.tomtop.management.service.model.ModulContentObject;
import com.tomtop.management.services.CategoryService;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.services.ModulContentService;

@Controller
@RequestMapping("/base/modulContent")
public class ModulContentManageController {
	
	@Autowired
	ModulContentService mcService;
	@Autowired
	CommonService commonService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	private EventBus eventBus;
	
	/**
	 * 
	 * @Title: view
	 * @Description: TODO(模块内容页面显示)
	 * @param @param model
	 * @param @param section
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "",method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		List<Layout> layoutList = commonService.getAllLayout();
		List<LoyoutModul> loyoutModulList = commonService.getAllLoyoutModuls();
		List<Category> rootCategoryList = categoryService.getCategoryByLevel(1);
		model.put("clientList", clientList);
		model.put("languageList", languageList);
		model.put("layoutList", layoutList);
		model.put("loyoutModulList", loyoutModulList);
		model.put("rootCategoryList", rootCategoryList);
		model.put("section", "baseModul/modulContentManage.ftl");
		return "index";
	}

	/**
	 * 
	 * @Title: getModulContentPage
	 * @Description: TODO(分页查询模块内容)
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param mc
	 * @param @return    
	 * @return Page<ModulContentServiceModel>    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public Page<ModulContentObject> getModulContentPage(int pageNo, int pageLimit, ModulContent mc) {
		Page<ModulContentObject> mcPage = mcService.getModulContentPage(pageNo, pageLimit, mc);
		return mcPage;
	}
	
	/**
	 * 
	 * @Title: addModulContent
	 * @Description: TODO(新增模块内容)
	 * @param @param mc
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String addModulContent(ModulContent mc) {
		int addCount = mcService.addModulContent(mc);
		String result = null;
		if (addCount > 0) {
			eventBus.post(new ProductCacheEvent("base_layout_module_content", EventType.Add));
			result = "success";
		} else {
			result = "failed";
		}
		return result;
	}
	
	/**
	 * 
	 * @Title: getMCById
	 * @Description: TODO(通过id获取模块内容)
	 * @param @param id
	 * @param @return    
	 * @return ModulContent    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ModulContent getMCById(@PathVariable(value = "id") Integer id) {
		return mcService.getMCById(id);
	}
	
	/**
	 * 
	 * @Title: updateModulContent
	 * @Description: TODO(修改模块内容)
	 * @param @param mc
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateModulContent(ModulContent mc) {
		int updateCount = mcService.updateModulContent(mc);
		String result = null;
		if (updateCount > 0) {
			eventBus.post(new ProductCacheEvent("base_layout_module_content", EventType.Update));
			result = "success";
		} else {
			result = "failed";
		}
		return result;
	}
	
	/**
	 * 
	 * @Title: deleteModulContent
	 * @Description: TODO(删除模块内容)
	 * @param @param mcIds
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "/delete/{mcIds}", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteModulContent(@PathVariable(value = "mcIds") String mcIds) {
		int deleteCount = mcService.deleteModulContent(mcIds);
		String result = null;
		if (deleteCount > 0) {
			eventBus.post(new ProductCacheEvent("base_layout_module_content", EventType.Delete));
			result = "success";
		} else {
			result = "failed";
		}
		return result;
	}
	
}
