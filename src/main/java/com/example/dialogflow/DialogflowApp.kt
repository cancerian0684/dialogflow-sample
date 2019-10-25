package com.example.dialogflow;

import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class DialogflowApp {

	public static void main(String[] args) {
		SpringApplication.run(DialogflowApp.class, args);
	}

	@Bean
	public JacksonFactory jacksonFactory() {
		return JacksonFactory.getDefaultInstance();
	}
}
