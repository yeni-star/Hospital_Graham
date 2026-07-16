package cl.duoc.bff_hospital_graham;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BffHospitalGrahamApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(BffHospitalGrahamApplication.class, args);
	}

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
