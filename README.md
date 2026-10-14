# CineFlow - Backend API

CineFlow Backend es una API RESTful desarrollada en Java con Spring Boot que provee la lógica de negocio, persistencia y seguridad para el sistema de gestión de salas de cine. Implementa una arquitectura multicapa y utiliza JWT para la autenticación sin estado.

## Tecnologías Principales
* **Java 21**
* **Spring Boot 3.x** (Web, Data JPA, Security, Validation)
* **PostgreSQL** (NeonDB)
* **JSON Web Tokens (JWT)** (io.jsonwebtoken)
* **Lombok**
* **Springdoc OpenAPI** (Swagger UI)

## Arquitectura
El proyecto sigue una estricta separación de responsabilidades:
* **Controllers (`@RestController`):** Exponen los endpoints HTTP y manejan el ruteo.
* **Handlers (`@Component`):** Orquestan las conversiones entre Entidades y DTOs utilizando los Mappers, manteniendo los Controllers limpios.
* **Services (`@Service`):** Contienen toda la lógica de negocio y validaciones transaccionales (`@Transactional`).
* **Repositories (`@Repository`):** Interfaces de Spring Data JPA para el acceso a la base de datos PostgreSQL.
* **Mappers:** Clases dedicadas a la transformación bidireccional entre DTOs y Entidades.

## Características Destacadas
* **Autenticación JWT Stateless:** Filtro personalizado (`JwtAuthenticationFilter`) que extrae y valida tokens, asignando roles de seguridad (`ADMIN`, `CLIENTE`) al contexto de Spring Security.
* **Borrado Lógico (Soft Delete):** Todas las entidades principales heredan una bandera `is_activo`. Los endpoints de eliminación utilizan `@PatchMapping` para desactivar los registros sin perder la integridad referencial.
* **Manejo Global de Excepciones:** `GlobalExceptionHandler` captura errores de validación (`@Valid`), accesos denegados, y reglas de negocio (`BusinessException`, `NotFoundException`), devolviendo siempre una estructura estandarizada (`ApiResponse`).
* **Prevención de Cruces de Horario:** Lógica a nivel de base de datos (`@Query` en `FuncionRepository`) para impedir la programación de funciones solapadas en la misma sala.

## Estructura de Módulos
* `auth`: Login y emisión de tokens.
* `usuarios`: Gestión de cuentas, perfiles de usuario y cambio de contraseñas.
* `peliculas`: Catálogo de películas (CRUD completo).
* `funciones`: Asignación de películas a salas con validación de rangos de fecha y hora.
* `salas` y `sucursales`: Catálogos de solo lectura para la infraestructura física del cine.

## Ejecución Local
1. Clona el repositorio.
2. Configura las variables de entorno necesarias (ej. `DB_PASSWORD`, `JWT_SECRET`, `ADMIN_PASSWORD`).
3. Compila y ejecuta mediante Maven:
   ```bash
   ./mvnw spring-boot:run
