package com.tomtop.management.authority.controllers;

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
import com.tomtop.management.ebean.authority.model.AdminRole;
import com.tomtop.management.ebean.authority.model.AdminUser;
import com.tomtop.management.ebean.authority.model.UserRoleMap;
import com.tomtop.management.ebean.authority.model.UserSiteMap;
import com.tomtop.management.ebean.manage.model.Site;
import com.tomtop.management.event.BaseCacheEvent;
import com.tomtop.management.event.EventType;
import com.tomtop.management.service.model.UserObject;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.services.UserService;

@Controller
@RequestMapping("/authority/user")
public class UserManageController {

	@Autowired
	CommonService commonService;
	@Autowired
	UserService userService;
	@Autowired
	EventBus eventBus;
	
	/**
	 * 
	 * @Title: view
	 * @Description: TODO(用户管理页面显示)
	 * @param @param model
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Model model){
		List<AdminRole> adminRoleList = commonService.getAllRole();
		List<Site> siteList = commonService.getAllSite();
		model.addAttribute("adminRoleList", adminRoleList);
		model.addAttribute("siteList", siteList);
		model.addAttribute("section", "authority/userManage.ftl");
		return "index";
	}
	
	/**
	 * 
	 * @Title: getUserPage
	 * @Description: TODO(分页查询用户)
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param user
	 * @param @param role
	 * @param @return    
	 * @return Page<UserObject>    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public Page<UserObject> getUserPage(int pageNo, int pageLimit, AdminUser user, Long role) {
		return userService.getUserPage(pageNo, pageLimit, user, role);
	}
	
	/**
	 * 
	 * @Title: getRandomPassword
	 * @Description: TODO(获取随机密码)
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	@RequestMapping(value = "/getRandomPassword", method = RequestMethod.GET)
	@ResponseBody
	public String getRandomPassword(){
		return userService.getRandomPassword();
	}
	
	/**
	 * 
	 * @Title: addUser
	 * @Description: TODO(新增用户)
	 * @param @param u
	 * @param @param roleIds
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String addUser(AdminUser u, String roleIds) {
		int addCount = userService.addUser(u, roleIds);
		if (addCount > 0) {
			eventBus.post(new BaseCacheEvent("user", EventType.Add));
			return "success";
		} else {
			return "failed";
		}
	}
	
	/**
	 * 
	 * @Title: getUserById
	 * @Description: TODO(通过id查询用户)
	 * @param @param id
	 * @param @return    
	 * @return AdminUser    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public AdminUser getUserById(@PathVariable(value = "id") Integer id) {
		return userService.getUserById(id);
	}
	
	/**
	 * 
	 * @Title: updateUser
	 * @Description: TODO(修改用户)
	 * @param @param u
	 * @param @param roleIds
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateUser(AdminUser u, String roleIds) {
		int updateCount = userService.updateUser(u, roleIds);
		if (updateCount > 0) {
			eventBus.post(new BaseCacheEvent("user", EventType.Update));
			return "success";
		} else {
			return "failed";
		}
	}
	
	/**
	 * 
	 * @Title: userNameUniqueValidate
	 * @Description: TODO(用户名唯一校验)
	 * @param @param id
	 * @param @param userName
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	@RequestMapping(value = "/userNameUV", method = RequestMethod.POST)
	@ResponseBody
	public String userNameUniqueValidate(Integer id, String userName) {
		int findCount = userService.userNameUniqueValidate(id, userName);
		if (findCount > 0) {
			return "false";
		} else {
			return "true";
		}
	}
	
	/**
	 * 
	 * @Title: deleteUser
	 * @Description: TODO(删除用户)
	 * @param @param Ids
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	@RequestMapping(value = "/delete/{Ids}", method = RequestMethod.GET)
	@ResponseBody
	public String deleteUser(@PathVariable(value = "Ids") String Ids) {
		int deleteCount = userService.deleteUser(Ids);
		if (deleteCount > 0) {
			eventBus.post(new BaseCacheEvent("user", EventType.Delete));
			return "success";
		} else {
			return "fail";
		}
	}
	
	/**
	 * 
	 * @Title: addRoleAndSite
	 * @Description: TODO(新增用户角色映射和用户站点映射)
	 * @param @param userName
	 * @param @param roleIds
	 * @param @param siteIds
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	@RequestMapping(value = "/addRoleAndSite", method = RequestMethod.POST)
	@ResponseBody
	public String addRoleAndSite(String userName,String roleIds,String siteIds){
		int addCount = userService.addRoleAndSite(userName,roleIds,siteIds);
		if (addCount > 0) {
			eventBus.post(new BaseCacheEvent("role", EventType.Add));
			return "success";
		} else {
			return "failed";
		}
	}
	
	/**
	 * 
	 * @Title: updateRoleAndSite
	 * @Description: TODO(修改用户角色映射和用户站点映射)
	 * @param @param userName
	 * @param @param roleIds
	 * @param @param siteIds
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	@RequestMapping(value = "/updateRoleAndSite", method = RequestMethod.POST)
	@ResponseBody
	public String updateRoleAndSite(String userName,String roleIds,String siteIds){
		int addCount = userService.updateRoleAndSite(userName,roleIds,siteIds);
		if (addCount > 0) {
			eventBus.post(new BaseCacheEvent("role", EventType.Add));
			return "success";
		} else {
			return "failed";
		}
	}
	
	/**
	 * 
	 * @Title: getURListById
	 * @Description: TODO(通过用户Id查询用户角色映射)
	 * @param @param id
	 * @param @return    
	 * @return List<UserRoleMap>    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	@RequestMapping(value = "/getRole/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<UserRoleMap> getURListById(@PathVariable(value = "id") Long id) {
		return userService.getURListById(id);
	}
	
	/**
	 * 
	 * @Title: getUSListById
	 * @Description: TODO(通过用户id查询用户站点映射)
	 * @param @param id
	 * @param @return    
	 * @return List<UserSiteMap>    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	@RequestMapping(value = "/getSite/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<UserSiteMap> getUSListById(@PathVariable(value = "id") Long id) {
		return userService.getUSListById(id);
	}
	
	/**
	 * 
	 * @Title: resetPassword
	 * @Description: TODO(重置密码)
	 * @param @param u
	 * @param @return    
	 * @return boolean    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月19日
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public boolean resetPassword(AdminUser u){
		return userService.resetPassword(u);
	}
	
	/**
	 * 
	 * @Title: jobNumberUniqueValidate
	 * @Description: TODO(工号唯一校验)
	 * @param @param id
	 * @param @param jobNumber
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月20日
	 */
	@RequestMapping(value = "/jobNumberUV", method = RequestMethod.POST)
	@ResponseBody
	public String jobNumberUniqueValidate(Integer id, String jobNumber) {
		int findCount = userService.jobNumberUniqueValidate(id, jobNumber);
		if (findCount > 0) {
			return "false";
		} else {
			return "true";
		}
	}
}
