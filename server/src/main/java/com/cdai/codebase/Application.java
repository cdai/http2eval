package com.cdai.codebase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Entry point for the entire program
 */
@SpringBootApplication
@ComponentScan("com.cdai.codebase")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
