package com.tomtop.management.services.aop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.google.common.collect.Lists;
import com.tomtop.management.authority.services.serviceImp.CurrentUserService;
import com.tomtop.management.ebean.authority.model.UserSiteMap;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.forms.CurrentUser;
import com.tomtop.management.services.UserService;

/**
 * @ClassName: ClientMonitor
 * @Description: 权限设置限制 客户端访问
 * @author fcl
 * @date 2016年1月19日
 *
 */
@Aspect
@Component
public class ClientMonitor {
	@Autowired
	UserService uService;
	@Autowired
	CurrentUserService currentUserService;

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Around("execution(* com.tomtop.management.services.CommonService.getAllClient(..))")
	public Object clientsFilter(ProceedingJoinPoint joinPoint) {
		Object ob = null;
		try {
			ob = joinPoint.proceed();
			if (ob instanceof List) {
				List<Client> list = (List<Client>) ob;
				CurrentUser currentUser = currentUserService.getUserDetails();
				/*List<UserSiteMap> usList = uService.getUSListById(currentUser.getUser().getId());
				List<Long> siteIdList = Lists.transform(usList, us -> us.getSite_id());*/
				List<Client> clientList = new ArrayList<Client>();
				Integer currentSite = currentUser.getSiteMap().get("siteId");
				if(null == currentSite){
					currentSite = 1;
				}
				for(int i=0;i<list.size();i++){
					/*for(Long siteId : siteIdList){
						if(Long.valueOf(list.get(i).getSite_id()).equals(siteId)){
							clientList.add(list.get(i));
							break;
						}
					}*/
					if(list.get(i).getSite_id().equals(currentSite)){
						clientList.add(list.get(i));
					}
				}
				return clientList;
			}
		} catch (Throwable e) {
			logger.error("nonitor com.tomtop.management.services.CommonService.getAllClient error: ", e);
		}
		return ob;
	}

}
