package com.tomtop.ipn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.tomtop")
@EnableAutoConfiguration()
@SpringBootApplication
public class Application {
	public static void main(String[] args) {


		SpringApplication.run(Application.class, args);

		// ConfigurableApplicationContext context = SpringApplication.run(
		// Application.class, args);
		// Map<String, IEventHandler> handlers = context
		// .getBeansOfType(IEventHandler.class);
		//
		// logger.info("handlers is null:{}", handlers == null);
		// EventBus eventBus = (EventBus) context.getBean("eventBus");
		// logger.info("eventBus isSingleton:{}",
		// context.isSingleton("eventBus"));
		// if (handlers != null) {
		// for (IEventHandler handler : handlers.values()) {
		// logger.info("handlers:{}", handler.getClass());
		// eventBus.register(handler);
		// }
		// }

	}
}
