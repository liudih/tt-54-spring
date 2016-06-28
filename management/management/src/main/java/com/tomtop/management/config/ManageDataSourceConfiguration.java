package com.tomtop.management.config;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.ServerConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ManageDataSourceConfiguration {

	@Value("${datasource.manage.username}")
	private String user;

	@Value("${datasource.manage.password}")
	private String password;

	@Value("${datasource.manage.url}")
	private String dataSourceUrl;

	@Value("${datasource.manage.driver}")
	private String dataSourceClassName;

	@Value("${datasource.manage.maximumpoolsize}")
	private int dataMaximumPoolSize;

	@Value("${datasource.manage.idletimeout}")
	private long dataIdleTimeout;

	//@Value("${datasource.manage.maxlifetime}")
	//private long dataMaxlifetime;

	@Autowired
	CurrentUser currentUser;

	/**
	 * DataSource
	 */
	@Bean(destroyMethod = "close", name = "managedb")
	@Primary
	protected HikariDataSource buildDataSource() {
		HikariConfig cfg = new HikariConfig();
		cfg.setUsername(user);
		cfg.setPassword(password);
		cfg.setJdbcUrl(dataSourceUrl);
		cfg.setDriverClassName(dataSourceClassName);
		cfg.setIdleTimeout(dataIdleTimeout);
		//cfg.setMaxLifetime(dataMaxlifetime);
		cfg.setConnectionTestQuery("");
		cfg.setMaximumPoolSize(dataMaximumPoolSize);

		return new HikariDataSource(cfg);
	}

	@Bean(name = "manageEbean")
	protected EbeanServer buildEbean(
			@Qualifier("managedb") HikariDataSource dataSource) {
		ServerConfig config = new ServerConfig();
		config.setName("manage");
		config.addPackage("com.tomtop.management.ebean.manage.model");
		config.addPackage("com.tomtop.management.ebean.homepage.model");
		config.addPackage("com.tomtop.management.ebean.shipping.model");
		config.setDataSource(dataSource);
		config.setCurrentUserProvider(currentUser);
		config.setDefaultServer(true);
		config.setRegister(true);
		return EbeanServerFactory.create(config);
	}

}
