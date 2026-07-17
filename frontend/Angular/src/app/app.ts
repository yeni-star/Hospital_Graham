import { Component, OnInit, ChangeDetectorRef } from "@angular/core";
import { MsalService } from "@azure/msal-angular";
import { AuthenticationResult } from "@azure/msal-browser";
import { RouterOutlet } from "@angular/router";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { environment } from "../environments/environment";

interface Paciente {
  idPaciente: number;
  nombreApellido: string;
  rut: string;
  criticidad: string;
  fechaLlegada: string;
  fechaSalida: string | null;
  temperatura: number;
  habitacion: string;
  signosVitales: string;
}

interface EventoMensajeria {
  hora: string;
  tipo: string;
  paciente: string;
  estado: string;
  detalle: string;
}

interface AlertaKafka {
  idAlerta: number;
  fechaAlerta: string;
  idPaciente: string;
  mensaje: string;
  nombrePaciente: string;
  tipoAnomalia: string;
  valorDetectado: number;
}

@Component({
  selector: "app-root",
  standalone: true,
  imports: [CommonModule, FormsModule, RouterOutlet],
  templateUrl: "./app.html",
  styleUrls: ["./app.css"]
})
export class AppComponent implements OnInit {
  estaLogueado = false;
  cargandoMsal = true;

  pacientes: Paciente[] = [];
  cargandoPacientes = false;
  errorPacientes = "";
  mensajeAccion = "";

  eventosMensajeria: EventoMensajeria[] = [];

  alertasKafka: AlertaKafka[] = [];
  cargandoAlertasKafka = false;
  errorAlertasKafka = "";

  modoEdicion = false;

  pacienteFormulario: Paciente = {
    idPaciente: 0,
    nombreApellido: "",
    rut: "",
    criticidad: "MEDIO",
    fechaLlegada: "2026-06-08",
    fechaSalida: null,
    temperatura: 37.0,
    habitacion: "",
    signosVitales: ""
  };

  constructor(
    private msalService: MsalService,
    private detectorCambios: ChangeDetectorRef,
    private http: HttpClient
  ) {}

  async ngOnInit(): Promise<void> {
    try {
      await this.msalService.instance.initialize();

      const resultado: AuthenticationResult | null =
        await this.msalService.instance.handleRedirectPromise();

      if (resultado && resultado.account) {
        console.log("Llegó token desde Azure:", resultado);

        this.msalService.instance.setActiveAccount(resultado.account);
        localStorage.setItem("jwt", resultado.idToken);
      }

      this.verificarSesion();
    } catch (error) {
      console.error("Error al validar sesión con Azure:", error);

      this.estaLogueado = false;
      this.cargandoMsal = false;
      this.detectorCambios.detectChanges();
    }
  }

  verificarSesion(): void {
    const cuentaActiva = this.msalService.instance.getActiveAccount();
    const cuentas = this.msalService.instance.getAllAccounts();

    console.log("Cuenta activa:", cuentaActiva);
    console.log("Cuentas encontradas:", cuentas.length);

    if (!cuentaActiva && cuentas.length > 0) {
      this.msalService.instance.setActiveAccount(cuentas[0]);
    }

    const cuentasActualizadas = this.msalService.instance.getAllAccounts();

    this.estaLogueado = cuentasActualizadas.length > 0;
    this.cargandoMsal = false;

    console.log("estaLogueado:", this.estaLogueado);
    console.log("cargandoMsal:", this.cargandoMsal);

   if (this.estaLogueado) {
      this.consultarPacientes();
      this.consultarAlertasKafka();
    }

    this.detectorCambios.detectChanges();
  }

  obtenerHeaders(): HttpHeaders {
    const token = localStorage.getItem("jwt");

    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  consultarPacientes(): void {
    const token = localStorage.getItem("jwt");

    if (!token) {
      this.errorPacientes = "No se encontró token de Azure.";
      return;
    }

    this.cargandoPacientes = true;
    this.errorPacientes = "";
    this.mensajeAccion = "";

    this.http.get<Paciente[]>(`${environment.apiConfig.uri}/pacientes`, {
      headers: this.obtenerHeaders()
    }).subscribe({
      next: (respuesta) => {
        console.log("Pacientes recibidos:", respuesta);
        this.pacientes = respuesta;
        this.prepararFormularioNuevo();
        this.cargandoPacientes = false;
        this.detectorCambios.detectChanges();
      },
      error: (error) => {
        console.error("Error al consultar pacientes:", error);
        this.errorPacientes = "No se pudieron cargar los pacientes.";
        this.mensajeAccion = "";
        this.cargandoPacientes = false;
        this.detectorCambios.detectChanges();
      }
    });
  }

  consultarAlertasKafka(): void {
  const token = localStorage.getItem("jwt");

  if (!token) {
    this.errorAlertasKafka = "No se encontró token de Azure.";
    return;
  }

  this.cargandoAlertasKafka = true;
  this.errorAlertasKafka = "";

  this.http.get<AlertaKafka[]>(`${environment.apiConfig.uri}/alertas-kafka`, {
    headers: this.obtenerHeaders()
  }).subscribe({
    next: (respuesta) => {
      console.log("Alertas Kafka recibidas:", respuesta);

      this.alertasKafka = respuesta;
      this.cargandoAlertasKafka = false;

      this.detectorCambios.detectChanges();
    },
    error: (error) => {
      console.error("Error al consultar alertas Kafka:", error);

      this.errorAlertasKafka = "No se pudieron cargar las alertas Kafka.";
      this.cargandoAlertasKafka = false;

      this.detectorCambios.detectChanges();
    }
  });
}

  prepararFormularioNuevo(): void {
    const ids = this.pacientes.map(paciente => paciente.idPaciente);
    const siguienteId = ids.length > 0 ? Math.max(...ids) + 1 : 1;

    this.modoEdicion = false;

    this.pacienteFormulario = {
      idPaciente: siguienteId,
      nombreApellido: "",
      rut: "",
      criticidad: "MEDIO",
      fechaLlegada: "2026-06-08",
      fechaSalida: null,
      temperatura: 37.0,
      habitacion: "",
      signosVitales: ""
    };
  }

  validarFormularioPaciente(): string {
    if (!this.pacienteFormulario.nombreApellido.trim()) {
      return "Debe ingresar el nombre y apellido del paciente.";
    }

    if (!this.pacienteFormulario.rut.trim()) {
      return "Debe ingresar el RUT del paciente.";
    }

    if (!this.pacienteFormulario.criticidad.trim()) {
      return "Debe seleccionar la criticidad del paciente.";
    }

    if (!this.pacienteFormulario.fechaLlegada) {
      return "Debe ingresar la fecha de llegada del paciente.";
    }

    if (
      this.pacienteFormulario.temperatura === null ||
      this.pacienteFormulario.temperatura === undefined
    ) {
      return "Debe ingresar la temperatura del paciente.";
    }

    if (this.pacienteFormulario.temperatura < 30 || this.pacienteFormulario.temperatura > 45) {
      return "La temperatura debe estar entre 30 y 45 grados.";
    }

    if (!this.pacienteFormulario.habitacion.trim()) {
      return "Debe ingresar la habitación del paciente.";
    }

    if (!this.pacienteFormulario.signosVitales.trim()) {
      return "Debe ingresar los signos vitales del paciente.";
    }

    return "";
  }

  guardarPaciente(): void {
    const errorValidacion = this.validarFormularioPaciente();

    if (errorValidacion) {
      this.errorPacientes = errorValidacion;
      this.mensajeAccion = "";
      return;
    }

    if (this.modoEdicion) {
      this.actualizarPaciente();
    } else {
      this.crearPaciente();
    }
  }

  crearPaciente(): void {
    this.errorPacientes = "";
    this.mensajeAccion = "";

    this.http.post<Paciente>(`${environment.apiConfig.uri}/pacientes`, this.pacienteFormulario, {
      headers: this.obtenerHeaders()
    }).subscribe({
      next: () => {
        this.mensajeAccion = "Paciente creado correctamente.";
        this.errorPacientes = "";
        this.consultarPacientes();
        this.detectorCambios.detectChanges();
      },
      error: (error) => {
        console.error("Error al crear paciente:", error);
        this.errorPacientes = "No se pudo crear el paciente.";
        this.mensajeAccion = "";
        this.detectorCambios.detectChanges();
      }
    });
  }

  cargarPacienteParaEditar(paciente: Paciente): void {
    this.modoEdicion = true;
    this.mensajeAccion = "";
    this.errorPacientes = "";

    this.pacienteFormulario = {
      ...paciente
    };

    this.detectorCambios.detectChanges();
  }

  actualizarPaciente(): void {
    this.errorPacientes = "";
    this.mensajeAccion = "";

    this.http.put<Paciente>(
      `${environment.apiConfig.uri}/pacientes/${this.pacienteFormulario.idPaciente}`,
      this.pacienteFormulario,
      { headers: this.obtenerHeaders() }
    ).subscribe({
      next: () => {
        this.mensajeAccion = "Paciente modificado correctamente.";
        this.errorPacientes = "";
        this.consultarPacientes();
        this.detectorCambios.detectChanges();
      },
      error: (error) => {
        console.error("Error al modificar paciente:", error);
        this.errorPacientes = "No se pudo modificar el paciente.";
        this.mensajeAccion = "";
        this.detectorCambios.detectChanges();
      }
    });
  }

  eliminarPaciente(idPaciente: number): void {
    this.errorPacientes = "";
    this.mensajeAccion = "";

    this.http.delete(`${environment.apiConfig.uri}/pacientes/${idPaciente}`, {
      headers: this.obtenerHeaders()
    }).subscribe({
      next: () => {
        this.mensajeAccion = "Paciente eliminado correctamente.";
        this.errorPacientes = "";
        this.consultarPacientes();
        this.detectorCambios.detectChanges();
      },
      error: (error) => {
        console.error("Error al eliminar paciente:", error);
        this.errorPacientes = "No se pudo eliminar el paciente.";
        this.mensajeAccion = "";
        this.detectorCambios.detectChanges();
      }
    });
  }

  registrarEventoMensajeria(
    tipo: string,
    paciente: string,
    estado: string,
    detalle: string
  ): void {
    const fechaActual = new Date();

    const hora = fechaActual.toLocaleTimeString("es-CL", {
      hour: "2-digit",
      minute: "2-digit",
      second: "2-digit"
    });

    const nuevoEvento: EventoMensajeria = {
      hora,
      tipo,
      paciente,
      estado,
      detalle
    };

    this.eventosMensajeria = [nuevoEvento, ...this.eventosMensajeria].slice(0, 8);
    this.detectorCambios.detectChanges();
  }

  enviarAlertaUrgente(paciente: Paciente): void {
    this.errorPacientes = "";
    this.mensajeAccion = "";

    const alertaMsg = `ALERTA CRÍTICA: Paciente ${paciente.nombreApellido} (RUT: ${paciente.rut}) presenta signos vitales fuera de rango: ${paciente.signosVitales}.`;

    this.registrarEventoMensajeria(
      "RabbitMQ",
      paciente.nombreApellido,
      "Enviando",
      "El BFF está orquestando la alerta hacia productor-alertas."
    );

    this.http.post(
      `${environment.apiConfig.uri}/orquestador/alerta`,
      alertaMsg,
      {
        headers: this.obtenerHeaders(),
        responseType: "text"
      }
    ).subscribe({
      next: (respuesta) => {
        console.log("Respuesta alerta:", respuesta);

        this.mensajeAccion = "Alerta urgente enviada correctamente al sistema.";
        this.errorPacientes = "";

        this.registrarEventoMensajeria(
          "RabbitMQ",
          paciente.nombreApellido,
          "Enviado",
          "Alerta enviada a cola.alertas.oracle y cola.alertas.json."
        );

        this.detectorCambios.detectChanges();
      },
      error: (error) => {
        console.error("Fallo al intentar enviar la alerta", error);

        this.errorPacientes = "No pudimos enviar la alerta al sistema.";
        this.mensajeAccion = "";

        this.registrarEventoMensajeria(
          "RabbitMQ",
          paciente.nombreApellido,
          "Error",
          "No se pudo enviar la alerta desde el BFF hacia productor-alertas."
        );

        this.detectorCambios.detectChanges();
      }
    });
  }

  enviarResumenPaciente(paciente: Paciente): void {
    this.errorPacientes = "";
    this.mensajeAccion = "";

    const resumen = {
      idPaciente: paciente.idPaciente,
      nombrePaciente: paciente.nombreApellido,
      frecuenciaCardiaca: 80,
      temperatura: paciente.temperatura,
      presionArterial: "120/80",
      saturacionOxigeno: 98,
      estadoGeneral: paciente.criticidad,
      fechaResumen: new Date().toISOString()
    };

    this.registrarEventoMensajeria(
      "RabbitMQ",
      paciente.nombreApellido,
      "Enviando",
      "El BFF está enviando el resumen hacia productor-resumenes."
    );

    this.http.post(
      `${environment.apiConfig.uri}/orquestador/resumen`,
      resumen,
      {
        headers: this.obtenerHeaders(),
        responseType: "text"
      }
    ).subscribe({
      next: (respuesta) => {
        console.log("Respuesta resumen:", respuesta);

        this.mensajeAccion = "Resumen del paciente enviado correctamente.";
        this.errorPacientes = "";

        this.registrarEventoMensajeria(
          "RabbitMQ",
          paciente.nombreApellido,
          "Enviado",
          "Resumen enviado a la cola de mensajes correspondiente."
        );

        this.detectorCambios.detectChanges();
      },
      error: (error) => {
        console.error("Fallo al mandar el resumen", error);

        this.errorPacientes = "Tuvimos un problema enviando el resumen.";
        this.mensajeAccion = "";

        this.registrarEventoMensajeria(
          "RabbitMQ",
          paciente.nombreApellido,
          "Error",
          "No se pudo enviar el resumen desde el BFF hacia productor-resumenes."
        );

        this.detectorCambios.detectChanges();
      }
    });
  }

  iniciarSesion(): void {
    this.msalService.loginRedirect();
  }

  cerrarSesion(): void {
    localStorage.removeItem("jwt");
    this.estaLogueado = false;

    this.msalService.logoutRedirect({
      postLogoutRedirectUri: "http://localhost:4200"
    });
  }
}