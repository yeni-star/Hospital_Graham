package com.consumidor_alertas_oracle.consumidor_alertas_oracle.service;

import com.consumidor_alertas_oracle.consumidor_alertas_oracle.model.AlertaMedica;
import com.consumidor_alertas_oracle.consumidor_alertas_oracle.repository.AlertaMedicaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertaMedicaService {

    private final AlertaMedicaRepository alertaMedicaRepository;

    public AlertaMedicaService(AlertaMedicaRepository alertaMedicaRepository) {
        this.alertaMedicaRepository = alertaMedicaRepository;
    }

    public List<AlertaMedica> listarAlertas() {
        return alertaMedicaRepository.findAll();
    }

    public Optional<AlertaMedica> buscarPorId(Long idAlerta) {
        return alertaMedicaRepository.findById(idAlerta);
    }

    public AlertaMedica guardarAlerta(AlertaMedica alertaMedica) {
        return alertaMedicaRepository.save(alertaMedica);
    }

    public AlertaMedica actualizarAlerta(Long idAlerta, AlertaMedica alertaNueva) {
        return alertaMedicaRepository.findById(idAlerta).map(alertaExistente -> {
            alertaExistente.setIdPaciente(alertaNueva.getIdPaciente());
            alertaExistente.setNombrePaciente(alertaNueva.getNombrePaciente());
            alertaExistente.setTipoAnomalia(alertaNueva.getTipoAnomalia());
            alertaExistente.setValorDetectado(alertaNueva.getValorDetectado());
            alertaExistente.setMensaje(alertaNueva.getMensaje());
            alertaExistente.setFechaAlerta(alertaNueva.getFechaAlerta());
            return alertaMedicaRepository.save(alertaExistente);
        }).orElse(null);
    }

    public boolean eliminarAlerta(Long idAlerta) {
        if (alertaMedicaRepository.existsById(idAlerta)) {
            alertaMedicaRepository.deleteById(idAlerta);
            return true;
        }
        return false;
    }
}