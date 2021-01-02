package com.example.hackernews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@EnableAutoConfiguration(
//		exclude = {org.springframework.boot.autoconfigure.com.example.hackernews.security.servlet.SecurityAutoConfiguration.class})
@SpringBootApplication
public class HackerNewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackerNewsApplication.class, args);
	}

}
