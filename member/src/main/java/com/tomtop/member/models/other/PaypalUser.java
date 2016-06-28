package com.tomtop.member.models.other;

import java.io.Serializable;

public class PaypalUser implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private String userId;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
