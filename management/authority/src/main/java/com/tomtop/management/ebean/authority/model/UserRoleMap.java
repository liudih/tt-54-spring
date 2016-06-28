package com.tomtop.management.ebean.authority.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "user_role_map")
public class UserRoleMap extends  UserModel  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3138787068776676662L;

	
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	@Column(name = "role_id", nullable = false)
	private Long roleId;


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
