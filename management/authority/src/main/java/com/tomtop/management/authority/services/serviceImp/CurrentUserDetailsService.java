package com.tomtop.management.authority.services.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tomtop.management.authority.mapper.impl.UserRoleRepositoryImpl;
import com.tomtop.management.authority.services.iservice.IUserService;
import com.tomtop.management.ebean.authority.model.AdminRole;
import com.tomtop.management.ebean.authority.model.AdminUser;
import com.tomtop.management.enums.base.Role;
import com.tomtop.management.forms.CurrentUser;

@Service
public class CurrentUserDetailsService implements UserDetailsService {

	private final IUserService userService;

	@Autowired
	UserRoleRepositoryImpl userRoleRepositoryImpl;
	
	@Autowired
	public CurrentUserDetailsService(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public CurrentUser loadUserByUsername(String cjobnumber)
			throws UsernameNotFoundException {
		Optional<AdminUser> userByEmail = userService
				.getUserByJobnumber(cjobnumber);
		if (userByEmail == null || !userByEmail.isPresent()) {
			throw new UsernameNotFoundException(String.format(
					"User with email=%s was not found", cjobnumber));
		}
		
        AdminUser user = userByEmail.get();
		List<AdminRole> rolesByUserList = userRoleRepositoryImpl.getRolesByUserId(user.getId());
		String[] roleName={};
		if(CollectionUtils.isNotEmpty(rolesByUserList)){
			//AdminRole adminRole = rolesByUserList.get(0);
			List<String> list=Lists.transform(rolesByUserList, i->i.getRoleName());
			roleName = (String[])list.toArray(new String[list.size()]);
			//roleName=adminRole.getRoleName();
			
			
		}
		return new CurrentUser(user,roleName);
	}

}
