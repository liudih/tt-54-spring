package com.tomtop.management.forms;

import com.tomtop.management.ebean.authority.model.AdminMenu;


public class MenuRoleForm{
	private boolean roleChecked;//是否选中

	AdminMenu adminMenu;		//菜单
	public AdminMenu getAdminMenu() {
		return adminMenu;
	}

	public void setAdminMenu(AdminMenu adminMenu) {
		this.adminMenu = adminMenu;
	}

	public boolean isRoleChecked() {
		return roleChecked;
	}

	public void setRoleChecked(boolean roleChecked) {
		this.roleChecked = roleChecked;
	}
	
}
