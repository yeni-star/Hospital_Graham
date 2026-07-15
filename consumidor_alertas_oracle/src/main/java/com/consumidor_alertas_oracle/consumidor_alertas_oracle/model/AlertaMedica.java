package com.consumidor_alertas_oracle.consumidor_alertas_oracle.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ALERTAS_KAFKA")
public class AlertaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALERTA")
    private Long idAlerta;

    @Column(name = "ID_PACIENTE")
    private String idPaciente;

    @Column(name = "NOMBRE_PACIENTE")
    private String nombrePaciente;

    @Column(name = "TIPO_ANOMALIA")
    private String tipoAnomalia;

    @Column(name = "VALOR_DETECTADO")
    private String valorDetectado;

    @Column(name = "MENSAJE")
    private String mensaje;

    @Column(name = "FECHA_ALERTA")
    private String fechaAlerta;

    public AlertaMedica() {
    }

    public Long getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Long idAlerta) {
        this.idAlerta = idAlerta;
    }

    public String getIdPaciente() {
    return idPaciente;
}

public void setIdPaciente(String idPaciente) {
    this.idPaciente = idPaciente;
}

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getTipoAnomalia() {
        return tipoAnomalia;
    }

    public void setTipoAnomalia(String tipoAnomalia) {
        this.tipoAnomalia = tipoAnomalia;
    }

    public String getValorDetectado() {
        return valorDetectado;
    }

    public void setValorDetectado(String valorDetectado) {
        this.valorDetectado = valorDetectado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFechaAlerta() {
        return fechaAlerta;
    }

    public void setFechaAlerta(String fechaAlerta) {
        this.fechaAlerta = fechaAlerta;
    }
}