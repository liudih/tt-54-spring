package com.tomtop.management.authority.services.serviceImp;

import org.springframework.stereotype.Service;

import com.tomtop.management.authority.services.iservice.ICurrentUserService;
import com.tomtop.management.enums.base.Role;
import com.tomtop.management.forms.CurrentUser;

@Service("currentUserServiceImpl")
public class CurrentUserServiceImpl implements ICurrentUserService {

	@Override
	public boolean canAccessUser(CurrentUser currentUser, int userId) {
		return currentUser != null
				&& (currentUser.getRole() == Role.ADMIN || currentUser.getId() == userId);
	}

}