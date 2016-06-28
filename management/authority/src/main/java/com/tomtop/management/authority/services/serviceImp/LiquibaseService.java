package com.tomtop.management.authority.services.serviceImp;

import java.sql.Connection;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Service
public class LiquibaseService {// implements ApplicationContextAware{

	@Autowired
	@Qualifier("userCenterdb")
	DataSource dataSource;
	
	
	private static final  String sourceName = "userCenterManagement";
	

	Map<String, Liquibase> liquibases;

	Map<String, DataSource> datasources;

	// ApplicationContext applicationContext;
	public Liquibase getLiquibaseInstance(String name) {
		if (liquibases == null) {
			initialize();
		}
		return liquibases.get(name);
	}

	public DataSource getDataSource(String name) {
		if (datasources == null) {
			initialize();
		}
		return datasources.get(name);
	}

	protected synchronized void initialize() {
		
		Set<String> names = Sets.newHashSet();
		names.add(sourceName);
		datasources = Maps.newHashMap();
		datasources.put(sourceName, dataSource);
		liquibases = Maps.toMap(names, new Function<String, Liquibase>() {
			@Override
			public Liquibase apply(String name) {
				try {
					DataSource ds = datasources.get(name);
					Connection conn = ds.getConnection();
					Liquibase lb = new Liquibase("liquibase/" + name + ".xml",
							new ClassLoaderResourceAccessor(),
							new JdbcConnection(conn));
					return lb;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

}
