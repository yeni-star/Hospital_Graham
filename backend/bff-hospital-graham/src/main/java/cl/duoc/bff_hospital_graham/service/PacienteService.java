package cl.duoc.bff_hospital_graham.service;

import cl.duoc.bff_hospital_graham.model.Paciente;
import cl.duoc.bff_hospital_graham.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> buscarPacientePorId(Long idPaciente) {
        return pacienteRepository.findById(idPaciente);
    }

    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Paciente actualizarPaciente(Long idPaciente, Paciente pacienteActualizado) {
        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        paciente.setNombreApellido(pacienteActualizado.getNombreApellido());
        paciente.setRut(pacienteActualizado.getRut());
        paciente.setCriticidad(pacienteActualizado.getCriticidad());
        paciente.setFechaLlegada(pacienteActualizado.getFechaLlegada());
        paciente.setFechaSalida(pacienteActualizado.getFechaSalida());
        paciente.setTemperatura(pacienteActualizado.getTemperatura());
        paciente.setHabitacion(pacienteActualizado.getHabitacion());
        paciente.setSignosVitales(pacienteActualizado.getSignosVitales());

        return pacienteRepository.save(paciente);
    }

    public void eliminarPaciente(Long idPaciente) {
        pacienteRepository.deleteById(idPaciente);
    }
}