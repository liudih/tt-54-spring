package com.tomtop.management.base.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.eventbus.EventBus;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.manage.model.Banner;
import com.tomtop.management.ebean.manage.model.BannerContent;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.event.AdvertCacheEvent;
import com.tomtop.management.event.EventType;
import com.tomtop.management.service.model.BannerContentObject;
import com.tomtop.management.service.model.BannerObject;
import com.tomtop.management.service.model.BannerRequest;
import com.tomtop.management.services.BannerService;
import com.tomtop.management.services.CategoryService;
import com.tomtop.management.services.CommonService;


/**
 * 
 * @ClassName: BannerManageController
 * @Description: TODO(广告组管理类)
 * @author Guozy
 * @date 2015年12月18日
 *
 */
@Controller
@RequestMapping("/base/banner")
public class BannerManageController {

	@Autowired
	BannerService bannerService;
	@Autowired
	CommonService commonService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	private EventBus eventBus;

	/**
	 * 
	 * @Title: view
	 * @Description: TODO(广告管理页面显示)
	 * @param @param model
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月13日
	 */
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
		model.put("section", "baseModul/bannersManage.ftl");
		return "index";
	}

	/**
	 * 
	 * @Title: getList
	 * @Description: TODO(分页查询广告)
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param banner
	 * @param @param clients
	 * @param @param languages
	 * @param @return    
	 * @return Page<BannerObject>    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月13日
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public Page<BannerObject> getList(int pageNo, int pageLimit, Banner banner, String clients, String languages) {
		banner.setName(banner.getName() == "" ? null : banner.getName());
		banner.setLayout_code(banner.getLayout_code() == "" ? null : banner.getLayout_code());
		Page<BannerObject> mcPage = bannerService.getBannerPage(pageNo, pageLimit, banner, clients, languages);
		return mcPage;
	}

	/**
	 * 
	 * @Title: validateCode
	 * @Description: TODO(验证编码)
	 * @param @param code
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月13日
	 */
	@RequestMapping(value = "/validateCode", method = RequestMethod.POST)
	@ResponseBody
	public String validateCode(String code) {
		String msg = null;
		List<Banner> layouts = bannerService.validateCode(code);
		if (layouts.size() > 0) {
			msg = "FAIL";
		} else {
			msg = "SUCCESS";
		}
		return msg;
	};

	/**
	 * 
	 * @Title: add
	 * @Description: TODO(新增广告)
	 * @param @param bannerRequest
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月13日
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@RequestBody BannerRequest bannerRequest) {
		String msg = null;
		try {
			bannerService.addBanner(bannerRequest);
			eventBus.post(new AdvertCacheEvent("banners", EventType.Add));
			msg = "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "FAIL";
		}
		return msg;
	};

	/**
	 * 
	 * @Title: update
	 * @Description: TODO(修改广告)
	 * @param @param bannerRequest
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月13日
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@RequestBody BannerRequest bannerRequest) {
		String msg = null;
		try {
			bannerService.updateBanner(bannerRequest);
			eventBus.post(new AdvertCacheEvent("banners", EventType.Update));
			msg = "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "FAIL";
		}
		return msg;
	};

	/**
	 * 
	 * @Title: getId
	 * @Description: TODO(通过id查询广告)
	 * @param @param id
	 * @param @return    
	 * @return Banner    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月13日
	 */
	@RequestMapping(value = "/getId/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Banner getId(@PathVariable(value = "id") int id) {
		return bannerService.getBannerId(id);
	};

	/**
	 * 
	 * @Title: delete
	 * @Description: TODO(删除广告)
	 * @param @param delIds
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月13日
	 */
	@RequestMapping(value = "/delete/{delIds}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable(value = "delIds") String delIds) {
		String msg = null;
		int deleteid = bannerService.delById(delIds);
		if (deleteid > 0) {
			eventBus.post(new AdvertCacheEvent("banners", EventType.Delete));
			msg = "SUCCESS";
		} else {
			msg = "FAIL";
		}
		return msg;
	};

	/**
	 * 
	 * @Title: toAdd
	 * @Description: TODO(跳转到新增页面)
	 * @param @param model
	 * @param @param type
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月13日
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(Model model, String type) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		List<Layout> layoutList = commonService.getAllLayout();
		List<BaseParameter> parameters = commonService.getAllParam();
		List<Category> rootCategoryList = categoryService.getCategoryByLevel(1);
		model.addAttribute("paramters", parameters);
		model.addAttribute("clientList", clientList);
		model.addAttribute("languageList", languageList);
		model.addAttribute("layoutList", layoutList);
		model.addAttribute("section", "baseModul/addBanner.ftl");
		model.addAttribute("rootCategoryList", rootCategoryList);
		model.addAttribute("type", type);
		model.addAttribute("id", 0);
		return "index";
	}

	/**
	 * 
	 * @Title: toUpdate
	 * @Description: TODO(跳转到修改页面)
	 * @param @param model
	 * @param @param type
	 * @param @param id
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月13日
	 */
	@RequestMapping(value = "/toUpdate/{id}", method = RequestMethod.GET)
	public String toUpdate(Model model, @PathVariable(value = "id") Long id) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		List<Layout> layoutList = commonService.getAllLayout();
		List<BaseParameter> parameters = commonService.getAllParam();
		List<Category> rootCategoryList = categoryService.getCategoryByLevel(1);
		model.addAttribute("paramters", parameters);
		model.addAttribute("clientList", clientList);
		model.addAttribute("languageList", languageList);
		model.addAttribute("layoutList", layoutList);
		model.addAttribute("section", "baseModul/addBanner.ftl");
		model.addAttribute("rootCategoryList", rootCategoryList);
		model.addAttribute("type", "update");
		model.addAttribute("id", id);
		return "index";
	}

	/**
	 * 
	 * @Title: getBCListByBannerCode
	 * @Description: TODO(通过广告标识查询广告内容)
	 * @param @param bannerCode
	 * @param @return    
	 * @return List<BannerContentObject>    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月13日
	 */
	@RequestMapping(value = "/getBCByBanner", method = RequestMethod.POST)
	@ResponseBody
	public Page<BannerContentObject> getBCListByBanner(int pageNo, int pageLimit, Banner banner) {
		return bannerService.getBCListByBanner(pageNo, pageLimit, banner);
	}

	/**
	 * 
	 * @Title: bannerCodeUniqueValidate
	 * @Description: TODO(广告标识重复验证)
	 * @param @param id
	 * @param @param code
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月13日
	 */
	@RequestMapping(value = "/bannerCodeUV", method = RequestMethod.POST)
	@ResponseBody
	public String bannerCodeUniqueValidate(Integer id, String code) {
		int findCount = bannerService.bannerCodeUniqueValidate(id, code);
		if (findCount > 0) {
			return "false";
		} else {
			return "true";
		}
	}
	
	/**
	 * 
	 * @Title: bannerUniqueValidate
	 * @Description: TODO(广告唯一验证)
	 * @param @param code
	 * @param @param clients
	 * @param @param languages
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月14日
	 */
	@RequestMapping(value = "/bannerUV", method = RequestMethod.POST)
	@ResponseBody
	public String bannerUniqueValidate(String code, String clients, String languages, String layout_code) {
		int findCount = bannerService.bannerUniqueValidate(code, clients, languages,layout_code);
		if (findCount > 0) {
			return "false";
		} else {
			return "true";
		}
	}
	
	/**
	 * 
	 * @Title: addBannerContent
	 * @Description: TODO(新增广告内容)
	 * @param @param bc
	 * @param @return    
	 * @return boolean    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月7日
	 */
	@RequestMapping(value = "/addBannerContent", method = RequestMethod.POST)
	@ResponseBody
	public boolean addBannerContent(BannerContent bc){
		int addCount = bannerService.addBannerContent(bc);
		if(addCount > 0){
			eventBus.post(new AdvertCacheEvent("banners", EventType.Add));
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: updateBannerContent
	 * @Description: TODO(修改广告内容)
	 * @param @param bc
	 * @param @return    
	 * @return boolean    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月7日
	 */
	@RequestMapping(value = "/updateBannerContent", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateBannerContent(BannerContent bc){
		int updateCount = bannerService.updateBannerContent(bc);
		if(updateCount > 0){
			eventBus.post(new AdvertCacheEvent("banners", EventType.Update));
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: deleteBannerContent
	 * @Description: TODO(删除广告内容)
	 * @param @param id
	 * @param @return    
	 * @return boolean    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月7日
	 */
	@RequestMapping(value = "/deleteBannerContent/{delId}", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean deleteBannerContent(@PathVariable(value = "delId") Long id){
		int deleteCount = bannerService.deleteBannerContent(id);
		if(deleteCount > 0){
			eventBus.post(new AdvertCacheEvent("banners", EventType.Delete));
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: updateBanner
	 * @Description: TODO(修改广告（只修改广告）)
	 * @param @param banner
	 * @param @return    
	 * @return boolean    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月9日
	 */
	@RequestMapping(value = "/updateBanner", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateBanner(Banner banner){
		int updateCount = bannerService.updateBannerOnly(banner);
		return updateCount > 0;
	}
}
