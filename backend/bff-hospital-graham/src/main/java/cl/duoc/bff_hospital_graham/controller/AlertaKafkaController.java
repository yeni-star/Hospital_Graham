package cl.duoc.bff_hospital_graham.controller;

import cl.duoc.bff_hospital_graham.model.AlertaKafka;
import cl.duoc.bff_hospital_graham.repository.AlertaKafkaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/api/alertas-kafka")
public class AlertaKafkaController {

    private final AlertaKafkaRepository alertaKafkaRepository;

    public AlertaKafkaController(AlertaKafkaRepository alertaKafkaRepository) {
        this.alertaKafkaRepository = alertaKafkaRepository;
    }

    @GetMapping
    public List<AlertaKafka> listarUltimasAlertasKafka() {
        return alertaKafkaRepository.findTop10ByOrderByIdAlertaDesc();
    }
}