package cl.duoc.bff_hospital_graham.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "PACIENTES")
public class Paciente {

    @Id
    @Column(name = "ID_PACIENTE")
    private Long idPaciente;

    @Column(name = "NOMBRE_APELLIDO", nullable = false, length = 100)
    private String nombreApellido;

    @Column(name = "RUT", nullable = false, length = 12)
    private String rut;

    @Column(name = "CRITICIDAD", nullable = false, length = 20)
    private String criticidad;

    @Column(name = "FECHA_LLEGADA", nullable = false)
    private LocalDate fechaLlegada;

    @Column(name = "FECHA_SALIDA")
    private LocalDate fechaSalida;

    @Column(name = "TEMPERATURA")
    private Double temperatura;

    @Column(name = "HABITACION", length = 20)
    private String habitacion;

    @Column(name = "SIGNOS_VITALES", length = 200)
    private String signosVitales;

    public Paciente() {
    }

    public Paciente(Long idPaciente, String nombreApellido, String rut, String criticidad,
                    LocalDate fechaLlegada, LocalDate fechaSalida, Double temperatura,
                    String habitacion, String signosVitales) {
        this.idPaciente = idPaciente;
        this.nombreApellido = nombreApellido;
        this.rut = rut;
        this.criticidad = criticidad;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.temperatura = temperatura;
        this.habitacion = habitacion;
        this.signosVitales = signosVitales;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getCriticidad() {
        return criticidad;
    }

    public void setCriticidad(String criticidad) {
        this.criticidad = criticidad;
    }

    public LocalDate getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(LocalDate fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public String getSignosVitales() {
        return signosVitales;
    }

    public void setSignosVitales(String signosVitales) {
        this.signosVitales = signosVitales;
    }
}