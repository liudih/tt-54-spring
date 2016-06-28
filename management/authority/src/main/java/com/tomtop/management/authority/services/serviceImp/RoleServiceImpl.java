package com.tomtop.management.authority.services.serviceImp;

import java.util.List;
import java.util.Map;

import javax.persistence.OptimisticLockException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.util.Lists;
import com.google.api.client.util.Maps;
import com.tomtop.management.authority.mapper.impl.AdminRoleRepositoryImpl;
import com.tomtop.management.ebean.authority.model.AdminRole;
import com.tomtop.management.forms.AdminRoleForm;
@Service
public class RoleServiceImpl {
	
	private static Logger log=Logger.getLogger(RoleServiceImpl.class.getName());
	@Autowired
	AdminRoleRepositoryImpl adminRoleRepositoryImpl;
	public Map<String,Object> queryAdminRole(int pageNo, int pageSize, AdminRoleForm ar) {
		Map<String,Object> map=Maps.newHashMap();
		try{
			int queryAdminRoleSize = adminRoleRepositoryImpl.queryAdminRoleSize(ar);
			if(queryAdminRoleSize>0){
				
				List<AdminRoleForm> queryAdminRole = adminRoleRepositoryImpl.queryAdminRole(pageNo, pageSize, ar);
				map.put("list", queryAdminRole);
			}else{
				map.put("list", null);
			}
			map.put("count", queryAdminRoleSize);
			map.put("pageNo", pageNo);
			map.put("pageSize", pageSize);
		}catch(Exception e){
			log.error("queryAdminRole error",e);
		}
		return map;
	}
	
	public String addAdminRole(AdminRole ar) {
		String result="";
		try {
			adminRoleRepositoryImpl.addAdminRole(ar);
			result="Y";
		} catch (OptimisticLockException e) {
			log.error("addAdminRole error",e);
			result="E";
		}
		return result;
	}

	public AdminRole getAdminRoleById(Long id) {
		return adminRoleRepositoryImpl.getAdminRoleById(id);
	}

	public String updateOrSaveAdminRole(AdminRole bc) throws Exception {
		String result="";
		AdminRole adminRole =getAdminRoleById(bc.getId());
		if(adminRole==null){
			result=addAdminRole(bc);
		}else{
			try{
				
				adminRoleRepositoryImpl.updateAdminRole(bc);
				result="Y";
			}catch(Exception e){
				log.error("updateOrSaveAdminRole error",e);
				result="E";
			}
		}
		return result;
	}

	public String delete(String ids) {
		String result="";
		try{
			String[] split = ids.split(",");
			List<Long> list=Lists.newArrayList();
			for(String str : split){
				if(StringUtils.isNotEmpty(str)){
					list.add(new Long(str));
				}
			}
			AdminRole.db().deleteAll(AdminRole.class, list);
			result="Y";
		}catch(Exception e){
			log.error("delete error",e);
			result="E";
		}
		return result;
	}
	public boolean exist(Integer id,String roleName) {
		
		return adminRoleRepositoryImpl.exist(id,roleName);
	}
	
}
