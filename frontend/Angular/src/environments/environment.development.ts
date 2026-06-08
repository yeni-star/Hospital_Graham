export const environment = {
  production: false,
  msalConfig: {
    auth: {
      // 1. Pega aquí tu Client ID (Application ID)
      clientId: '34df68d0-6050-4fec-85f5-5ad5c103336b', 
      
      // 2. Construye tu Authority con el Tenant y el Flujo de Usuario
      // Reemplaza "NOMBRE_TENANT" (ej: alertasmedicas) y "NOMBRE_FLUJO" (ej: B2C_1_signupsignin)
      authority: 'https://grupo3duoc.b2clogin.com/grupo3duoc.onmicrosoft.com/B2C_1_LOGIN_AND_REGISTER_GRUPO_3', 
    },
  },
  apiConfig: {
    scopes: ['openid', 'profile'], 
    uri: 'http://localhost:8080/api', // Aquí conectaremos el BFF (Spring Boot) más adelante
  },
};