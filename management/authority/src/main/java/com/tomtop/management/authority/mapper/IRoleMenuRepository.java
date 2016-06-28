package com.tomtop.management.authority.mapper;

import java.util.List;

import com.avaje.ebean.Transaction;
import com.tomtop.management.ebean.authority.model.AdminMenu;
import com.tomtop.management.ebean.authority.model.MenuRoleMap;


public interface IRoleMenuRepository{
	
	public List<AdminMenu> getMenusByRoleId(int  roleId);
	
	public List<AdminMenu> getMenusByUserId(Long  userId);
	
	public AdminMenu getMenuById(int  menuId);

	List<String> getRoleNameByPath(String path,String jobNumber);

	List<AdminMenu> getAllMenu();

	void deleteMenuByRoleIdMap(List<MenuRoleMap> list,Transaction transaction);

	void addMenuByRoleIdMap(List<MenuRoleMap> list,Transaction transaction);
	
	String updateRoleMenuMap(Integer roleId,List<Integer> list);

	List<MenuRoleMap> getMenuByRoleIdMap(Integer roleId);

	void updateMenuList(List<MenuRoleMap> list, Transaction transaction);
}

