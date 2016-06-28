package com.tomtop.product.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties({ JdbcConnectionSettings.class })
public class ApplicationConfigurations {

	@Bean(name = "restTemplate")
	public RestTemplate getRestTemplate() {
		RestTemplate template = new RestTemplate();
		template.getMessageConverters().add(new FormHttpMessageConverter());
		return template;
	}
}
