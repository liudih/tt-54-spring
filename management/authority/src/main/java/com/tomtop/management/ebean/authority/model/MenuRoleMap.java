package com.tomtop.management.ebean.authority.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "menu_role_map")
public class MenuRoleMap extends UserModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6557916790777689173L;

	@Column(name = "menu_id", nullable = false)
	private Integer menuId;

	@Column(name = "role_id", nullable = false)
	private Integer roleId;

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
