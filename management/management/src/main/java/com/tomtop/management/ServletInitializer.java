package com.tomtop.management;


import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure( SpringApplicationBuilder application ) {
		return application.sources(ManagementApplication.class);
	}

	@Bean(name = "userCenterLiquibase")
	public SpringLiquibase liquibase(
			@Qualifier("userCenterdb") DataSource dataSource) {
		SpringLiquibase lb = new SpringLiquibase();
		lb.setDataSource(dataSource);
		lb.setChangeLog("classpath:liquibase/userCenterManagement.xml");
		lb.setShouldRun(false);
		// contexts specifies the runtime contexts to use.
		return lb;
	}
	
	/*@Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(someFilter());
        registration.addUrlPatterns("/public/login/user");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("someFilter");
        return registration;
    }

    @Bean(name = "someFilter")
    public AuthoritySSOFilter someFilter() {
    	AuthoritySSOFilter authoritySSOFilter = new AuthoritySSOFilter();
    	authoritySSOFilter.setAuthenticationManager(authenticationManager());
        return authoritySSOFilter;
    }*/

}
