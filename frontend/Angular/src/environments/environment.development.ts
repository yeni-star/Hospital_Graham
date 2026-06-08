export const environment = {
  production: false,
  msalConfig: {
    auth: {
      clientId: "34df68d0-6050-4fec-85f5-5ad5c103336b",
      authority: "https://grupo3duoc.b2clogin.com/grupo3duoc.onmicrosoft.com/B2C_1_LOGIN_AND_REGISTER_GRUPO_3",
    },
  },
  apiConfig: {
    scopes: ["openid", "profile"],
    uri: "https://uwbn20sm3h.execute-api.us-east-1.amazonaws.com/api",
  },
};