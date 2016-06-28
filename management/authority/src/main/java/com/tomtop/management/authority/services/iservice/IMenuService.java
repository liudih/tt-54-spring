package com.tomtop.management.authority.services.iservice;

import java.util.List;
import java.util.Map;

import com.tomtop.management.forms.MenuDynamic;
import com.tomtop.management.forms.MenuRoleForm;


public interface IMenuService {
	
	public   Map<String,MenuDynamic>  getDynamicMenu(Long userId);

	List<MenuRoleForm> getAllMenu(Integer roleId);

	String updateRoleMenuMap(Integer roleId, List<Integer> menuIdList);


}
