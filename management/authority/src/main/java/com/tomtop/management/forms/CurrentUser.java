package com.tomtop.management.forms;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.authority.AuthorityUtils;

import com.google.common.collect.Lists;
import com.tomtop.management.ebean.authority.model.AdminUser;
import com.tomtop.management.enums.base.Role;

public class CurrentUser extends
		org.springframework.security.core.userdetails.User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3052640653164995147L;
	private AdminUser user;
	private Map<String, Integer> siteMap = new HashMap<String, Integer>();

	public CurrentUser(AdminUser user1, String[] roleNames) {
		super(user1.getJobNumber(), user1.getPassword(),
				user1.isAdmin() ? AuthorityUtils.createAuthorityList(roleNames)
						: Lists.newArrayList());
		this.user = user1;
	}

	public AdminUser getUser() {
		return user;
	}

	public String getJobNumber() {
		return user.getJobNumber();
	}

	public String getUserName() {
		return user.getUserName();
	}

	public Long getId() {
		return user.getId();
	}

	public Role getRole() {
		return Role.ADMIN;
	}

	public Map<String, Integer> getSiteMap() {
		return siteMap;
	}

	public void setSiteMap(Map<String, Integer> siteMap) {
		this.siteMap = siteMap;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setUser(AdminUser user) {
		this.user = user;
	}

}
