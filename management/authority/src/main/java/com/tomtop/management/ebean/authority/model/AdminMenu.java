package com.tomtop.management.ebean.authority.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admin_menu")
public class AdminMenu extends UserModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9148659997814695440L;

	@Column(name = "menu_name", nullable = false)
	private String menuName;

	@Column(name = "menu_position", nullable = false)
	private String menuPosition;

	@Column(name = "parent_id", nullable = false)
	private Long parentId;

	@Column(name = "menu_level", nullable = false)
	private Integer menuLevel;

	@Column(name = "menu_style", nullable = false)
	private String menuStyle;

	@Column(name = "menu_url", nullable = false)
	private String menuUrl;

	private String status;

	public String getMenuPosition() {
		return menuPosition;
	}

	public void setMenuPosition(String menuPosition) {
		this.menuPosition = menuPosition;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuStyle() {
		return menuStyle;
	}

	public void setMenuStyle(String menuStyle) {
		this.menuStyle = menuStyle;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
