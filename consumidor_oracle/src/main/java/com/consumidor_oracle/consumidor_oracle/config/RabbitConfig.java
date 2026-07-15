package com.consumidor_oracle.consumidor_oracle.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app.rabbitmq.cola-alertas-oracle}")
    private String colaAlertasOracle;

    @Bean
    public Queue colaAlertasOracle() {
        return new Queue(colaAlertasOracle, true);
    }
}