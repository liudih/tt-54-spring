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

@Configuration
public class ProductDataSourceConfiguration {

	@Value("${datasource.product.username}")
	private String user;

	@Value("${datasource.product.password}")
	private String password;

	@Value("${datasource.product.url}")
	private String dataSourceUrl;

	@Value("${datasource.product.driver}")
	private String dataSourceClassName;

	@Value("${datasource.product.maximumpoolsize}")
	private int dataMaximumPoolSize;

	@Value("${datasource.product.idletimeout}")
	private long dataIdleTimeout;

	//@Value("${datasource.product.maxlifetime}")
	//private long dataMaxlifetime;

	@Autowired
	CurrentUser currentUser;

	/**
	 * DataSource
	 */
	@Bean(destroyMethod = "close", name = "productdb")
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

	@Bean(name = "productEbean")
	protected EbeanServer buildEbean(
			@Qualifier("productdb") HikariDataSource dataSource) {
		ServerConfig config = new ServerConfig();
		config.setName("product");
		config.addPackage("com.tomtop.management.ebean.product.model");
		config.setDataSource(dataSource);
		config.setCurrentUserProvider(currentUser);
		config.setDefaultServer(true);
		config.setRegister(true);
		return EbeanServerFactory.create(config);
	}

}
