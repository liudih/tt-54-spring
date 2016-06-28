package com.tomtop.management.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.ServerConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class OrderSourceConfiguration {
	
	@Value("${datasource.order.username}")
	private String user;

	@Value("${datasource.order.password}")
	private String password;

	@Value("${datasource.order.url}")
	private String dataSourceUrl;

	@Value("${datasource.order.driver}")
	private String dataSourceClassName;
	
	@Value("${datasource.order.maximumpoolsize}")
	private int dataMaximumPoolSize;

	@Value("${datasource.order.idletimeout}")
	private long dataIdleTimeout;

	@Autowired
	CurrentUser currentUser;
	
	/**
	 * DataSource
	 */
	@Bean(destroyMethod = "close", name = "orderdb")
	//@Primary
	protected HikariDataSource buildDataSource() {
		HikariConfig cfg = new HikariConfig();
		cfg.setUsername(user);
		cfg.setPassword(password);
		cfg.setJdbcUrl(dataSourceUrl);
		cfg.setDriverClassName(dataSourceClassName);
		cfg.setIdleTimeout(dataIdleTimeout);
		cfg.setMaxLifetime(dataMaximumPoolSize);
		
		cfg.setConnectionTestQuery("");
		return new HikariDataSource(cfg);
	}
	
	@Bean(name = "orderdbEbean")
	protected EbeanServer buildEbean(
			@Qualifier("orderdb") HikariDataSource dataSource) {
		ServerConfig config = new ServerConfig();
		config.setName("orderdb");
		config.addPackage("com.tomtop.management.ebean.order.model");
		config.setDataSource(dataSource);
		config.setCurrentUserProvider(currentUser);
		config.setDefaultServer(true);
		config.setRegister(true);
		return EbeanServerFactory.create(config);
	}

	/**
	 * TransactionManager
	 */
	@Bean(name = "baseTransaction")
	@Autowired
	protected PlatformTransactionManager createTransactionManager(
			DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
}
