package com.tomtop.management.ebean.authority.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_site_map")
public class UserSiteMap extends UserModel {

	private static final long serialVersionUID = 5957000622103104902L;
	private Long user_id;
	private Long site_id;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getSite_id() {
		return site_id;
	}

	public void setSite_id(Long site_id) {
		this.site_id = site_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
