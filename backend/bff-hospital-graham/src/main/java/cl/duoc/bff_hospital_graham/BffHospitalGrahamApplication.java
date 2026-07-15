package cl.duoc.bff_hospital_graham;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BffHospitalGrahamApplication {

	@Bean
	public static void main(String[] args) {
		SpringApplication.run(BffHospitalGrahamApplication.class, args);
	}

}
