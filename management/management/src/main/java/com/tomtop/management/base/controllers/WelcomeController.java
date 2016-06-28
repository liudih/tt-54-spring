/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tomtop.management.base.controllers;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tomtop.management.authority.services.iservice.IMenuService;
import com.tomtop.management.authority.services.serviceImp.CurrentUserService;
import com.tomtop.management.forms.CurrentUser;
import com.tomtop.management.services.LayoutEnquiryService;

@Controller
public class WelcomeController {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(this
			.getClass());
	
	@Autowired
	LayoutEnquiryService layoutEnquiryService;

	@Autowired
	ResourceBundleMessageSource messageResource;
	@Autowired
	CurrentUserService currentUserService;
	
	@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@RequestMapping("/welcome")
	public String welcome(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", this.message);
		model.put("layoutsize",
				messageResource.getMessage("page.save", null, Locale.CHINESE));
		logger.debug("debug--welcome--page--{}", 1);
		logger.info("info--welcome--page--{}", 2);
		logger.error("error--welcome--page--{}", 3);
		return "welcome";
	}
	
	@RequestMapping("/")
	public String index(Map<String, Object> model, String section) {
		CurrentUser currentUser = currentUserService.getUserDetails();
		if(currentUser==null){
			return "redirect:/login";
		}
		if (null != section && !section.equals("")) {
			model.put("section", section);
		} else {
			model.put("section", "");
		}
		return "redirect:/base";//"index"
	}

}
