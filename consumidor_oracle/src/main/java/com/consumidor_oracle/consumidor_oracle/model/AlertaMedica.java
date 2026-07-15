package com.consumidor_oracle.consumidor_oracle.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ALERTAS_MEDICAS")
public class AlertaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALERTA")
    private Long idAlerta;

    @Column(name = "ID_PACIENTE")
    private Long idPaciente;

    @Column(name = "TIPO_ALERTA")
    private String tipoAlerta;

    @Column(name = "VALOR_DETECTADO")
    private String valorDetectado;

    @Column(name = "NIVEL_CRITICIDAD")
    private String nivelCriticidad;

    @Column(name = "MENSAJE_ALERTA")
    private String mensajeAlerta;

    @Column(name = "ORIGEN_MENSAJE")
    private String origenMensaje;

    
    @Column(name = "FECHA_ALERTA", insertable = false, updatable = false)
    private LocalDateTime fechaAlerta;

    public AlertaMedica() {}

    // Constructor para armar la alerta rápido
    public AlertaMedica(Long idPaciente, String tipoAlerta, String valorDetectado, String nivelCriticidad, String mensajeAlerta, String origenMensaje) {
        this.idPaciente = idPaciente;
        this.tipoAlerta = tipoAlerta;
        this.valorDetectado = valorDetectado;
        this.nivelCriticidad = nivelCriticidad;
        this.mensajeAlerta = mensajeAlerta;
        this.origenMensaje = origenMensaje;
    }

    // Getters y Setters
    public Long getIdAlerta() { return idAlerta; }
    public void setIdAlerta(Long idAlerta) { this.idAlerta = idAlerta; }
    public Long getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Long idPaciente) { this.idPaciente = idPaciente; }
    public String getTipoAlerta() { return tipoAlerta; }
    public void setTipoAlerta(String tipoAlerta) { this.tipoAlerta = tipoAlerta; }
    public String getValorDetectado() { return valorDetectado; }
    public void setValorDetectado(String valorDetectado) { this.valorDetectado = valorDetectado; }
    public String getNivelCriticidad() { return nivelCriticidad; }
    public void setNivelCriticidad(String nivelCriticidad) { this.nivelCriticidad = nivelCriticidad; }
    public String getMensajeAlerta() { return mensajeAlerta; }
    public void setMensajeAlerta(String mensajeAlerta) { this.mensajeAlerta = mensajeAlerta; }
    public String getOrigenMensaje() { return origenMensaje; }
    public void setOrigenMensaje(String origenMensaje) { this.origenMensaje = origenMensaje; }
    public LocalDateTime getFechaAlerta() { return fechaAlerta; }
    public void setFechaAlerta(LocalDateTime fechaAlerta) { this.fechaAlerta = fechaAlerta; }
}