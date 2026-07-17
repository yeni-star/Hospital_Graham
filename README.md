# Hospital Graham - Sistema de Alertas Médicas Cloud Native

Proyecto desarrollado para la asignatura **Desarrollo Cloud Native I - DSY2206**.

El sistema corresponde a una plataforma para gestionar pacientes del Hospital Graham y demostrar una arquitectura cloud native usando frontend Angular, backend BFF, autenticación con Azure B2C, API Gateway, Oracle Cloud, RabbitMQ, Kafka, Docker y despliegue en AWS EC2.

## Integrantes

- Yenifer Tapia
- Nicolás Gutiérrez

## Descripción general

La aplicación permite iniciar sesión mediante Azure B2C, consultar pacientes, crear nuevos registros, modificar datos, eliminar pacientes y enviar eventos asíncronos mediante RabbitMQ y Kafka.

El proyecto integra tres flujos principales:

```text
Frontend Angular → API Gateway → BFF Spring Boot → Oracle Cloud
```

```text
Frontend Angular → API Gateway → BFF → productor-alertas → RabbitMQ → consumidor-oracle → ALERTAS_MEDICAS
```

```text
productor-senales → Kafka → procesador-senales → consumidor-alertas-oracle → ALERTAS_KAFKA → BFF → API Gateway → Frontend
```

## Tecnologías utilizadas

- Angular
- TypeScript
- Java 17
- Spring Boot
- Spring Security
- JWT
- Azure B2C
- AWS API Gateway
- AWS EC2
- Docker
- Docker Hub
- Oracle Cloud Autonomous Database
- RabbitMQ
- Apache Kafka
- Zookeeper
- Maven

## Estructura del proyecto

```text
Hospital_Graham/
│
├── frontend/
│   └── Angular/
│
├── backend/
│   └── bff-hospital-graham/
│
├── productor_alertas/
├── productor_resumenes/
├── consumidor_json/
├── consumidor_oracle/
│
├── productor_senales/
├── procesador_senales/
├── consumidor_alertas_oracle/
│
├── docker-compose.yml
└── README.md
```

## Frontend Angular

El frontend permite al usuario autenticarse con Azure B2C y acceder al panel principal del sistema.

Funciones principales del frontend:

- Iniciar sesión con Azure B2C.
- Consultar pacientes desde Oracle.
- Crear pacientes.
- Modificar pacientes.
- Eliminar pacientes.
- Enviar alerta manual mediante RabbitMQ.
- Enviar resumen de paciente mediante RabbitMQ.
- Visualizar alertas Kafka reales almacenadas en Oracle.

La aplicación se ejecuta localmente en:

```text
http://localhost:4200
```

## Backend BFF

El backend principal corresponde a un BFF desarrollado en Spring Boot.

Este backend recibe las solicitudes del frontend, valida el token JWT y se comunica con Oracle Cloud y con los microservicios productores.

Endpoints principales:

```text
GET     /api/pacientes
POST    /api/pacientes
PUT     /api/pacientes/{id}
DELETE  /api/pacientes/{id}
```

Endpoints de orquestación:

```text
POST    /api/orquestador/alerta
POST    /api/orquestador/resumen
```

Endpoint para mostrar alertas Kafka en el frontend:

```text
GET     /api/alertas-kafka
```

## Seguridad con Azure B2C

La autenticación se realiza mediante Azure B2C como Identity as a Service.

El usuario inicia sesión desde Angular y Azure entrega un token JWT. Ese token se envía en cada solicitud con el header:

```text
Authorization: Bearer <token>
```

El API Gateway y el BFF validan el token antes de permitir el acceso a los endpoints protegidos.

## API Gateway

AWS API Gateway se utiliza como API Manager para exponer públicamente las rutas del BFF.

Rutas configuradas:

```text
GET     /api/pacientes
POST    /api/pacientes
PUT     /api/pacientes/{id}
DELETE  /api/pacientes/{id}
POST    /api/orquestador/alerta
POST    /api/orquestador/resumen
GET     /api/alertas-kafka
```

URL base usada desde el frontend:

```text
https://uwbn20sm3h.execute-api.us-east-1.amazonaws.com/api
```

La integración apunta al BFF desplegado en EC2 por el puerto 8080.

> Importante: si la IP pública de la EC2 cambia, se debe actualizar la integración en API Gateway y también permitir la nueva IP en Oracle Cloud.

## Base de datos Oracle Cloud

La base de datos utilizada corresponde a Oracle Cloud Autonomous Database.

Tablas principales usadas:

```text
PACIENTES
ALERTAS_MEDICAS
ALERTAS_KAFKA
PACIENTES_CRITICOS
SIGNOS_VITALES
```

Durante la integración se corrigió la llave foránea de `ALERTAS_MEDICAS`, para que apunte a la tabla correcta:

```text
ALERTAS_MEDICAS.ID_PACIENTE → PACIENTES.ID_PACIENTE
```

Esto permite que las alertas generadas desde RabbitMQ se puedan guardar correctamente en Oracle.

## RabbitMQ

RabbitMQ se usa para manejar mensajes asíncronos desde botones del frontend.

Contenedor utilizado:

```text
rabbitmq-alertas-medicas
```

Credenciales de prueba:

```text
usuario: admin
password: admin123
```

Puerto de mensajería:

```text
5672
```

Consola de administración:

```text
http://<IP_EC2>:15672
```

### Colas principales

```text
cola.alertas.oracle
cola.alertas.json
cola.resumenes
```

### Flujo del botón Alerta

```text
Frontend
→ API Gateway
→ BFF
→ productor-alertas
→ RabbitMQ
→ cola.alertas.oracle
→ consumidor-oracle
→ Oracle ALERTAS_MEDICAS
```

El botón **Alerta** genera una alerta médica en formato JSON, la envía a RabbitMQ y el consumidor Oracle la guarda en la tabla `ALERTAS_MEDICAS`.

También se envía una copia hacia:

```text
cola.alertas.json
```

para que sea procesada por el consumidor JSON.

### Flujo del botón Resumen

```text
Frontend
→ API Gateway
→ BFF
→ productor-resumenes
→ RabbitMQ
→ cola.resumenes
→ consumidor-json
```

El botón **Resumen** demuestra una segunda cola de RabbitMQ. Este flujo no guarda en Oracle, sino que genera un mensaje JSON con datos del paciente para ser procesado por el consumidor JSON.

## Kafka

Kafka se utiliza para el streaming automático de señales vitales.

Componentes principales:

```text
zookeeper-1
kafka-1
productor-senales
procesador-senales
consumidor-alertas-oracle
```

### Topics utilizados

```text
senales_vitales
alertas
```

### Flujo Kafka

```text
productor-senales
→ topic senales_vitales
→ procesador-senales
→ topic alertas
→ consumidor-alertas-oracle
→ Oracle ALERTAS_KAFKA
→ BFF
→ API Gateway
→ Frontend Angular
```

El productor de señales genera lecturas de signos vitales. El procesador revisa esas señales y, cuando detecta una anomalía, publica una alerta. Luego el consumidor de alertas Oracle guarda el registro en la tabla `ALERTAS_KAFKA`.

Finalmente, el frontend consulta el endpoint:

```text
GET /api/alertas-kafka
```

y muestra las últimas alertas Kafka reales almacenadas en Oracle.

Para evitar saturación, el productor de señales fue ajustado para generar datos cada 60 segundos:

```java
@Scheduled(fixedRate = 60000)
```

## Docker Hub

Imágenes publicadas en Docker Hub:

```text
darknight17/bff-hospital-graham:latest
darknight17/productor-alertas:latest
darknight17/productor-resumenes:latest
darknight17/consumidor-json:latest
darknight17/consumidor-oracle:latest
darknight17/productor-senales:latest
darknight17/procesador-senales:latest
darknight17/consumidor-alertas-oracle:latest
```

## Despliegue en EC2

El sistema fue desplegado en AWS EC2 usando Docker.

Inicialmente se probó con una instancia `t2.micro`, pero al levantar Kafka, RabbitMQ y varios microservicios Java al mismo tiempo, la instancia se saturaba por memoria.

Por ese motivo se cambió a:

```text
t2.medium
```

Con esta instancia fue posible ejecutar los contenedores principales de forma estable.

IP pública usada durante la prueba final:

```text
23.22.114.156
```

> Esta IP puede cambiar si la instancia EC2 se detiene y se inicia nuevamente.

## Comandos útiles en EC2

Crear red Docker:

```bash
sudo docker network create red-hospital
```

Ver contenedores activos:

```bash
sudo docker ps
```

Ver contenedores creados:

```bash
sudo docker ps -a
```

Ver logs sin dejar pegada la terminal:

```bash
sudo timeout 10 docker logs --tail 80 nombre-contenedor
```

## Levantar flujo Kafka

```bash
sudo docker start zookeeper-1
sleep 30

sudo docker start kafka-1
sleep 60

sudo docker start consumidor-alertas-oracle
sleep 30

sudo docker start procesador-senales
sleep 30

sudo docker start productor-senales
sleep 70
```

Validar contenedores:

```bash
sudo timeout 10 docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Image}}"
```

Logs recomendados para la demo:

```bash
sudo timeout 10 docker logs --tail 60 productor-senales
sudo timeout 10 docker logs --tail 80 procesador-senales
sudo timeout 10 docker logs --tail 80 consumidor-alertas-oracle
```

## Levantar flujo RabbitMQ

Primero se recomienda detener Kafka si no se va a mostrar en ese momento:

```bash
sudo docker stop productor-senales procesador-senales consumidor-alertas-oracle kafka-1 zookeeper-1
```

Luego levantar RabbitMQ:

```bash
sudo docker start rabbitmq-alertas-medicas
sleep 25

sudo docker start bff-hospital-graham
sleep 15

sudo docker start productor-alertas
sleep 15

sudo docker start productor-resumenes
sleep 15

sudo docker start consumidor-json
sleep 15

sudo docker start consumidor-oracle
```

Validar colas:

```bash
sudo timeout 10 docker exec rabbitmq-alertas-medicas rabbitmqctl list_queues name messages_ready messages_unacknowledged
```

Logs recomendados:

```bash
sudo timeout 10 docker logs --tail 50 productor-alertas
sudo timeout 10 docker logs --tail 50 productor-resumenes
sudo timeout 10 docker logs --tail 50 consumidor-json
sudo timeout 10 docker logs --tail 50 consumidor-oracle
```

## Ejecución local del frontend

Desde la carpeta del frontend:

```bash
npm install
npm start
```

Luego abrir:

```text
http://localhost:4200
```

## Compilar y subir imagen del BFF

Desde la carpeta del BFF:

```bash
mvn clean package -DskipTests
```

Construir imagen:

```bash
docker build -t darknight17/bff-hospital-graham:latest .
```

Subir imagen:

```bash
docker push darknight17/bff-hospital-graham:latest
```

Actualizar en EC2:

```bash
sudo docker pull darknight17/bff-hospital-graham:latest
sudo docker rm -f bff-hospital-graham
```

Crear nuevamente el contenedor:

```bash
sudo docker create \
--name bff-hospital-graham \
--network red-hospital \
--memory=768m \
-p 8080:8080 \
-e TNS_ADMIN_PATH=/app/Wallet \
-e SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE=2 \
-e SPRING_DATASOURCE_HIKARI_MINIMUM_IDLE=1 \
-e JAVA_TOOL_OPTIONS="-Xms128m -Xmx384m" \
darknight17/bff-hospital-graham:latest
```

Levantar:

```bash
sudo docker start bff-hospital-graham
```

## Pruebas realizadas

Se validaron las siguientes pruebas:

- Inicio de sesión con Azure B2C.
- Consumo de endpoints protegidos con JWT.
- CRUD de pacientes desde Angular.
- Persistencia de pacientes en Oracle.
- Envío de alerta manual desde el frontend.
- Publicación de alerta en RabbitMQ.
- Consumo de alerta desde `consumidor-oracle`.
- Inserción de alerta en `ALERTAS_MEDICAS`.
- Envío de resumen a `cola.resumenes`.
- Consumo de resumen por `consumidor-json`.
- Generación de señales vitales con Kafka.
- Procesamiento de anomalías en Kafka.
- Inserción de alertas Kafka en `ALERTAS_KAFKA`.
- Visualización de alertas Kafka en el frontend.

## Consideraciones importantes

- Si la instancia EC2 cambia de IP pública, se debe actualizar:
  - API Gateway.
  - Lista de acceso de Oracle Cloud.
- La instancia `t2.micro` no fue suficiente para ejecutar todos los microservicios juntos.
- Para la prueba final se utilizó `t2.medium`.
- Kafka y RabbitMQ pueden levantarse por separado para evitar consumo innecesario.
- El Wallet de Oracle y las credenciales deben tratarse como información sensible.

## Estado final del proyecto

El sistema quedó funcionando con:

```text
Azure B2C ✅
Frontend Angular ✅
API Gateway ✅
BFF Spring Boot en EC2 ✅
Oracle Cloud ✅
RabbitMQ ✅
Kafka ✅
Docker Hub ✅
```
