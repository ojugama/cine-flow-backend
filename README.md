# CineFlow - Backend API

CineFlow Backend es una API RESTful desarrollada en Java con Spring Boot que provee la lógica de negocio, persistencia y seguridad para el sistema de gestión de salas de cine. Implementa una arquitectura multicapa y utiliza JWT para la autenticación sin estado.

## Tecnologías Principales
* **Java 21**
* **Spring Boot 4.x** (Web, Data MongoDB, Security, Validation)
* **MongoDB** (Spring Data MongoDB)
* **JSON Web Tokens (JWT)** (io.jsonwebtoken)
* **Lombok**
* **Springdoc OpenAPI** (Swagger UI)

## Arquitectura
El proyecto sigue una estricta separación de responsabilidades:
* **Controllers (`@RestController`):** Exponen los endpoints HTTP y manejan el ruteo.
* **Handlers (`@Component`):** Orquestan las conversiones entre Entidades y DTOs utilizando los Mappers, manteniendo los Controllers limpios.
* **Services (`@Service`):** Contienen toda la lógica de negocio y validaciones de los casos de uso.
* **Repositories (`@Repository`):** Interfaces de Spring Data MongoDB para el acceso a la base de datos MongoDB. Las referencias entre documentos usan `@DBRef` y los identificadores numéricos se generan con una colección de secuencias (`counters`).
* **Mappers:** Clases dedicadas a la transformación bidireccional entre DTOs y Entidades.

## Características Destacadas
* **Autenticación JWT Stateless:** Filtro personalizado (`JwtAuthenticationFilter`) que extrae y valida tokens, asignando roles de seguridad (`ADMIN`, `CLIENTE`) al contexto de Spring Security.
* **Borrado Lógico (Soft Delete):** Todas las entidades principales manejan una bandera `isActivo`. Los endpoints de eliminación utilizan `@PatchMapping` para desactivar los registros sin perder la integridad referencial.
* **Manejo Global de Excepciones:** `GlobalExceptionHandler` captura errores de validación (`@Valid`), accesos denegados, y reglas de negocio (`BusinessException`, `NotFoundException`), devolviendo siempre una estructura estandarizada (`ApiResponse`).
* **Prevención de Cruces de Horario:** Consulta directa sobre MongoDB para impedir la programación de funciones solapadas en la misma sala.

## Estructura de Módulos
* `auth`: Login y emisión de tokens.
* `usuarios`: Gestión de cuentas, perfiles de usuario y cambio de contraseñas.
* `peliculas`: Catálogo de películas (CRUD completo).
* `funciones`: Asignación de películas a salas con validación de rangos de fecha y hora.
* `salas` y `sucursales`: Catálogos de la infraestructura física del cine.

## Datos iniciales
Al iniciar con una base vacía, la aplicación crea el usuario administrador y un catálogo de demostración (sucursales, salas, películas y funciones). Estos seeders se desactivan con el perfil `test`.

## Ejecución Local
1. Clona el repositorio.
2. Configura las variables de entorno necesarias:
   ```bash
   MONGODB_URI=mongodb://localhost:27017/cineflow
   JWT_SECRET=una-clave-secreta-de-al-menos-32-caracteres
   ADMIN_PASSWORD=tu-password-admin
   ```
3. Compila y ejecuta mediante Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

