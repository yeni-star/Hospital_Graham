package com.consumidor_alertas_oracle.consumidor_alertas_oracle.consumer;

import com.consumidor_alertas_oracle.consumidor_alertas_oracle.model.AlertaMedica;
import com.consumidor_alertas_oracle.consumidor_alertas_oracle.service.AlertaMedicaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AlertaKafkaConsumer {

    private final AlertaMedicaService alertaMedicaService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AlertaKafkaConsumer(AlertaMedicaService alertaMedicaService) {
        this.alertaMedicaService = alertaMedicaService;
    }

    @KafkaListener(
            topics = "${app.kafka.topic-alertas}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void recibirAlerta(String mensaje) {
        try {
            System.out.println("Alerta recibida desde Kafka: " + mensaje);

            AlertaMedica alertaMedica = objectMapper.readValue(mensaje, AlertaMedica.class);

            alertaMedicaService.guardarAlerta(alertaMedica);

            System.out.println("Alerta guardada correctamente en Oracle Cloud");

        } catch (Exception error) {
            System.out.println("Error al procesar alerta desde Kafka: " + error.getMessage());
        }
    }
}