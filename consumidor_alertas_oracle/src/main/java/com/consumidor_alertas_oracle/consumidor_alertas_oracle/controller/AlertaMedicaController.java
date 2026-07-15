package com.consumidor_alertas_oracle.consumidor_alertas_oracle.controller;

import com.consumidor_alertas_oracle.consumidor_alertas_oracle.model.AlertaMedica;
import com.consumidor_alertas_oracle.consumidor_alertas_oracle.service.AlertaMedicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaMedicaController {

    private final AlertaMedicaService alertaMedicaService;

    public AlertaMedicaController(AlertaMedicaService alertaMedicaService) {
        this.alertaMedicaService = alertaMedicaService;
    }

    @GetMapping
    public List<AlertaMedica> listarAlertas() {
        return alertaMedicaService.listarAlertas();
    }

    @GetMapping("/{idAlerta}")
    public ResponseEntity<AlertaMedica> buscarPorId(@PathVariable Long idAlerta) {
        return alertaMedicaService.buscarPorId(idAlerta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AlertaMedica crearAlerta(@RequestBody AlertaMedica alertaMedica) {
        return alertaMedicaService.guardarAlerta(alertaMedica);
    }

    @PutMapping("/{idAlerta}")
    public ResponseEntity<AlertaMedica> actualizarAlerta(
            @PathVariable Long idAlerta,
            @RequestBody AlertaMedica alertaMedica) {

        AlertaMedica alertaActualizada = alertaMedicaService.actualizarAlerta(idAlerta, alertaMedica);

        if (alertaActualizada == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(alertaActualizada);
    }

    @DeleteMapping("/{idAlerta}")
    public ResponseEntity<Void> eliminarAlerta(@PathVariable Long idAlerta) {
        boolean eliminada = alertaMedicaService.eliminarAlerta(idAlerta);

        if (!eliminada) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}