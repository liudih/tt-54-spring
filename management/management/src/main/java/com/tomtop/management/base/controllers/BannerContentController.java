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
import com.tomtop.management.ebean.manage.model.Banner;
import com.tomtop.management.ebean.manage.model.BannerContent;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.event.AdvertCacheEvent;
import com.tomtop.management.event.EventType;
import com.tomtop.management.service.model.BannerContentObject;
import com.tomtop.management.services.BannerContentService;
import com.tomtop.management.services.CategoryService;
import com.tomtop.management.services.CommonService;

@Controller
@RequestMapping("/base/bannerContent")
public class BannerContentController {

	@Autowired
	CommonService commonService;
	@Autowired
	BannerContentService bannerService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	private EventBus eventBus;

	/**
	 * 
	 * @Title: view
	 * @Description: TODO(广告内容页面显示)
	 * @param @param
	 *            model
	 * @param @param
	 *            section
	 * @param @return
	 * @return String
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月21日
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		List<Layout> layoutList = commonService.getAllLayout();
		List<Banner> bannerList = commonService.getAllBanner();
		List<Category> rootCategoryList = categoryService.getCategoryByLevel(1);
		model.put("clientList", clientList);
		model.put("languageList", languageList);
		model.put("layoutList", layoutList);
		model.put("bannerList", bannerList);
		model.put("rootCategoryList", rootCategoryList);
		model.put("section", "baseModul/bannerContentManage.ftl");
		return "index";
	}

	/**
	 * 
	 * @Title: getBannerPage
	 * @Description: TODO(分页查询广告内容)
	 * @param @param
	 *            pageNo
	 * @param @param
	 *            pageLimit
	 * @param @param
	 *            banner
	 * @param @return
	 * @return Page<BannerContentObject>
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月21日
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public Page<BannerContentObject> getBannerPage(int pageNo, int pageLimit, BannerContent banner) {
		return bannerService.getBannerPage(pageNo, pageLimit, banner);
	}

	/**
	 * 
	 * @Title: addBannerContent
	 * @Description: TODO(新增广告内容)
	 * @param @param
	 *            bc
	 * @param @param
	 *            file
	 * @param @return
	 * @return String
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月21日
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String addBannerContent(BannerContent bc) {
		int addCount = bannerService.addBannerContent(bc);
		if (addCount > 0) {
			eventBus.post(new AdvertCacheEvent("banners", EventType.Add));
			return "success";
		} else {
			return "fail";
		}
	}

	/**
	 * 
	 * @Title: updateBannerContent
	 * @Description: TODO(修改广告内容)
	 * @param @param
	 *            bc
	 * @param @param
	 *            file
	 * @param @return
	 * @return String
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月21日
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateBannerContent(BannerContent bc) {
		int updateCount = bannerService.updateBannerContent(bc);
		if (updateCount > 0) {
			eventBus.post(new AdvertCacheEvent("banners", EventType.Update));
			return "success";
		} else {
			return "fail";
		}
	}

	/**
	 * 
	 * @Title: getBannerContentById
	 * @Description: TODO(通过id查询广告)
	 * @param @param
	 *            id
	 * @param @return
	 * @return BannerContent
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月21日
	 */
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BannerContent getBannerContentById(@PathVariable(value = "id") Integer id) {
		return bannerService.getBannerContentById(id);
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: TODO(删除广告内容)
	 * @param @param
	 *            bcIds
	 * @param @return
	 * @return String
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月21日
	 */
	@RequestMapping(value = "/delete/{bcIds}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable(value = "bcIds") String bcIds) {
		int deleteCount = 0;
		deleteCount = bannerService.delete(bcIds);
		if (deleteCount > 0) {
			eventBus.post(new AdvertCacheEvent("banners", EventType.Delete));
			return "success";
		} else {
			return "fail";
		}
	}
}
