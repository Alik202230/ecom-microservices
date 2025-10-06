```markdown
# ecom-microservices

A modular e-commerce microservices system built with Java 21, Jakarta EE imports, Spring Boot, Spring Data JPA, and Lombok. The system demonstrates service decomposition, inter-service communication, centralized configuration, service discovery, API gateway routing, asynchronous messaging, and containerized local development.

## Modules

- config-server — Centralized configuration for all services.
- eureka — Service discovery (Eureka Server).
- gateway — API Gateway routing and edge concerns.
- order-service — Order domain, persistence, and event production.
- product-service — Product catalog and inventory.
- user-service — User management and profiles.
- notification-service — Consumes domain events (e.g., order events) and sends notifications.

## Tech Stack

- Language: Java 21
- Frameworks: Spring Boot, Spring Data JPA, Jakarta EE imports
- Messaging: Apache Kafka
- Service Discovery: Netflix Eureka
- Config: Spring Cloud Config Server
- API Gateway: Spring Cloud Gateway
- Build: Maven
- Lombok for boilerplate reduction
- Docker Compose for local infra

## Prerequisites

- JDK 21
- Maven 3.9+
- Docker and Docker Compose
- IDE: IntelliJ IDEA (recommended)

## Quick Start

1) Start infrastructure (Kafka, Zookeeper, DBs if defined, etc.):
- docker-compose up -d

2) Start platform services (order recommended):
- Start config-server
- Start eureka
- Start gateway

3) Start domain services:
- Start product-service
- Start user-service
- Start order-service
- Start notification-service

Tip: Respect this order so services can fetch config, register with discovery, and be routable via the gateway.

## Running Services

Each service is a Spring Boot app with its own pom.xml. From each module directory:

- Unix/macOS: ./mvnw spring-boot:run
- Windows: mvnw.cmd spring-boot:run

Or use IntelliJ Run Configurations per module.

## Service Endpoints (via Gateway)

- Gateway base URL: http://localhost:<gateway-port>/
- Services are typically routed by prefix, for example:
  - /orders/** -> order-service
  - /products/** -> product-service
  - /users/** -> user-service
  - /notifications/** -> notification-service

Actual routes depend on your gateway configuration.

## Configuration

- Externalized via config-server. Each service fetches on startup using its spring.cloud.config.* settings.
- Set environment-specific properties in the config repository used by config-server.

Common env vars to consider:
- SPRING_PROFILES_ACTIVE
- SPRING_CLOUD_CONFIG_URI
- EUREKA_CLIENT_SERVICEURL_DEFAULTZONE
- KAFKA_BOOTSTRAP_SERVERS
- DB connection variables per service

## Kafka

- order-service produces domain events (e.g., order lifecycle).
- notification-service consumes events using a ConcurrentKafkaListenerContainerFactory with configured concurrency to scale consumers.
- Ensure Kafka bootstrap servers in application configuration point to the docker-compose Kafka.

Local topics:
- Create topics via docker-compose init scripts or let apps auto-create (if enabled). For production, pre-create topics with proper partitions/replication.

## Databases

- Each service can own its schema. Configure JDBC URLs, usernames, and passwords via config-server or environment variables.

## Building

From the root:

- mvn clean package

Artifacts for each service will be created in their respective target directories.

## Testing

- mvn test
- Add integration tests using Testcontainers for Kafka/DB if desired.

## Observability

- Add Actuator to services for health, info, and metric endpoints.
- Register with Eureka for service status visibility.
- Consider centralized logs and tracing (e.g., OpenTelemetry) for production.

## Local Development Tips

- Start config-server first; if services boot before it’s ready, they may fail to load configuration.
- Verify Eureka is available so services can register successfully.
- When changing Kafka topics or partitions, restart consumers to pick up changes.
- Use Docker Compose logs to troubleshoot infra:
  - docker-compose logs -f kafka
  - docker-compose logs -f zookeeper

## Common Environment Variables (examples)

Replace placeholders with actual values.

- SPRING_PROFILES_ACTIVE=local
- SPRING_CLOUD_CONFIG_URI=http://localhost:8888
- EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://localhost:8761/eureka
- KAFKA_BOOTSTRAP_SERVERS=localhost:9092
- SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/<db_name>
- SPRING_DATASOURCE_USERNAME=<db_user>
- SPRING_DATASOURCE_PASSWORD=<db_password>

## Docker Compose

- Provides Kafka, Zookeeper, and other infra needed locally.
- Bring everything down with:
  - docker-compose down -v

## Project Structure (high level)

- config-server/
- eureka/
- gateway/
- order-service/
- product-service/
- user-service/
- notification-service/
- docker-compose.yml

## Contributing

- Use feature branches.
- Write tests for new features.
- Run mvn clean verify before PRs.
- Keep configuration externalized; do not commit secrets.

```
