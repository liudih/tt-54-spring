package com.tomtop.management.authority.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

import com.tomtop.management.authority.services.serviceImp.MD5Encoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login","/public/**")
				.permitAll()
				.anyRequest().authenticated()
				/*.antMatchers("/", "/**")
				.hasAuthority("ADMIN")
				.anyRequest()
				.fullyAuthenticated()*/
				// .antMatchers("/**").permitAll()
				.and().formLogin().loginPage("/login")
				.failureUrl("/login?error").usernameParameter("jobNumber")
				.permitAll().and().logout().logoutUrl("/logout")
				.deleteCookies("remember-me").logoutSuccessUrl("/login").permitAll()
				.and().rememberMe()
				.and().headers()
			      .frameOptions().sameOrigin()
			      .and().sessionManagement().sessionAuthenticationStrategy(sessionAuthenticationStrategy());
	}

	@Bean  
	public SessionRegistry sessionRegistry(){  
	    return new SessionRegistryImpl();  
	} 
	
	@Bean  
	public CompositeSessionAuthenticationStrategy sessionAuthenticationStrategy(){  
	    ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy=new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());  
	    concurrentSessionControlAuthenticationStrategy.setMaximumSessions(200);  
	    concurrentSessionControlAuthenticationStrategy.setExceptionIfMaximumExceeded(true);  
	  
	    SessionFixationProtectionStrategy sessionFixationProtectionStrategy=new SessionFixationProtectionStrategy();  
	  
	    RegisterSessionAuthenticationStrategy registerSessionStrategy = new RegisterSessionAuthenticationStrategy(sessionRegistry());  
	    CompositeSessionAuthenticationStrategy sessionAuthenticationStrategy=new CompositeSessionAuthenticationStrategy(  
	            Arrays.asList(concurrentSessionControlAuthenticationStrategy,sessionFixationProtectionStrategy,registerSessionStrategy));  
	    return sessionAuthenticationStrategy;  
	}
	
	@Bean  
	public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception {  
	    UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter=new UsernamePasswordAuthenticationFilter();  
	    usernamePasswordAuthenticationFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy());  
	    usernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());  
	    return usernamePasswordAuthenticationFilter;  
	} 
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(
				new MD5Encoder());
		// new BCryptPasswordEncoder();
	}

}