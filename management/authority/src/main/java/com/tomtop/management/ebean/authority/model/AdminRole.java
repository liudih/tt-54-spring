package com.tomtop.management.ebean.authority.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "admin_role")
public class AdminRole   extends  UserModel  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1615657998907077396L;
	
	@Column(name = "role_name", nullable = false)
	private String roleName;
	
	private String status;
	

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
