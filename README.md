# 🎮 Sistema de Ventas de Videojuegos

Aplicación web para gestionar el catálogo de videojuegos, clientes y ventas de una tienda, con control automático de stock.

Construida con **Spring Boot**, **Thymeleaf** y **Bootstrap** como proyecto académico.

## Características

- CRUD completo de **Videojuegos** (título, plataforma, género, precio, stock)
- CRUD completo de **Clientes** (con validación de correo duplicado)
- **Registro de ventas** con control de stock: no permite vender más unidades de las disponibles y descuenta el stock automáticamente
- Validaciones de formulario en servidor (campos obligatorios, precios/cantidades positivas) y feedback visual en la interfaz
- Manejo de errores centralizado (página de error clara en vez del error genérico de Spring)
- Protección ante borrado de registros con relaciones activas (no se puede eliminar un videojuego o cliente con ventas asociadas)
- Panel principal con resumen de totales e ingresos

## Tecnologías

| Componente     | Tecnología             |
|----------------|-------------------------|
| Backend        | Spring Boot 3.3, Spring Web, Spring Data JPA |
| Vistas         | Thymeleaf                |
| Estilos        | Bootstrap 5 (CDN)        |
| Base de datos  | H2 (desarrollo) / PostgreSQL — [Neon](https://neon.tech) (producción) |
| Build          | Maven                    |
| Java           | 17                        |

## Estructura del proyecto

```
src/main/java/com/cartagena/ventas/
├── model/          Entidades JPA (Videojuego, Cliente, Venta, DetalleVenta)
├── repository/      Interfaces JpaRepository
├── service/          Lógica de negocio (stock, validaciones)
├── controller/       Controladores MVC
├── dto/              Objetos de formulario (VentaForm)
└── exception/         Manejo global de errores
src/main/resources/
├── templates/          Vistas Thymeleaf
├── application.properties        Configuración común
├── application-dev.properties     Perfil desarrollo (H2)
├── application-prod.properties    Perfil producción (PostgreSQL / Neon)
└── data.sql                        Datos de ejemplo (solo se insertan una vez)
```

## Cómo ejecutarlo

### Opción A — Desarrollo local con H2 (por defecto, sin configurar nada)

```bash
mvn spring-boot:run
```

O desde NetBeans: abrir el proyecto (File > Open Project) y ejecutar `VentasApplication.java`.

La app corre en `http://localhost:8080`, con una base de datos H2 que persiste en `./data/ventasdb.mv.db` entre reinicios. Consola H2 disponible en `http://localhost:8080/h2-console`.

### Opción B — Con PostgreSQL / Neon

1. Crea una base de datos gratuita en [neon.tech](https://neon.tech) y copia la cadena de conexión que te dan (algo como `jdbc:postgresql://ep-xxxx.neon.tech/neondb`), el usuario y la contraseña.
2. Define las variables de entorno:

```bash
export SPRING_PROFILES_ACTIVE=prod
export DB_URL=jdbc:postgresql://<tu-host>.neon.tech/<tu-db>?sslmode=require
export DB_USERNAME=<tu-usuario>
export DB_PASSWORD=<tu-contraseña>
```

3. Ejecuta normalmente: `mvn spring-boot:run`.

> Las credenciales nunca se escriben en el código ni se suben a GitHub — se leen desde variables de entorno.

## Licencia

MIT — ver [LICENSE](LICENSE).
