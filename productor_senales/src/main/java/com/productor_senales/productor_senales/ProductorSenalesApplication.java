package com.productor_senales.productor_senales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ProductorSenalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductorSenalesApplication.class, args);
	}

}
