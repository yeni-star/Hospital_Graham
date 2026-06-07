package cl.duoc.bff_hospital_graham.controller;

import cl.duoc.bff_hospital_graham.model.Paciente;
import cl.duoc.bff_hospital_graham.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public List<Paciente> listarPacientes() {
        return pacienteService.listarPacientes();
    }

    @GetMapping("/{idPaciente}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Long idPaciente) {
        return pacienteService.buscarPacientePorId(idPaciente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Paciente guardarPaciente(@RequestBody Paciente paciente) {
        return pacienteService.guardarPaciente(paciente);
    }

    @PutMapping("/{idPaciente}")
    public Paciente actualizarPaciente(@PathVariable Long idPaciente, @RequestBody Paciente paciente) {
        return pacienteService.actualizarPaciente(idPaciente, paciente);
    }

    @DeleteMapping("/{idPaciente}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long idPaciente) {
        pacienteService.eliminarPaciente(idPaciente);
        return ResponseEntity.noContent().build();
    }
}
