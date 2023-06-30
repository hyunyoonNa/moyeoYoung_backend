package com.kosta.moyoung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class MoyoungApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoyoungApplication.class, args);
	}

}
