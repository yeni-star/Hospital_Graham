package com.consumidor_json.consumidor_json.service;

import com.consumidor_json.consumidor_json.model.ResumenSignosVitales;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ConsumidorJsonService {

    private final ObjectMapper objectMapper;

    @Value("${app.archivos.ruta}")
    private String rutaArchivos;

    public ConsumidorJsonService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${app.rabbitmq.cola-resumenes}")
    public void recibirMensaje(Message mensaje) {
        try {
            String contenido = new String(mensaje.getBody(), StandardCharsets.UTF_8);

            ResumenSignosVitales resumen = objectMapper.readValue(contenido, ResumenSignosVitales.class);

            Files.createDirectories(Path.of(rutaArchivos));

            String fechaArchivo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String nombreArchivo = "resumen_paciente_" + resumen.getIdPaciente() + "_" + fechaArchivo + ".json";

            Path archivo = Path.of(rutaArchivos, nombreArchivo);

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(archivo.toFile(), resumen);

            System.out.println("Mensaje recibido desde RabbitMQ");
            System.out.println("Archivo JSON generado: " + archivo.toAbsolutePath());

        } catch (Exception e) {
            System.out.println("Error al procesar mensaje de RabbitMQ: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "${app.rabbitmq.cola-alertas-json}")    
    public void recibirAlerta(Message mensaje) {
        try {
             String contenidoJson = new String(mensaje.getBody(), StandardCharsets.UTF_8);
            
             Files.createDirectories(Path.of(rutaArchivos));
            
             String fechaArchivo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String nombreArchivo = "alerta_urgente_" + fechaArchivo + ".json";
            Path archivo = Path.of(rutaArchivos, nombreArchivo);
            
            Files.writeString(archivo, contenidoJson, StandardCharsets.UTF_8);
            
            System.out.println("==================================================");
            System.out.println("[!] Alerta recibida para auditoría de archivos");
            System.out.println("[v] Archivo de alerta guardado en: " + archivo.toAbsolutePath());
            System.out.println("==================================================");
            
        } catch (Exception e) {
            System.out.println("[-] Error al procesar la alerta para el archivo JSON: " + e.getMessage());
        }
    }

}