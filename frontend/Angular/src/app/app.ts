import { Component, OnInit, OnDestroy } from '@angular/core';
import { MsalService, MsalBroadcastService } from '@azure/msal-angular';
import { AuthenticationResult, InteractionStatus } from '@azure/msal-browser';
import { Subject } from 'rxjs';
import { filter, takeUntil } from 'rxjs/operators';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class AppComponent implements OnInit, OnDestroy {
  estaLogueado = false;
  private readonly _destruir$ = new Subject<void>();

  constructor(
    private msalService: MsalService,
    private msalBroadcastService: MsalBroadcastService
  ) {}

  ngOnInit(): void {
    // Atrapamos al usuario cuando vuelve de Azure
    this.msalService.handleRedirectObservable().subscribe({
      next: (resultado: AuthenticationResult | null) => {
        if (resultado) {
          console.log("¡Llegó token desde Azure!", resultado);
          this.msalService.instance.setActiveAccount(resultado.account);
          localStorage.setItem('jwt', resultado.idToken);
        }
      },
      error: (error) => console.error('Error al procesar la vuelta de Azure:', error)
    });

    // Esperamos a que MSAL estabilice su estado interno
    this.msalBroadcastService.inProgress$
      .pipe(
        filter((estado: InteractionStatus) => estado === InteractionStatus.None),
        takeUntil(this._destruir$)
      )
      .subscribe(() => {
        console.log("Carga de MSAL terminada. Revisando estado de cuentas...");
        this.verificarCuentaActiva();
      });
  }

  verificarCuentaActiva() {
    const cuentas = this.msalService.instance.getAllAccounts();
    console.log("Cuentas guardadas en memoria:", cuentas.length);

    if (cuentas.length > 0) {
      this.msalService.instance.setActiveAccount(cuentas[0]);
      this.estaLogueado = true;
    } else {
      this.estaLogueado = false;
    }
  }

  iniciarSesion() {
    this.msalService.loginRedirect();
  }

  cerrarSesion() {
    localStorage.removeItem('jwt');
    this.estaLogueado = false;
    this.msalService.logoutRedirect();
  }

  ngOnDestroy(): void {
    this._destruir$.next(undefined);
    this._destruir$.complete();
  }
}