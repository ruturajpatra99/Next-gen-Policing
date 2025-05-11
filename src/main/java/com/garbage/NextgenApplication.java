package com.garbage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.garbage.service")
@ComponentScan(basePackages = "com.garbage.repository")
@ComponentScan(basePackages = "com.garbage.model")
@ComponentScan(basePackages = { "com.garbage.controller" })

@SpringBootApplication
public class NextgenApplication {
	public static void main(String[] args) {
		SpringApplication.run(NextgenApplication.class, args);
		System.out.println("my application .... ");
	}

}
