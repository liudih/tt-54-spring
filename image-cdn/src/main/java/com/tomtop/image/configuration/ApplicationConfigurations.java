package com.tomtop.image.configuration;

import java.util.List;

import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.cloudfront.AmazonCloudFrontAsyncClient;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.eventbus.EventBus;
import com.tomtop.image.handlers.IEventHandler;
import com.tomtop.image.services.impl.FileUploadService;

@Configuration
@EnableConfigurationProperties({ JdbcConnectionSettings.class })
public class ApplicationConfigurations {

	private static final Logger Logger = LoggerFactory
			.getLogger(ApplicationConfigurations.class);

	@Autowired
	private List<IEventHandler> eventHandlers;

	@Bean
	public SpringLiquibase liquibase(DataSource dataSource) {
		SpringLiquibase lb = new SpringLiquibase();
		lb.setDataSource(dataSource);
		lb.setChangeLog("classpath:liquibase/*.xml");
		// contexts specifies the runtime contexts to use.
		return lb;
	}

	@Bean
	public EventBus eventBus(DataSource dataSource) {
		EventBus eventBus = new EventBus();

		if (eventHandlers != null) {
			for (IEventHandler handler : this.eventHandlers) {
				eventBus.register(handler);
			}
		} else {
			Logger.info("not find event handlers");
		}

		return eventBus;
	}

	@Bean
	public TrackerClient tClient() {

		try {
			String file = FileUploadService.class.getClassLoader()
					.getResource("fdfs_client.conf").getFile();
			ClientGlobal.init(file);
			Logger.info("********************fastdfs clientglobal init succeed********************");

			TrackerClient tClient = new TrackerClient();
			return tClient;
		} catch (Exception e) {
			Logger.error(
					"********************fastdfs clientglobal init failed********************",
					e);
		}
		return null;
	}

	@Bean(name = "multipartResolver")
	public MultipartResolver multipartResolver() {
		StandardServletMultipartResolver multi = new StandardServletMultipartResolver();
		return multi;
	}

	@Bean
	public HttpRequestFactory httpRequestFactory() {
		NetHttpTransport transport = new NetHttpTransport();
		HttpRequestFactory factory = transport.createRequestFactory();
		return factory;
	}

	@Bean
	public AWSCredentials aWSCredentials() {
		AWSCredentialsProvider provider = new ClasspathPropertiesFileCredentialsProvider(
				"AwsCredentials.properties");

		AWSCredentials credentials = provider.getCredentials();

		return credentials;
	}

	@Bean
	public AmazonCloudFrontAsyncClient amazonCloudFrontAsyncClient(
			AWSCredentials ct) {

		AmazonCloudFrontAsyncClient client = new AmazonCloudFrontAsyncClient(ct);
		return client;
	}

	@Bean
	public AmazonS3Client amazonS3Client(AWSCredentials ct) {
		AmazonS3Client client = new AmazonS3Client(ct);
		return client;
	}

}
