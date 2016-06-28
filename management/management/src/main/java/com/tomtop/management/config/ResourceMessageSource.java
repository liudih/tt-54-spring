package com.tomtop.management.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class ResourceMessageSource {
	/**
	    * @Title: localeResolver
	    * @Description: TODO(设置默认语言)
	    * @param @return    参数
	    * @return LocaleResolver    返回类型
	    * @throws
		* @author fcl
	    * @date 2015年12月18日
	 */
   @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.CHINESE);
        return slr;
    }
   
   /**
       * @Title: localeChangeInterceptor
       * @Description: TODO(改变语言的参数)
       * @param @return    参数
       * @return LocaleChangeInterceptor    返回类型
       * @throws
   	* @author fcl
       * @date 2015年12月18日
    */
   @Bean
   public LocaleChangeInterceptor localeChangeInterceptor() {
       LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
       lci.setParamName("lang");
       return lci;
   }

}
