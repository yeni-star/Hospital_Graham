import { Component, OnInit } from '@angular/core';
import { MsalService } from '@azure/msal-angular';
import { AuthenticationResult } from '@azure/msal-browser';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class AppComponent implements OnInit {
  estaLogueado = false;

  constructor(private msalService: MsalService) {}

  ngOnInit(): void {
    // Nos quedamos escuchando por si el usuario viene aterrizando desde la página de Microsoft
    this.msalService.handleRedirectObservable().subscribe({
      // ¡Acá está el cambio! Le avisamos a TypeScript que el resultado también puede venir nulo
      next: (resultado: AuthenticationResult | null) => { 
        if (resultado) {
          // Llegó con datos, lo dejamos pasar y guardamos su sesión
          this.msalService.instance.setActiveAccount(resultado.account);
          localStorage.setItem('jwt', resultado.idToken);
          this.estaLogueado = true;
        } else {
          // Venía nulo, así que revisamos si ya estaba logueado de una visita anterior
          this.verificarCuentaActiva();
        }
      },
      error: (error) => {
        console.error('Uy, algo falló al volver de Azure:', error);
      }
    });
  }

  verificarCuentaActiva() {
    const cuentas = this.msalService.instance.getAllAccounts();
    if (cuentas.length > 0) {
      this.msalService.instance.setActiveAccount(cuentas[0]);
      this.estaLogueado = true;
    }
  }

  // Esta función ahora la llamaremos desde un botón
  iniciarSesion() {
    this.msalService.loginRedirect();
  }

  cerrarSesion() {
    localStorage.removeItem('jwt');
    this.msalService.logoutRedirect({
      postLogoutRedirectUri: 'http://localhost:4200'
    });
  }
}