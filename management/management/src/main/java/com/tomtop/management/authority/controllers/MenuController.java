package com.tomtop.management.authority.controllers;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.api.client.util.Lists;
import com.google.api.client.util.Maps;
import com.tomtop.management.authority.services.iservice.IMenuService;
import com.tomtop.management.authority.services.serviceImp.RoleServiceImpl;
import com.tomtop.management.forms.AdminRoleForm;
import com.tomtop.management.forms.MenuRoleForm;


@Controller
public class MenuController {
	@Autowired
	RoleServiceImpl roleServiceImpl;

	@Autowired
	IMenuService menuService;

	private static Logger log = Logger.getLogger(MenuController.class.getName());

	@RequestMapping(value = "/menu/List", method = RequestMethod.GET)
	public String getMenuList(Authentication authentication, Map<String, Object> model) {

		return "managerMenu";
	}

	@RequestMapping(value = "/role/menu/manager", method = RequestMethod.GET)
	public String getPublicLoginPage(Model model) {
		model.addAttribute("section", "authority/menuRoleMap.ftl");
		return "index";
	}

	@RequestMapping(value = "/role/menu/get", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRoleMenuList(Integer roleId) {
		Map<String, Object> result = Maps.newHashMap();

		AdminRoleForm adminRoleForm = new AdminRoleForm();
		Map<String, Object> queryAdminRole = roleServiceImpl.queryAdminRole(1, 1000, adminRoleForm);
		if (MapUtils.isNotEmpty(queryAdminRole) && CollectionUtils.isNotEmpty((List) queryAdminRole.get("list"))) {

			@SuppressWarnings("unchecked")
			List<AdminRoleForm> list = (List<AdminRoleForm>) queryAdminRole.get("list");
			result.put("roleList", list);
		} else {
			result.put("roleList", null);
		}
		List<MenuRoleForm> allMenu = menuService.getAllMenu(roleId);
		result.put("allMenu", allMenu);
		return result;
	}

	@RequestMapping(value = "/role/menu/map", method = RequestMethod.POST)
	@ResponseBody
	public String updateRoleMenuMap(Integer roleId, String menus) {
		log.debug("updateRoleMenuMap roleId:" + roleId + "   menus:" + menus);
		String result = "";
		if (roleId == null || StringUtils.isEmpty(menus)) {
			log.info("updateRoleMenuMap params null !  roleId:" + roleId + "   menus:" + menus);
			return "fail";
		}

		String[] split = menus.split(",");
		List<Integer> list = Lists.newArrayList();
		for (String str : split) {
			if (StringUtils.isNotEmpty(str)) {
				list.add(Integer.parseInt(str));
			}
		}
		// 更新映射关系
		result = menuService.updateRoleMenuMap(roleId, list);

		return result;
	}

	/**
	 * 
	 * @Title: home
	 * @Description: TODO(菜单-主页)
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月15日
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "index";
	}

	/**
	 * 
	 * @Title: baseModul
	 * @Description: TODO(菜单-基础模块)
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月15日
	 */
	@RequestMapping(value = "/base", method = RequestMethod.GET)
	public String baseModul() {
		return "index";
	}

	/**
	 * 
	 * @Title: indexConfig
	 * @Description: TODO(菜单-首页设置)
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月15日
	 */
	@RequestMapping(value = "/indexConfig", method = RequestMethod.GET)
	public String indexConfig() {
		return "index";
	}

	/**
	 * 
	 * @Title: indexConfigAuthority
	 * @Description: TODO(菜单-权限管理)
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月15日
	 */
	@RequestMapping(value = "/authority", method = RequestMethod.GET)
	public String indexConfigAuthority() {
		return "index";
	}

	/**
	 * 
	 * @Title: baseDesign
	 * @Description: TODO(菜单-基础设计)
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月15日
	 */
	@RequestMapping(value = "/base/baseDesign", method = RequestMethod.GET)
	public String baseDesign() {
		return "index";
	}

	/**
	 * 
	 * @Title: baseLocalize
	 * @Description: TODO(菜单-本地化)
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月15日
	 */
	@RequestMapping(value = "/base/baseLocalize", method = RequestMethod.GET)
	public String baseLocalize() {
		return "index";
	}

	/**
	 * 
	 * @Title: indexConfigSearch
	 * @Description: TODO(菜单-搜索功能)
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月15日
	 */
	@RequestMapping(value = "/indexConfig/search", method = RequestMethod.GET)
	public String indexConfigSearch() {
		return "index";
	}

	/**
	 * 
	 * @Title: indexConfigGoods
	 * @Description: TODO(菜单-商品促销)
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月15日
	 */
	@RequestMapping(value = "/indexConfig/goods", method = RequestMethod.GET)
	public String indexConfigGoods() {
		return "index";
	}

	/**
	 * 
	 * @Title: indexConfigBrand
	 * @Description: TODO(菜单-品牌信息管理)
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月15日
	 */
	@RequestMapping(value = "/indexConfig/brand", method = RequestMethod.GET)
	public String indexConfigBrand() {
		return "index";
	}

	/**
	 * 
	 * @Title: indexConfigCategory
	 * @Description: TODO(菜单-特色类目)
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月15日
	 */
	@RequestMapping(value = "/indexConfig/category", method = RequestMethod.GET)
	public String indexConfigCategory() {
		return "index";
	}

	/**
	 * 
	 * @Title: indexConfigSale
	 * @Description: TODO(菜单-最近卖出品管理)
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月15日
	 */
	@RequestMapping(value = "/indexConfig/sale", method = RequestMethod.GET)
	public String indexConfigSale() {
		return "index";
	}

	/**
	 * 
	 * @Title: indexConfigCustomerVoice
	 * @Description: TODO(菜单-Customer Voice)
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月15日
	 */
	@RequestMapping(value = "/indexConfig/customerVoice", method = RequestMethod.GET)
	public String indexConfigCustomerVoice() {
		return "index";
	}
	
	/**
	 * 
	 * @Title: systemConfig
	 * @Description: TODO(系统管理)
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年2月23日
	 */
	@RequestMapping(value = "/systemConfig", method = RequestMethod.GET)
	public String systemConfig() {
		return "index";
	}
	
	/**
	 * 
	 * @Title: freightChargeManage
	 * @Description: TODO(物流运费管理)
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年2月23日
	 */
	@RequestMapping(value = "/shipping/freightChargeManage", method = RequestMethod.GET)
	public String freightChargeManage() {
		return "index";
	}
	
	/**
	 * 
	    * @Title: market
	    * @Description: 市场营销
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	    * @author liuxin
	    * @date 2016年3月8日
	 */
	@RequestMapping(value = "/market", method = RequestMethod.GET)
	public String market() {
		return "index";
	}
	
	/**
	 * 
	    * @Title: dataFeed
	    * @Description: datafeed
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	    * @author liuxin
	    * @date 2016年3月8日
	 */
	@RequestMapping(value = "/market/datafeeds", method = RequestMethod.GET)
	public String dataFeed() {
		return "index";
	}
}
