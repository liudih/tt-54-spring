package com.tomtop.image;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.tomtop.image.configuration.FastdfsSettings;

@Configuration
@ComponentScan(basePackages = "com.tomtop")
@EnableAutoConfiguration()
@EnableConfigurationProperties({FastdfsSettings.class})
@SpringBootApplication
	
public class Application {
	public static void main( String[] args ) {
		SpringApplication.run(Application.class, args);
	}
}
