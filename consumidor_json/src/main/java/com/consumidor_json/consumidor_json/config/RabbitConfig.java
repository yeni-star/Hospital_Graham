package com.consumidor_json.consumidor_json.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app.rabbitmq.cola-resumenes}")
    private String colaResumenes;

    @Value("${app.rabbitmq.cola-alertas-json}")
    private String colaAlertasJson;

    @Bean
    public Queue colaResumenes() {
        return new Queue(colaResumenes, true);
    }

    @Bean
    public Queue colaAlertasJson() {
        return new Queue(colaAlertasJson, true);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}