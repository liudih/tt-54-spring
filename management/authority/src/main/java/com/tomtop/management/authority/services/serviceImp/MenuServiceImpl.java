package com.tomtop.management.authority.services.serviceImp;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.util.Lists;
import com.google.common.collect.Maps;
import com.tomtop.management.authority.mapper.IRoleMenuRepository;
import com.tomtop.management.authority.services.iservice.IMenuService;
import com.tomtop.management.ebean.authority.model.AdminMenu;
import com.tomtop.management.forms.MenuDynamic;
import com.tomtop.management.forms.MenuRoleForm;

@Service
public class MenuServiceImpl implements IMenuService {

	private static Logger log = Logger.getLogger(MenuServiceImpl.class
			.getName());

	@Autowired
	IRoleMenuRepository roleMenuRepository;

	@Override
	public Map<String,MenuDynamic> getDynamicMenu(Long userId) {
		
		Map<String,MenuDynamic> result=Maps.newHashMap();
		try {
			
			MenuDynamic menuDynamic = new MenuDynamic();
			List<AdminMenu> menusList = roleMenuRepository
					.getMenusByUserId(userId);
			if (CollectionUtils.isNotEmpty(menusList)) {
				// 采用递归，处理当前节点和其子节点
				AdminMenu currentMenu = new AdminMenu();
				currentMenu.setId(0L);
				currentMenu.setMenuLevel(0);
				menuDynamic.setCurrentMenu(currentMenu);
				Map<String, MenuDynamic> map = Maps.newTreeMap();
				menuDynamic.setChildMenuMap(map);

				getMenuChild(menuDynamic, menusList);
				
				Map<String, MenuDynamic> childMenuMap = menuDynamic.getChildMenuMap();
				//设置好菜单
				result.put("baseMode", childMenuMap.get("010000"));
				result.put("indexSet", childMenuMap.get("020000"));
				result.put("authoritySet", childMenuMap.get("030000"));
				result.put("systemSet", childMenuMap.get("040000"));
				result.put("market", childMenuMap.get("050000"));
			}
		} catch (Exception e) {
			log.error("MenuServiceImpl getDynamicMenu error", e);
		}
		return result;
	}
	@Override
	public List<MenuRoleForm> getAllMenu(Integer roleId){
		List<MenuRoleForm> formList=Lists.newArrayList();
		try {
			Map<Long, Object> menuMapByRoleId = getMenuMapByRoleId(roleId);//指定用户已授权的角色
			List<AdminMenu> menusList = roleMenuRepository.getAllMenu();//全部动态菜单
			if (CollectionUtils.isNotEmpty(menusList)) {
				for(AdminMenu adminMenu : menusList){
					MenuRoleForm menuRoleForm=new MenuRoleForm();
					menuRoleForm.setAdminMenu(adminMenu);
					if(menuMapByRoleId.containsKey(adminMenu.getId())){
						menuRoleForm.setRoleChecked(true);
					}
					formList.add(menuRoleForm);
				}
			}
		} catch (Exception e) {
			log.error("MenuServiceImpl getDynamicMenu error", e);
		}
		return formList;
	}
	
	public Map<Long,Object> getMenuMapByRoleId(int roleId){
		Map<Long,Object> result=Maps.newHashMap();
		try {
			if(roleId==-1){
				return result;//
			}
			List<AdminMenu> menusList = roleMenuRepository.getMenusByRoleId(roleId);
			if (CollectionUtils.isNotEmpty(menusList)) {
				for(AdminMenu adminMenu : menusList){
					
					result.put(adminMenu.getId(),null);
				}
			}
		} catch (Exception e) {
			log.error("getMenuMapByRoleId  error", e);
		}
		return result;
	}
	
	
	private void getMenuChild(MenuDynamic currentMenu,
			List<AdminMenu> childMenuList) {
		if (CollectionUtils.isEmpty(childMenuList)) {
			return;
		}
		Map<String, MenuDynamic> childMenuMap = currentMenu.getChildMenuMap();
		AdminMenu currentMenu2 = currentMenu.getCurrentMenu();
		AdminMenu item = childMenuList.get(0);

		// 如果被处理的父节点ID为当前节点ID
		if(item==null || item.getMenuLevel()==null){
			log.info("getMenuChild currend Level end");
		}else if (currentMenu2.getId() == item.getParentId()) {
			MenuDynamic childMenuDynamic = new MenuDynamic();// 子节点
			childMenuDynamic.setCurrentMenu(item);
			Map<String, MenuDynamic> childMap = new TreeMap<String, MenuDynamic>();
			childMenuDynamic.setChildMenuMap(childMap);
			childMenuMap.put(item.getMenuPosition(), childMenuDynamic);

			List<AdminMenu> subList = childMenuList.subList(1,
					childMenuList.size());
			getMenuChild(childMenuDynamic, subList);

			getMenuChild(currentMenu, subList);
		} else if (item.getMenuLevel()>=currentMenu2.getMenuLevel()) {
			if(childMenuList.size()>1){
				getMenuChild(currentMenu,
						childMenuList.subList(1, childMenuList.size()));
			}
		}
	}

	@Override
	public String updateRoleMenuMap(Integer roleId,List<Integer> menuIdList){
		log.debug("updateRoleMenuMap roleId:"+roleId+"  menuIdList:"+menuIdList);
		return roleMenuRepository.updateRoleMenuMap(roleId, menuIdList);
		
	}
}
