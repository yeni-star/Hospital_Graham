package com.consumidor_oracle.consumidor_oracle.service;

import com.consumidor_oracle.consumidor_oracle.model.AlertaMedica;
import com.consumidor_oracle.consumidor_oracle.repository.AlertaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertaConsumer {

    @Autowired
    private AlertaRepository alertaRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

        @RabbitListener(queues = "${app.rabbitmq.cola-alertas-oracle}")    public void consumirMensaje(String mensajeJson) {
        System.out.println("==================================================");
        System.out.println("[x] ¡Llegó una alerta de RabbitMQ! -> " + mensajeJson);
        
        try {
            AlertaMedica nuevaAlerta = objectMapper.readValue(mensajeJson, AlertaMedica.class);
            
            alertaRepository.save(nuevaAlerta);
            
            System.out.println("[v] Todo un éxito, la alerta ya está guardada en la base de datos.");
        } catch (Exception e) {
            System.out.println("[-] Ups, algo falló procesando el mensaje: " + e.getMessage());
        }
        
        System.out.println("==================================================");
    }
}