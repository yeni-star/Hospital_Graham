export const environment = {
  production: false,
  msalConfig: {
    auth: {
      // 1. Pega aquí tu Client ID (Application ID)
      clientId: 'a9840079-baa0-4034-87c2-5585221d5b39', 
      
      // 2. Construye tu Authority con el Tenant y el Flujo de Usuario
      // Reemplaza "NOMBRE_TENANT" (ej: alertasmedicas) y "NOMBRE_FLUJO" (ej: B2C_1_signupsignin)
      authority: 'https://Grupo3duoc.b2clogin.com/Grupo3duoc.onmicrosoft.com/B2C_1_LOGIN_AND_REGISTER_GRUPO_3', 
    },
  },
  apiConfig: {
    scopes: ['openid', 'profile'], 
    uri: 'http://localhost:8080/api', // Aquí conectaremos el BFF (Spring Boot) más adelante
  },
};