package com.flightinportugal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FipApplication {

	public static void main(String[] args) {
		SpringApplication.run(FipApplication.class, args);
	}

}
