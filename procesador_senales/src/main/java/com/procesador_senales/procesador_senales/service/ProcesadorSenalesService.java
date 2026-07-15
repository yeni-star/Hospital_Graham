package com.procesador_senales.procesador_senales.service;

import com.procesador_senales.procesador_senales.model.Alerta;
import com.procesador_senales.procesador_senales.model.SenalVital;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class ProcesadorSenalesService {

    private final KafkaTemplate<String, Alerta> kafkaTemplate;
    private final Random random = new Random();

    private final List<String> nombresPacientes = List.of(
            "Carlos Ramirez", "Ana Torres", "Luis Fernandez", 
            "Maria Gomez", "Jorge Silva", "Camila Soto"
    );

    public ProcesadorSenalesService(KafkaTemplate<String, Alerta> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "senales_vitales", groupId = "grupo-procesadores-medicos")
    public void vigilarPaciente(SenalVital senal) {
        System.out.println("Analizando signos vitales del " + senal.getIdPaciente() + "...");


        if (senal.getTemperatura() > 38.0) {
            dispararAlerta(
                    senal.getIdPaciente(), 
                    "TEMPERATURA_ALTA", 
                    String.valueOf(senal.getTemperatura()), 
                    "Paciente presenta temperatura corporal alta"
            );
        }

        if (senal.getFrecuenciaCardiaca() > 100) {
            dispararAlerta(
                    senal.getIdPaciente(), 
                    "RITMO_CARDIACO_ALTO", 
                    String.valueOf(senal.getFrecuenciaCardiaca()), 
                    "Paciente presenta frecuencia cardíaca fuera de rango"
            );
        }
    }

    private void dispararAlerta(String id, String tipo, String valor, String mensaje) {
        String fechaFormateada = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String nombreAsignado = nombresPacientes.get(random.nextInt(nombresPacientes.size()));

        Alerta nuevaAlerta = new Alerta(
                id,
                nombreAsignado,
                tipo,
                valor,
                mensaje,
                fechaFormateada
        );

        kafkaTemplate.send("alertas", nuevaAlerta.getIdPaciente(), nuevaAlerta);
        System.out.println("¡ALERTA ENVIADA! -> " + tipo + " para el paciente " + nombreAsignado);
    }
}