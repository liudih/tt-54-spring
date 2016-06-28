package com.tomtop.management.authority.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tomtop.management.authority.mapper.impl.RoleMenuRepositoryImpl;
import com.tomtop.management.authority.mapper.impl.UserRoleRepositoryImpl;
import com.tomtop.management.authority.services.serviceImp.CurrentUserService;
import com.tomtop.management.ebean.authority.model.AdminRole;
import com.tomtop.management.forms.CurrentUser;

@Component
public class UrlInterceptor implements HandlerInterceptor,
		ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		if (applicationContext != null) {
			
			CurrentUserService currentUserService = (CurrentUserService) applicationContext
					.getBean("currentUserService");
			RoleMenuRepositoryImpl roleMenuRepositoryImpl = (RoleMenuRepositoryImpl) applicationContext
					.getBean("roleMenuRepositoryImpl");
			String jobNumber = currentUserService.getCurrentUserJobNumber();
			CurrentUser userDetails = currentUserService.getUserDetails();
			if (userDetails == null || StringUtils.isEmpty(jobNumber)) {
				return true;
			}
			String requestURI = request.getRequestURI();
			List<String> urlRoleName = roleMenuRepositoryImpl
					.getRoleNameByPath(requestURI,jobNumber);
			if (CollectionUtils.isNotEmpty(urlRoleName)) {
				UserRoleRepositoryImpl userRoleRepositoryImpl = (UserRoleRepositoryImpl) applicationContext
						.getBean("userRoleRepositoryImpl");
				List<AdminRole> rolesByUserList = userRoleRepositoryImpl
						.getRolesByUserId(userDetails.getId());
				if (CollectionUtils.isNotEmpty(rolesByUserList)) {
					boolean canAccess = false;
					for (AdminRole adminRole : rolesByUserList) {
						if (urlRoleName.contains(adminRole.getRoleName())) {
							canAccess = true;
						}

					}
					if (!canAccess) {// 如果是动态路径配置，路径权限和用户权限受限时候，重定向到登录页
						response.sendRedirect("/login");
					}
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		applicationContext = arg0;

	}

}