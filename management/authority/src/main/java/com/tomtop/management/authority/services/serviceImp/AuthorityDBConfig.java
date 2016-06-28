/*package com.tomtop.management.authority.services.serviceImp;

import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
@Service
public class AuthorityDBConfig {
	
	private static Logger log=Logger.getLogger(AuthorityDBConfig.class.getName());
	
	public String authorityLiquibase() throws Exception{
		try{
			log.info("-------------->");
			liquibase(dataSource);
		}catch(Exception e){
			log.info("authorityLiquibase error!",e);
			throw e;
		}
		return "success";
	}
	
	@Bean(name = "userCenterLiquibase")
	private  SpringLiquibase liquibase(DataSource dataSource) {
		SpringLiquibase lb = new SpringLiquibase();
		lb.setDataSource(dataSource);
		lb.setChangeLog("classpath:liquibase/userCenterManagement.xml");
		return lb;
	}
}
*/