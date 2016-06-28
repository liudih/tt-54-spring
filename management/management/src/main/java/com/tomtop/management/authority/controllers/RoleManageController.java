package com.tomtop.management.authority.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.OptimisticLockException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.client.util.Maps;
import com.tomtop.management.authority.services.serviceImp.RoleServiceImpl;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.authority.model.AdminRole;
import com.tomtop.management.forms.AdminRoleForm;
import com.tomtop.management.service.model.UserObject;

@Controller
@RequestMapping("/authority/role")
public class RoleManageController {

	private static Logger log = Logger.getLogger(RoleManageController.class
			.getName());
	@Autowired
	RoleServiceImpl roleServiceImpl;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Model model) {
		model.addAttribute("section", "authority/adminRoleManage.ftl");
		return "index";
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public Page<AdminRoleForm>  queryAdminRole(int pageNo, int pageSize,
			AdminRoleForm ar) {
		Page<AdminRoleForm> pag=new Page<AdminRoleForm>();
		log.debug("queryAdminRole params null pageNo:" + pageNo + "  pageSize:"
				+ pageSize);
		if (pageNo < 1 || pageNo < 1) {

			log.info("queryAdminRole params null pageNo:" + pageNo
					+ "  pageSize:" + pageSize);
			return null;
		}
		Map<String, Object> queryAdminRole = roleServiceImpl.queryAdminRole(pageNo, pageSize, ar);
		
		pag.setCount((Integer)queryAdminRole.get("count"));
		pag.setLimit(pageSize);
		pag.setList((List<AdminRoleForm>)queryAdminRole.get("list"));
		pag.setPageNo(pageNo);
		return pag;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String addAdminRole(AdminRole ar) {
		String result = "";
		try {
			if (ar == null || StringUtils.isEmpty(ar.getRoleName())) {
				log.info("addAdminRole params null ar:" + ar);
				return "F";
			}
			ar.setStatus("1");
			ar.setWhenCreated(new Timestamp(new Date().getTime()));

			log.debug("addAdminRole  ar:" + ar);
			result = roleServiceImpl.addAdminRole(ar);
		} catch (OptimisticLockException e) {
			log.error("addAdminRole error", e);
			result = "E";
		}
		return result;
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public AdminRole getAdminRoleById(@PathVariable(value = "id") Long id) {
		log.debug("getAdminRoleById  id:" + id);
		return roleServiceImpl.getAdminRoleById(id);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateOrSaveAdminRole(AdminRole bc) throws Exception {
		if (bc == null || bc.getId() == null) {
			log.info("updateOrSaveAdminRole params null bc:" + bc);
			return "F";
		}
		log.debug("updateOrSaveAdminRole  bc:" + bc);
		return roleServiceImpl.updateOrSaveAdminRole(bc);
	}

	@RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable(value = "ids") String ids) {
		log.debug("delete  id:" + ids);
		return roleServiceImpl.delete(ids);
	}
	@RequestMapping(value = "/exist", method = RequestMethod.POST)
	@ResponseBody
	public String roleExist(Integer id,String roleName) {
		String result = "false";
		try {
			if (StringUtils.isEmpty(roleName)) {
				log.info("roleExist params null roleName:" + roleName);
				return "false";
			}

			log.debug("roleExist  roleName:" +roleName);
			if(roleServiceImpl.exist(id,roleName)){
				result = "false";
			}else{
				result = "true";
			}
		} catch (OptimisticLockException e) {
			log.error("roleExist error", e);
			result = "false";
		}
		return result;
	}
}
