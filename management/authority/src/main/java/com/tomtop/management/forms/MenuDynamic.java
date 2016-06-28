package com.tomtop.management.forms;

import java.util.Map;

import com.tomtop.management.ebean.authority.model.AdminMenu;


public class MenuDynamic {
	private AdminMenu currentMenu;
    private Map<String,MenuDynamic> childMenuMap;
	public AdminMenu getCurrentMenu() {
		return currentMenu;
	}
	public void setCurrentMenu(AdminMenu currentMenu) {
		this.currentMenu = currentMenu;
	}
	public Map<String, MenuDynamic> getChildMenuMap() {
		return childMenuMap;
	}
	public void setChildMenuMap(Map<String, MenuDynamic> childMenuMap) {
		this.childMenuMap = childMenuMap;
	}
	@Override
	public String toString() {
		return "MenuDynamic [currentMenu=" + currentMenu + ", childMenuMap="
				+ childMenuMap + "]";
	}
    

}
