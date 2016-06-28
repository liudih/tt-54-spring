package com.tomtop.ipn.configuration.mybatis;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ClassUtils;

import com.jolbox.bonecp.BoneCPDataSource;
import com.tomtop.ipn.configuration.JdbcConnectionSettings;

/**
 * product数据库配置
 * 
 * @author lijun
 *
 */
@Configuration
@MapperScan(basePackages = "com.tomtop.ipn.mappers.order", sqlSessionFactoryRef = "orderSqlSessionFactory")
public class OrderMybatisConfig {

	private static Logger log = LoggerFactory
			.getLogger(OrderMybatisConfig.class);

	@Autowired
	private JdbcConnectionSettings jdbcConnectionSettings;

	@Primary
	@Bean(name="orderDataSource")
	public DataSource dataSource() {
		BoneCPDataSource ds = new BoneCPDataSource();
		ds.setDriverClass(jdbcConnectionSettings.getOrderDriver());
		ds.setUsername(jdbcConnectionSettings.getOrderUsername());
		ds.setPassword(jdbcConnectionSettings.getOrderPassword());
		ds.setJdbcUrl(jdbcConnectionSettings.getOrderUrl());
		if (jdbcConnectionSettings.getIdleConnectionTestPeriod() != null) {
			ds.setIdleConnectionTestPeriodInMinutes(jdbcConnectionSettings
					.getIdleConnectionTestPeriod());
		}
		if (jdbcConnectionSettings.getIdleMaxAge() != null) {
			ds.setIdleMaxAgeInMinutes(jdbcConnectionSettings.getIdleMaxAge());
		}
		if (jdbcConnectionSettings.getOrderMaxConnectionsPerPartition() != null) {
			ds.setMaxConnectionsPerPartition(jdbcConnectionSettings
					.getOrderMaxConnectionsPerPartition());
		}
		if (jdbcConnectionSettings.getOrderMinConnectionsPerPartition() != null) {
			ds.setMinConnectionsPerPartition(jdbcConnectionSettings
					.getOrderMinConnectionsPerPartition());
		}
		if (jdbcConnectionSettings.getPartitionCount() != null) {
			ds.setPartitionCount(jdbcConnectionSettings.getPartitionCount());
		}
		if (jdbcConnectionSettings.getAcquireIncrement() != null) {
			ds.setAcquireIncrement(jdbcConnectionSettings.getAcquireIncrement());
		}
		if (jdbcConnectionSettings.getStatementsCacheSize() != null) {
			ds.setStatementsCacheSize(jdbcConnectionSettings
					.getStatementsCacheSize());
		}
		if (jdbcConnectionSettings.getReleaseHelperThreads() != null) {
			ds.setReleaseHelperThreads(jdbcConnectionSettings
					.getReleaseHelperThreads());
		}
		return ds;
	}

	public Resource[] getResource(String basePackage, String pattern)
			throws IOException {
		String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
				+ ClassUtils
						.convertClassNameToResourcePath(new StandardEnvironment()
								.resolveRequiredPlaceholders(basePackage))
				+ "/" + pattern;
		Resource[] resources = new PathMatchingResourcePatternResolver()
				.getResources(packageSearchPath);
		return resources;
	}

	@Bean(name="orderSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("orderDataSource") DataSource datasource)
			throws Exception {
		log.debug("> sqlSessionFactory");
		final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(datasource);
		sqlSessionFactory.setConfigLocation(new ClassPathResource(
				"mybatis-config.xml"));
		sqlSessionFactory.setFailFast(true);
		sqlSessionFactory.setMapperLocations(getResource("mapper/order",
				"*.xml"));
		// sqlSessionFactory.setTypeHandlersPackage("demo.springboot.configuration.mybatis.typehandler");
		// sqlSessionFactory.setTypeAliasesPackage("product.mybatis");
		return sqlSessionFactory.getObject();
	}

	// @Bean
	// public DataSourceTransactionManager transactionManager() {
	// log.debug("> transactionManager");
	// return new DataSourceTransactionManager(dataSource());
	// }

	@PostConstruct
	public void postConstruct() {
		log.info("jdbc.settings={}", jdbcConnectionSettings);
	}
}
