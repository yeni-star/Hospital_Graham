package com.productor_resumenes.productor_resumenes.service;

import com.productor_resumenes.productor_resumenes.model.ResumenSignosVitales;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductorResumenService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.cola-resumenes}")
    private String colaResumenes;

    public String enviarResumen(ResumenSignosVitales resumen) {
        rabbitTemplate.convertAndSend(colaResumenes, resumen);
        return "Resumen enviado correctamente a RabbitMQ";
    }
}