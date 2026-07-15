package cl.duoc.bff_hospital_graham.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/orquestador")
public class OrquestadorController {

    private final RestTemplate restTemplate;

    public OrquestadorController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/alerta")
    public ResponseEntity<String> enviarAlertaUrgente(@RequestBody String alerta) {
        String urlProductorAlertas = "http://productor-alertas:8081/api/alertas/enviar";

        String respuesta = restTemplate.postForObject(urlProductorAlertas, alerta, String.class);
        
        return ResponseEntity.ok("BFF orquestando -> " + respuesta);
    }

    @PostMapping("/resumen")
    public ResponseEntity<String> enviarResumenPaciente(@RequestBody Object resumen) {
        String urlProductorResumenes = "http://productor-resumenes:8082/api/resumenes/enviar";
        
        String respuesta = restTemplate.postForObject(urlProductorResumenes, resumen, String.class);
        
        return ResponseEntity.ok("BFF orquestando -> " + respuesta);
    }
}