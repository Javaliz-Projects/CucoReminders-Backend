package com.ucsal.cucoreminders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CucoremindersApplication {

	public static void main(String[] args) {
		SpringApplication.run(CucoremindersApplication.class, args);
	}

}
