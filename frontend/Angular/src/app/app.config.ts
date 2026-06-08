import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { environment } from '../environments/environment';

// Traemos las herramientas necesarias de la librería de Microsoft
import { MsalService, MSAL_INSTANCE, MsalGuard, MsalBroadcastService } from '@azure/msal-angular';
import { BrowserCacheLocation, IPublicClientApplication, PublicClientApplication } from '@azure/msal-browser';

// Esta función arma el objeto de configuración sacando los datos de tu environment.ts
export function MSALInstanceFactory(): IPublicClientApplication {
  return new PublicClientApplication({
    auth: {
      clientId: environment.msalConfig.auth.clientId,
      authority: environment.msalConfig.auth.authority,
      // MSAL es desconfiado por defecto. Hay que decirle explícitamente que confíe en tu dominio B2C
      knownAuthorities: ['grupo3duoc.b2clogin.com'], 
      redirectUri: 'http://localhost:4200/',
    },
    cache: {
      // Guardamos la sesión en el disco local del navegador para no perderla cuando Azure nos devuelve a la página
      cacheLocation: BrowserCacheLocation.LocalStorage 
    }
  });
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    // Aquí le enseñamos a Angular cómo construir y usar MSAL en toda la app
    {
      provide: MSAL_INSTANCE,
      useFactory: MSALInstanceFactory
    },
    MsalService,
    MsalGuard,
    MsalBroadcastService
  ]
};