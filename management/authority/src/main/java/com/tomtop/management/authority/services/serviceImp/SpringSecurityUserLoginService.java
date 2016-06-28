package com.tomtop.management.authority.services.serviceImp;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.tomtop.management.authority.mapper.impl.UserRepositoryImpl;
import com.tomtop.management.common.MD5;
import com.tomtop.management.ebean.authority.model.AdminRole;
import com.tomtop.management.ebean.authority.model.AdminUser;
import com.tomtop.management.forms.CurrentUser;

@Component
public class SpringSecurityUserLoginService {
	@Autowired
	CurrentUserService currentUserService;
	@Autowired
	private UserRepositoryImpl userRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	private static final String internalHashKeyForAutomaticLoginAfterRegistration = "magicInternalHashKeyForAutomaticLoginAfterRegistration";

	public boolean login(String login, String password) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login,
						password));
		boolean isAuthenticated = isAuthenticated(authentication);
		if (isAuthenticated) {
			SecurityContextHolder.getContext()
					.setAuthentication(authentication);
		}
		return isAuthenticated;
	}

	public boolean login(AdminUser adminUser, List<AdminRole> rolesByUserList) {// 免密码登录方式
		boolean isLoginSuccesfull = false;
		if (adminUser == null || CollectionUtils.isEmpty(rolesByUserList)) {
			return isLoginSuccesfull;
		}
		List<SimpleGrantedAuthority> grantedAuthorities = Lists.newArrayList();
		for (AdminRole adminRole : rolesByUserList) {
			if (StringUtils.isNotEmpty(adminRole.getRoleName())) {
				grantedAuthorities.add(new SimpleGrantedAuthority(adminRole
						.getRoleName()));// 权限配置
			}
		}
		User user = new User(adminUser.getJobNumber(), "protect",
				grantedAuthorities);
		if (user != null) {
			adminUser.setPassword("protect");
			adminUser.setAdmin(true);
			String[] role = {};
			List<String> list=Lists.newArrayList();
			if (CollectionUtils.isNotEmpty(user.getAuthorities())) {
				for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
					if ((role==null || role.length==0)
							&& grantedAuthority != null
							&& StringUtils.isNotEmpty(grantedAuthority
									.getAuthority())) {
						list.add(grantedAuthority.getAuthority());
					}
				}
				role=(String[]) list.toArray(new String[list.size()]);
			}
			CurrentUser userDetails = new CurrentUser(adminUser, role);
			final RememberMeAuthenticationToken rememberMeAuthenticationToken = new RememberMeAuthenticationToken(
					internalHashKeyForAutomaticLoginAfterRegistration,
					userDetails, grantedAuthorities);
			rememberMeAuthenticationToken.setAuthenticated(true);
			SecurityContextHolder.getContext().setAuthentication(
					rememberMeAuthenticationToken);
			String userSessionId = currentUserService.getUserSessionId(adminUser.getJobNumber());
			if(StringUtils.isEmpty(userSessionId)){
				String sessionId=MD5.md5(adminUser.getJobNumber());
				currentUserService.registUser(sessionId, userDetails);
			}
			isLoginSuccesfull = true;
		}
		return isLoginSuccesfull;
	}

	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	public boolean isLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return isAuthenticated(authentication);
	}

	public CurrentUser getLoggedUserDetails() throws Exception {
		CurrentUser loggedUserDetails = null;
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (isAuthenticated(authentication)) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof CurrentUser) {
				loggedUserDetails = ((CurrentUser) principal);
			} else {
				throw new Exception(
						"Expected class of authentication principal is AuthenticationUserDetails. Given: "
								+ principal.getClass());
			}
		}
		return loggedUserDetails;
	}

	private boolean isAuthenticated(Authentication authentication) {
		return authentication != null
				&& !(authentication instanceof AnonymousAuthenticationToken)
				&& authentication.isAuthenticated();
	}

	/*
	 * public User getLoggedUser() { User loggedUser = null;
	 * AuthenticationUserDetails userDetails = getLoggedUserDetails(); if
	 * (userDetails != null) { loggedUser =
	 * userRepository.findById(userDetails.getId()); } return loggedUser; }
	 */
}