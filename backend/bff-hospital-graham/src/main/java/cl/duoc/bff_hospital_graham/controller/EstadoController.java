package cl.duoc.bff_hospital_graham.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstadoController {

    @GetMapping("/api/estado")
    public String obtenerEstado() {
        return "BFF Hospital Graham funcionando correctamente";
    }
}