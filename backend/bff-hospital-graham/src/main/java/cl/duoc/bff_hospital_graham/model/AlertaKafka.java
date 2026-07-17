package cl.duoc.bff_hospital_graham.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ALERTAS_KAFKA")
public class AlertaKafka {

    @Id
    @Column(name = "ID_ALERTA")
    private Long idAlerta;

    @Column(name = "FECHA_ALERTA")
    private LocalDateTime fechaAlerta;

    @Column(name = "ID_PACIENTE")
    private String idPaciente;

    @Column(name = "MENSAJE")
    private String mensaje;

    @Column(name = "NOMBRE_PACIENTE")
    private String nombrePaciente;

    @Column(name = "TIPO_ANOMALIA")
    private String tipoAnomalia;

    @Column(name = "VALOR_DETECTADO")
    private Double valorDetectado;

    public Long getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Long idAlerta) {
        this.idAlerta = idAlerta;
    }

    public LocalDateTime getFechaAlerta() {
        return fechaAlerta;
    }

    public void setFechaAlerta(LocalDateTime fechaAlerta) {
        this.fechaAlerta = fechaAlerta;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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

    public Double getValorDetectado() {
        return valorDetectado;
    }

    public void setValorDetectado(Double valorDetectado) {
        this.valorDetectado = valorDetectado;
    }
}