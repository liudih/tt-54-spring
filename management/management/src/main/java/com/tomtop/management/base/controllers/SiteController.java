package com.tomtop.management.base.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.eventbus.EventBus;
import com.tomtop.management.authority.services.serviceImp.CurrentUserService;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.manage.model.Site;
import com.tomtop.management.event.BaseCacheEvent;
import com.tomtop.management.event.EventType;
import com.tomtop.management.forms.CurrentUser;
import com.tomtop.management.service.model.SiteObject;
import com.tomtop.management.services.SiteService;

@Controller
@RequestMapping("/base/site")
public class SiteController {

	@Autowired
	SiteService siteService;
	
	@Autowired
	CurrentUserService currentUserService;

	@Autowired
	private EventBus eventBus;

	/**
	 * 
	 * @Title: view
	 * @Description: 进入主界面
	 * @param @param
	 *            model
	 * @param @param
	 *            section
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月18日
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		model.put("section", "baseModul/site_manage.ftl");
		return "index";
	}

	/**
	 * 
	 * @Title: getCliPage
	 * @Description: 查询
	 * @param @param
	 *            pageNo
	 * @param @param
	 *            pageLimit
	 * @param @param
	 *            site
	 * @param @return
	 *            参数
	 * @return Page<SiteObject> 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月18日
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Page<SiteObject> getCliPage(int pageNo, int pageLimit, Site site) {
		Page<SiteObject> pagel = siteService.getSitePage(pageNo, pageLimit, site);
		return pagel;
	}

	/**
	 * 
	 * @Title: add
	 * @Description: 添加
	 * @param @param
	 *            site
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月18日
	 */
	@RequestMapping("/add")
	@ResponseBody
	public boolean add(Site site) {
		boolean result = true;
		int count = siteService.addSite(site);
		if (count <= 0) {
			result = false;
		} else {
			eventBus.post(new BaseCacheEvent("site", EventType.Add));
		}
		return result;
	}

	/**
	 * 
	 * @Title: update
	 * @Description: 修改信息
	 * @param @param
	 *            site
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月18日
	 */
	@RequestMapping("/update")
	@ResponseBody
	public boolean update(Site site) {
		boolean result = true;
		int count = siteService.updateSite(site);
		if (count <= 0) {
			result = false;
		} else {
			eventBus.post(new BaseCacheEvent("site", EventType.Update));
		}
		return result;
	}

	/**
	 * 
	 * @Title: getClientById
	 * @Description: 根据主键获取信息
	 * @param @param
	 *            id
	 * @param @return
	 *            参数
	 * @return Site 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月18日
	 */
	@RequestMapping(value = "/getSiteById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Site getById(@PathVariable(value = "id") int id) {
		return Site.db().find(Site.class, id);
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: 一条或多条信息删除（非物理删除）
	 * @param @param
	 *            delIds
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月18日
	 */
	@RequestMapping(value = "/delete/{delIds}", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean delete(@PathVariable(value = "delIds") String delIds) {
		Site site = null;
		int count = 0;
		boolean result = true;
		String[] ids = delIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			site = Site.db().find(Site.class, ids[i]);
			site.setIs_deleted(1);
			Site.db().update(site);
			count++;
		}
		if (count > 0) {
			eventBus.post(new BaseCacheEvent("site", EventType.Delete));
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @Title: checkSiteName
	 * @Description: 验证站点唯一性
	 * @param @param
	 *            name
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月18日
	 */
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkSiteName(@RequestParam("id") Integer id, @RequestParam("name") String name) {
		int sList = siteService.checkName(id, name);
		if (sList > 0) {
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = "/getCurrentSite", method = RequestMethod.GET)
	@ResponseBody
	public Site getCurrentSite(){
		CurrentUser currentUser = currentUserService.getUserDetails();
		Integer currentSite = currentUser.getSiteMap().get("siteId");
		if(null == currentSite){
			currentSite = 1;
		}
		return getById(currentSite);
	}
}
