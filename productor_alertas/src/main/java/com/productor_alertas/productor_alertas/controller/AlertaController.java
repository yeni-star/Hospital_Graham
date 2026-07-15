package com.productor_alertas.productor_alertas.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.cola-alertas-oracle}")
    private String colaAlertasOracle;

    @Value("${app.rabbitmq.cola-alertas-json}")
    private String colaAlertasJson;

    public AlertaController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/estado")
    public String estado() {
        return "ms-productor-alertas funcionando correctamente";
    }

    @PostMapping("/enviar")
    public String enviarAlerta(@RequestBody String alertaMedica) {
        rabbitTemplate.convertAndSend(colaAlertasOracle, alertaMedica);
        rabbitTemplate.convertAndSend(colaAlertasJson, alertaMedica);

        return "Alerta médica enviada correctamente a cola.alertas.oracle y cola.alertas.json";
    }
}