package com.kaldie.eveindustry;

import java.io.IOException;
import java.util.Collections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kaldie.eveindustry.client.zkillboard.KillboardWebSocketClient;
import com.kaldie.eveindustry.repository.ESDReader;
import com.kaldie.eveindustry.service.task.TaskRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableCaching
@RequiredArgsConstructor
public class EveIndustryApplication {

	Logger logger = LoggerFactory.getLogger(EveIndustryApplication.class);

	private final KillboardWebSocketClient killboardWebSocketClient;

	private final TaskRunner runner;

	private final ESDReader reader;

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() throws IOException {

		reader.storeEsd();
		
		runner.runAll();

		try {
			killboardWebSocketClient.startListnening();
		} catch (JsonProcessingException | InterruptedException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		SpringApplication.run(EveIndustryApplication.class, args);
	}

	// Fix the CORS errors
	@Bean
	public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {  
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
		CorsConfiguration config = new CorsConfiguration();  
		config.setAllowCredentials(true); 
		// *** URL below needs to match the Vue client URL and port ***
		config.setAllowedOrigins(Collections.singletonList("http://localhost:8081")); 
		config.setAllowedMethods(Collections.singletonList("*"));  
		config.setAllowedHeaders(Collections.singletonList("*"));  
		source.registerCorsConfiguration("/**", config);  
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);  
		return bean;  
	}   
	
}
