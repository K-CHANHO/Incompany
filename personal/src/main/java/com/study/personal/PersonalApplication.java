package com.study.personal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

// TODO : spring-security 설정 제외, 추후 지울 것.
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PersonalApplication extends SpringBootServletInitializer {

	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PersonalApplication.class);
	}
	
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter(); 
	}

	public static void main(String[] args) {
		SpringApplication.run(PersonalApplication.class, args);
	}
	

}
