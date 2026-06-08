# Hospital Graham - Sistema de Alertas Médicas

Este proyecto corresponde a un sistema de alertas médicas para el Hospital Graham. La aplicación permite gestionar pacientes críticos mediante un frontend desarrollado en Angular y un backend BFF desarrollado en Spring Boot.

El sistema utiliza autenticación mediante Azure B2C como Identity as a Service, validación de token JWT, API Gateway en AWS como API Manager, despliegue del backend en Docker sobre una instancia EC2 y persistencia de datos en Oracle Cloud.

## Integrantes

* Yenifer Tapia
* Nicolás Gutiérrez

## Arquitectura del sistema

El flujo principal del sistema es el siguiente:

```text
Usuario
↓
Frontend Angular
↓
Azure B2C / Identity as a Service
↓
Token JWT
↓
AWS API Gateway
↓
BFF Spring Boot en Docker sobre EC2
↓
Oracle Cloud Database
```

## Tecnologías utilizadas

* Angular
* TypeScript
* Spring Boot
* Java
* Spring Security
* JWT
* Azure B2C
* AWS API Gateway
* AWS EC2
* Docker
* Docker Hub
* Oracle Cloud Database
* Postman

## Funcionalidades principales

El sistema permite realizar las siguientes operaciones sobre pacientes críticos:

* Consultar pacientes
* Crear pacientes
* Modificar pacientes
* Eliminar pacientes
* Autenticación de usuario mediante Azure B2C
* Validación de token JWT
* Consumo de API protegida mediante API Gateway

## Endpoints principales

Los endpoints del BFF son:

```text
GET     /api/pacientes
POST    /api/pacientes
PUT     /api/pacientes/{id}
DELETE  /api/pacientes/{id}
```

Estos endpoints se encuentran publicados mediante AWS API Gateway.

## Seguridad

La autenticación del sistema se realiza mediante Azure B2C. El usuario debe iniciar sesión desde el frontend y, una vez autenticado, Azure entrega un token JWT.

Este token es enviado en cada solicitud hacia el API Gateway usando el header:

```text
Authorization: Bearer <token>
```

API Gateway valida el token antes de redirigir la solicitud al backend. Además, el BFF también valida el JWT mediante Spring Security.

## Backend BFF

El backend fue desarrollado en Spring Boot y cumple el rol de BFF para gestionar la comunicación entre el frontend y la base de datos.

El backend se conecta a Oracle Cloud Database y permite ejecutar operaciones CRUD sobre la tabla de pacientes.

## Frontend Angular

El frontend permite al usuario iniciar sesión mediante Azure B2C y luego acceder al panel de pacientes críticos.

Desde el panel se pueden realizar las operaciones:

* Listar pacientes
* Crear paciente
* Modificar paciente
* Eliminar paciente

También se agregaron validaciones simples en el formulario para evitar enviar datos incompletos.

## Despliegue con Docker

El backend fue empaquetado en una imagen Docker y subido a Docker Hub.

Nombre de la imagen:

```text
darknight17/bff-hospital-graham:latest
```

Comando usado para ejecutar el contenedor en EC2:

```bash
sudo docker run -d --name bff-hospital-graham -p 8080:8080 darknight17/bff-hospital-graham:latest
```

Para revisar el contenedor:

```bash
sudo docker ps
```

Para revisar logs:

```bash
sudo docker logs bff-hospital-graham
```

## API Gateway

AWS API Gateway se utilizó como API Manager para exponer públicamente los endpoints del BFF.

Las rutas configuradas fueron:

```text
GET     /api/pacientes
POST    /api/pacientes
PUT     /api/pacientes/{id}
DELETE  /api/pacientes/{id}
```

Además, se configuró JWT Auth para proteger las rutas del API.

## Base de datos

La base de datos utilizada corresponde a Oracle Cloud Database.

La información de los pacientes se almacena y se actualiza desde el backend BFF. Las operaciones realizadas desde el frontend y Postman se ven reflejadas en Oracle.

## Pruebas realizadas

Se realizaron pruebas desde Postman usando la URL pública de API Gateway.

Se validaron las operaciones:

* GET con respuesta 200 OK
* POST con respuesta 200 OK
* PUT con respuesta 200 OK
* DELETE con respuesta 200 OK
* Prueba sin token con respuesta Unauthorized

También se validó el funcionamiento desde el frontend Angular.

## Consideración importante

Como el despliegue se realizó en AWS Academy, la IP pública de la instancia EC2 puede cambiar cuando el laboratorio se apaga y se vuelve a iniciar.

Si la IP cambia, se debe actualizar la integración del API Gateway para que apunte nuevamente a la IP pública actual de la instancia EC2.

## Ejecución local del frontend

Para ejecutar el frontend:

```bash
npm install
ng serve
```

Luego abrir en el navegador:

```text
http://localhost:4200
```

## Ejecución local del backend

Para compilar el backend:

```bash
mvn clean package
```

Para construir la imagen Docker:

```bash
docker build -t bff-hospital-graham .
```

Para etiquetar la imagen:

```bash
docker tag bff-hospital-graham darknight17/bff-hospital-graham:latest
```

Para subir la imagen a Docker Hub:

```bash
docker push darknight17/bff-hospital-graham:latest
```

## Estado final del proyecto

El sistema quedó funcionando con autenticación mediante Azure B2C, consumo de servicios REST protegidos por JWT, API Gateway como API Manager, backend desplegado en Docker sobre AWS EC2 y conexión a Oracle Cloud.
