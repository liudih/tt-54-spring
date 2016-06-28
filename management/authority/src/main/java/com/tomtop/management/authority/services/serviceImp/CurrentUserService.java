package com.tomtop.management.authority.services.serviceImp;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Service;

import com.tomtop.management.common.MD5;
import com.tomtop.management.enums.base.Role;
import com.tomtop.management.forms.CurrentUser;

@Service
public class CurrentUserService implements ApplicationContextAware{

	private static Logger log=Logger.getLogger(CurrentUserService.class.getName());
	
	@Autowired
	CheckLoginUser checkLoginUser;
	
	SessionRegistryImpl sessionRegister;
	private  ApplicationContext applicationContext;
	public String getCurrentUserJobNumber() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return authentication == null ? "" : authentication.getName();
	}

	public Role getCurrentUserRole() {
		CurrentUser userDetails = getUserDetails();
		return userDetails == null ? null : userDetails.getRole();
	}

	public String getCurrentUserName() {
		CurrentUser userDetails = getUserDetails();
		return userDetails == null ? "" : userDetails.getUserName();
	}

	public CurrentUser getUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof String) {
			return null;
		}
		return principal == null ? null : (CurrentUser) principal;
	}

	public void registUser(String sessionId,CurrentUser principal){
		sessionRegister.registerNewSession(sessionId, principal);
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
		
	}
	
	public SessionRegistryImpl getSessionRegistryImpl(){
		Object bean = applicationContext.getBean("sessionRegistry");
		if(bean==null){
			return null;
		}
		return (SessionRegistryImpl)bean;
	}
	
	public String getUserSessionId(String jobNumber){
		
		if(StringUtils.isEmpty(jobNumber)){
			return null;
		}
		sessionRegister=getSessionRegistryImpl();
    	List<Object> allPrincipals = sessionRegister.getAllPrincipals();
    	
    	String sessionId="";
    	if(CollectionUtils.isNotEmpty(allPrincipals)){
    		
    		for(Object object : allPrincipals){
    			if(object instanceof CurrentUser){
    				log.debug("getUserSessionId  CurrentUser object:"+object);
    				
    				CurrentUser currentUser=(CurrentUser)object;
    				List<SessionInformation> allSessions = sessionRegister.getAllSessions(object, true);
    				if(jobNumber.equals(currentUser.getUsername()) && CollectionUtils.isNotEmpty(allSessions)){
    					for(SessionInformation sessionInformation : allSessions){
    						sessionId = sessionInformation.getSessionId();
    						log.info("------------>getUserSessionId sessionId:"+sessionId);
    					}
    				}
    				
    			}else{
    				log.debug("getUserSessionId object:"+object);
    			}
    		}
    		
    	}
    	return sessionId;
	}
}
