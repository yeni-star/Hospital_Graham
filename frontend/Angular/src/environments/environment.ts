export const environment = {
  production: false,
  msalConfig: {
    auth: {
      clientId: '34df68d0-6050-4fec-85f5-5ad5c103336b', 
      // DEBE ir todo en minúsculas para que coincida con el dominio de confianza
      authority: 'https://grupo3duoc.b2clogin.com/grupo3duoc.onmicrosoft.com/B2C_1_LOGIN_AND_REGISTER_GRUPO_3', 
    },
  },
  apiConfig: {
    scopes: ['openid', 'profile'], 
    uri: 'http://localhost:8080/api',
  },
};