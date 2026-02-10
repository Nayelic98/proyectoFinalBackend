# README: Plataforma de Gestion de Talentos y Proyectos

**Desarrollado por:** Nayeli Barbecho y Jordy Romero

Sistema integral para la gestion de perfiles profesionales, portafolios de proyectos y asesorias tecnicas. Desarrollado con **Spring Boot** y diseñado para integrarse con un frontend moderno en **Angular**.


## 1. Descripcion Tecnica y Arquitectura

El sistema utiliza una arquitectura **RESTful** basada en microcomponentes dentro de un monolito modular, asegurando la escalabilidad y el desacoplamiento de servicios.

### Tecnologias Clave:

* **Backend:** Java 17, Spring Boot, Spring Security (JWT).
* **Base de Datos:** PostgreSQL (Alojada en **Neon.tech**).
* **Seguridad:** Implementacion de JSON Web Tokens (JWT) para autenticacion sin estado y RBAC (Control de Acceso Basado en Roles) con roles: `ROLE_ADMIN`, `ROLE_PROGRAMMER`, `ROLE_USER`.
* **Integracion:** Configuracion de CORS habilitada para entornos de desarrollo local y produccion en Render.

---

## 2. Configuracion de Seguridad y JWT

La seguridad esta centralizada en la clase `SecurityConfig`, manejando politicas de acceso y el ciclo de vida de los tokens.

### Parametros de Autenticacion:

* **Algoritmo:** HS256.
* **Expiracion Token:** 30 minutos (1800000 ms).
* **Expiracion Refresh:** 7 dias (604800000 ms).
* **Prefijo:** Bearer.
* **Emisor:** fundamentos01-api.

### Jerarquia de Permisos:

1. **Rutas Publicas:** Acceso libre a `/auth/**`, `/status/**`, `/actuator/**`, y consultas GET para programadores y proyectos.
2. **Rutas Autenticadas:** Cualquier usuario con token valido puede acceder a `/api/users/me`, `/api/users/mi-solicitud` y postularse.
3. **Rutas de Gestion:** Los roles `ADMIN` y `PROGRAMMER` pueden gestionar proyectos. El rol `ADMIN` posee exclusividad sobre la aprobacion de solicitudes.

---

## 3. Documentacion de Endpoints REST

### Usuarios y Postulaciones (`/api/users`)

| Metodo | Endpoint | Descripcion | Acceso |
| --- | --- | --- | --- |
| `GET` | `/all` | Lista todos los usuarios registrados. | `ADMIN` |
| `POST` | `/create-programmer` | Creacion manual de programadores por admin. | `ADMIN` |
| `GET` | `/me` | Obtiene el perfil del usuario autenticado. | Autenticado |
| `POST` | `/postular` | Usuario solicita ser programador. | `USER` |
| `PATCH` | `/{id}/estado` | Aprueba o rechaza una postulacion. | `ADMIN` |

### Gestion de Proyectos (`/api/proyectos`)

| Metodo | Endpoint | Descripcion | Acceso |
| --- | --- | --- | --- |
| `GET` | `/` | Lista global de proyectos. | Publico |
| `POST` | `/` | Registro de un nuevo proyecto. | `ADMIN`, `PROGRAMMER` |
| `PUT` | `/{id}` | Actualizacion de datos de un proyecto. | `ADMIN`, `PROGRAMMER` |
| `DELETE` | `/{id}` | Eliminacion de un proyecto. | `ADMIN`, `PROGRAMMER` |


## 4. Guia de Uso

### Para el Programador (Panel de Gestion)

El programador dispone de herramientas para administrar su carrera dentro de la plataforma:

* **Gestion de Portafolio:** Puede crear, editar y eliminar sus proyectos personales para mostrar sus habilidades.
* **Control de Asesorias:** Visualiza las solicitudes enviadas por los usuarios.
* **Agenda de Horarios:** Consulta los horarios especificos en los que tiene asesorias agendadas.
* **Contacto via WhatsApp:** Al aceptar una asesoria, el sistema habilita un enlace que lo redirige directamente al chat de WhatsApp del usuario interesado para coordinar la sesion.

### Para el Usuario (Postulacion y Aprendizaje)

El usuario tiene un flujo orientado al crecimiento profesional:

* **Postulacion:** Puede postularse para cambiar su rol a **Programador** y empezar a subir sus propios proyectos.
* **Solicitud de Asesorias:** Puede explorar proyectos de expertos y solicitar una asesoria tecnica personalizada.

---

## 5. Guia de Despliegue (Render + Neon)

### Configuracion de Base de Datos (Neon.tech)

El proyecto esta configurado para conectar con una instancia de PostgreSQL en Neon utilizando las siguientes credenciales:

* **Host:** `ep-rough-art-aivb6r9h-pooler.c-4.us-east-1.aws.neon.tech`
* **Database:** `neondb`
* **User:** `neondb_owner`
* **Port:** `5432`

### Configuracion en Render (application.yml)

Para el despliegue en Render, se utilizan variables de entorno para proteger los datos sensibles:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}?sslmode=require
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update

app:
  jwt:
    secret: ${JWT_SECRET}

```

### Pasos para Desplegar:

1. Conectar el repositorio en Render.
2. Definir las **Environment Variables** en el panel de Render:
* `DB_HOST`: ep-rough-art-aivb6r9h-pooler.c-4.us-east-1.aws.neon.tech
* `DB_NAME`: neondb
* `DB_USERNAME`: neondb_owner
* `DB_PASSWORD`: (Tu contraseña de Neon)
* `JWT_SECRET`: (Tu clave secreta de al menos 256 bits)


3. **Build Command:** `./mvnw clean package -DskipTests`
4. **Start Command:** `java -jar target/*.jar`

---

**URL VIDEO:**
[https://1drv.ms/v/c/c53fc92d9f78cd25/IQDB8LYGSs6GTaFjfbKAFQBhAeVaYOY5H_Up9q6ZoeabSBk?e=lhYVn4](https://1drv.ms/v/c/c53fc92d9f78cd25/IQDB8LYGSs6GTaFjfbKAFQBhAeVaYOY5H_Up9q6ZoeabSBk?e=lhYVn4)

