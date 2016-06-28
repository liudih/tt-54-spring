package com.tomtop.management.authority.services.iservice;

import com.tomtop.management.forms.CurrentUser;

public interface ICurrentUserService {
	    boolean canAccessUser(CurrentUser currentUser, int userId);
}
