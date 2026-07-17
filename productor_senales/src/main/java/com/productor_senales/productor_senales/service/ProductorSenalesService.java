package com.productor_senales.productor_senales.service;

import com.productor_senales.productor_senales.model.SenalVital;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class ProductorSenalesService {


    private final KafkaTemplate<String, SenalVital> kafkaTemplate;
    private final Random random = new Random();

    public ProductorSenalesService(KafkaTemplate<String, SenalVital> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Scheduled(fixedRate = 60000)
    public void generarYEnviarSenales() {
        int ritmoCardiaco = 60 + random.nextInt(60);
        int presionSistolica = 90 + random.nextInt(50);
        int presionDiastolica = 60 + random.nextInt(30);
        double temperatura = 36.0 + (random.nextDouble() * 3.0); 

        // Armamos el objeto con la lectura del momento
        SenalVital lectura = new SenalVital(
                "PACIENTE-" + random.nextInt(5),
                ritmoCardiaco,
                presionSistolica,
                presionDiastolica,
                Math.round(temperatura * 10.0) / 10.0,
                LocalDateTime.now().toString()
        );


        kafkaTemplate.send("senales_vitales", lectura.getIdPaciente(), lectura);
    }
}