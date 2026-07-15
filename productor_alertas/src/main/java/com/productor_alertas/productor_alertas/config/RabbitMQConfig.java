package com.productor_alertas.productor_alertas.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbitmq.cola-alertas-oracle}")
    private String colaAlertasOracle;

    @Value("${app.rabbitmq.cola-alertas-json}")
    private String colaAlertasJson;

    @Bean
    public Queue colaAlertasOracle() {
        return new Queue(colaAlertasOracle, true);
    }

    @Bean
    public Queue colaAlertasJson() {
        return new Queue(colaAlertasJson, true);
    }
}