package com.tomtop.management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avaje.ebean.config.CurrentUserProvider;
import com.tomtop.management.authority.services.serviceImp.CurrentUserService;

@Component
public class CurrentUser implements CurrentUserProvider {
	@Autowired
	CurrentUserService currentUserService;
	@Override
	public Object currentUser() {
		return currentUserService.getCurrentUserJobNumber();
	}

}
