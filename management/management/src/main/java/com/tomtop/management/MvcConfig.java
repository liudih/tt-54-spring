package com.tomtop.management;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.tomtop.management.authority.interceptor.UrlInterceptor;
import com.tomtop.management.config.GlobalParameter;

import freemarker.template.TemplateModelException;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	/*
	 * @Override public void addViewControllers(ViewControllerRegistry registry)
	 * { registry.addViewController("/index").setViewName("index");
	 * //registry.addViewController("/").setViewName("index");
	 * //registry.addViewController("/hello").setViewName("hello");
	 * //registry.addViewController("/login").setViewName("login"); }
	 */
	/**
	 * 改变语言
	 */
	@Autowired
	LocaleChangeInterceptor localeChangeInterceptor;

	@Autowired
	GlobalParameter parameter;

	@Autowired
	private freemarker.template.Configuration freeMarkeConfig;
	@PostConstruct
	public void inint() throws TemplateModelException {
		freeMarkeConfig.setSharedVariable("styleurl", parameter.getStyleUrl());
		freeMarkeConfig.setSharedVariable("imageUploadUrl", parameter.getImageUploadUrl());
		freeMarkeConfig.setSharedVariable("imageUploadToken", parameter.getImageUploadToken());
		freeMarkeConfig.setSharedVariable("modifyPriceToken", parameter.getCachePriceClearUrl());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor);
		registry.addInterceptor(new UrlInterceptor()).addPathPatterns("/**");
	}
}
