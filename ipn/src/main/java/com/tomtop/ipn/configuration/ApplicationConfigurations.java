package com.tomtop.ipn.configuration;

import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.eventbus.EventBus;

@Configuration
@EnableConfigurationProperties({ JdbcConnectionSettings.class,
		IpnSettings.class })
public class ApplicationConfigurations {

	private static final Logger Logger = LoggerFactory
			.getLogger(ApplicationConfigurations.class);

	@Bean
	public SpringLiquibase liquibase(DataSource dataSource) {
		SpringLiquibase lb = new SpringLiquibase();
		lb.setDataSource(dataSource);
		lb.setChangeLog("classpath:liquibase/*.xml");
		// contexts specifies the runtime contexts to use.
		return lb;
	}

//	@Bean()
//	public EventBus eventBus() {
//		EventBus eventBus = new EventBus();
//
//		return eventBus;
//	}

}
