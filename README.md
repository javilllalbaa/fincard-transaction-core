# FinCard Transaction Core

Proyecto base de arquitectura backend con:

- Java 21
- Spring Boot 3.5.x
- Maven
- Spring Web
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL 16
- Redis 7
- Apache Kafka
- Springdoc OpenAPI
- JUnit 5 + Mockito
- Docker Compose
- C4 Model + ADR

## Estructura

```text
fincard-transaction-core/
├── authorization-service/
├── audit-service/
├── database/
├── docs/
│   ├── architecture/
│   └── adr/
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Requisitos

- JDK 21
- Maven 3.9+
- Docker Desktop

## Compilar

```bash
mvn clean test package
```

## Levantar infraestructura

```bash
docker compose up -d postgres redis kafka
```

## Construir imágenes

```bash
mvn clean package -DskipTests
docker compose build
```

## Levantar todo

```bash
docker compose up -d
```

Servicios:

- Authorization: http://localhost:8081
- Audit: http://localhost:8082
- Swagger Authorization: http://localhost:8081/swagger-ui.html
- Swagger Audit: http://localhost:8082/swagger-ui.html

## Login

Usuario:

```text
demo
```

Password:

```text
password
```

Request:

```bash
curl -X POST http://localhost:8081/api/v1/auth/login   -H 'Content-Type: application/json'   -d '{"username":"demo","password":"password"}'
```

## Endpoint protegido

```bash
curl http://localhost:8081/api/v1/auth/me   -H "Authorization: Bearer <TOKEN>"
```

## C4

Los tres niveles están en:

```text
docs/architecture/c4-context.puml
docs/architecture/c4-container.puml
docs/architecture/c4-component.puml
```

Se pueden renderizar con PlantUML.

## Nota de producción

El secreto JWT incluido en `application.yml` es únicamente para desarrollo. En ambientes reales debe inyectarse mediante un secret manager o variables seguras. Kafka, PostgreSQL y Redis también requieren configuración de seguridad, observabilidad, backups, TLS y políticas de alta disponibilidad según el ambiente.
