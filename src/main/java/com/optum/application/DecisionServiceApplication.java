package com.optum.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.optum")
public class DecisionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DecisionServiceApplication.class, args);
	}
}
