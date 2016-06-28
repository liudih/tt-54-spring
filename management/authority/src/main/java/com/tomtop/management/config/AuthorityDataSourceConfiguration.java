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
public class AuthorityDataSourceConfiguration {

	@Value("${datasource.user.username}")
	private String user;

	@Value("${datasource.user.password}")
	private String password;

	@Value("${datasource.user.url}")
	private String dataSourceUrl;

	@Value("${datasource.user.driver}")
	private String dataSourceClassName;
	
	@Value("${datasource.manage.maximumpoolsize}")
	private int dataMaximumPoolSize;

	@Value("${datasource.manage.idletimeout}")
	private long dataIdleTimeout;

	@Autowired
	CurrentUser currentUser;
	
	/**
	 * DataSource
	 */
	@Bean(destroyMethod = "close", name = "userCenterdb")
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
	
	@Bean(name = "userCenterdbEbean")
	protected EbeanServer buildEbean(
			@Qualifier("userCenterdb") HikariDataSource dataSource) {
		ServerConfig config = new ServerConfig();
		config.setName("userCenter");
		config.addPackage("com.tomtop.management.ebean.authority.model");
		config.addJar("authority-0.0.1-SNAPSHOT.jar");
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