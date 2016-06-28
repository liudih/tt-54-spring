package com.tomtop.management.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class FoundationService implements IFoundationService {


	@Override
	public String getCurrentMemberID() {
		return getCurrentUserJobNumber();
	}
	public String getCurrentUserJobNumber() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return authentication == null ? "" : authentication.getName();
	}
}
