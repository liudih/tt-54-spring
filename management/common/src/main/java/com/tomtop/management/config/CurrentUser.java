package com.tomtop.management.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.avaje.ebean.config.CurrentUserProvider;

@Component
public class CurrentUser implements CurrentUserProvider {
	@Override
	public Object currentUser() {
		return getCurrentUserJobNumber();
	}
	
	public String getCurrentUserJobNumber() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return authentication == null ? "" : authentication.getName();
	}

}
