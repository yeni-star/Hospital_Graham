package com.productor_resumenes.productor_resumenes.controller;

import com.productor_resumenes.productor_resumenes.model.ResumenSignosVitales;
import com.productor_resumenes.productor_resumenes.service.ProductorResumenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/resumenes")
@RequiredArgsConstructor
public class ResumenController {

    private final ProductorResumenService productorResumenService;
    private final Map<Long, ResumenSignosVitales> resumenes = new HashMap<>();

    @GetMapping("/estado")
    public ResponseEntity<String> estado() {
        return ResponseEntity.ok("ms-productor-resumenes funcionando correctamente");
    }

    @GetMapping("/{idPaciente}")
    public ResponseEntity<ResumenSignosVitales> obtenerResumen(@PathVariable Long idPaciente) {
        ResumenSignosVitales resumen = resumenes.get(idPaciente);

        if (resumen == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(resumen);
    }

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarResumen(@RequestBody ResumenSignosVitales resumen) {
        resumenes.put(resumen.getIdPaciente(), resumen);
        String respuesta = productorResumenService.enviarResumen(resumen);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{idPaciente}")
    public ResponseEntity<String> actualizarResumen(@PathVariable Long idPaciente, @RequestBody ResumenSignosVitales resumen) {
        resumen.setIdPaciente(idPaciente);
        resumenes.put(idPaciente, resumen);
        return ResponseEntity.ok("Resumen actualizado correctamente");
    }

    @DeleteMapping("/{idPaciente}")
    public ResponseEntity<String> eliminarResumen(@PathVariable Long idPaciente) {
        resumenes.remove(idPaciente);
        return ResponseEntity.ok("Resumen eliminado correctamente");
    }
}