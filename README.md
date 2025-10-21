# assessment-practice
Repo para POC de temas de assessment

## Overview
This project demonstrates a Spring Boot application with transactional database operations, JPA entities, and integration testing.

## Features
- **Spring Boot** application with JPA/Hibernate
- **Transactional Stock Management** - Demonstrates `@Transactional` behavior with rollback on exceptions
- **Product Management** - CRUD operations for products with stock tracking
- **Stock Logging** - Audit trail for all stock changes
- **Exception Handling** - Custom exceptions for insufficient stock scenarios
- **H2 Database** for testing
- **PostgreSQL** support for production
- **Redis** integration (available via docker-compose)
- **Docker Compose** setup for local development

## Project Structure
```
src/
├── main/
│   ├── java/com/assessmentpractice/
│   │   ├── AssessmentPracticeApplication.java
│   │   ├── entity/
│   │   │   ├── Product.java          # JPA entity for products
│   │   │   └── StockLog.java         # JPA entity for stock logs
│   │   ├── repository/
│   │   │   ├── ProductRepository.java
│   │   │   └── StockLogRepository.java
│   │   ├── service/
│   │   │   └── StockService.java     # Transactional stock operations
│   │   └── exception/
│   │       └── InsufficientStockException.java
│   └── resources/
│       ├── application.properties              # H2 config (default)
│       └── application-postgres.properties     # PostgreSQL config
└── test/
    └── java/com/assessmentpractice/
        └── StockServiceIntegrationTest.java   # Integration tests
```

## Prerequisites
- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose (for containerized deployment)

## Building the Project

### Build without running tests
```bash
mvn -DskipTests package
```

### Build and run tests
```bash
mvn clean package
```

### Run tests only
```bash
mvn test
```

## Running the Application

### Option 1: Run locally with H2 (in-memory database)
```bash
mvn spring-boot:run
```
The application will start on `http://localhost:8080` with H2 database.

### Option 2: Run with Docker Compose (PostgreSQL + Redis)
```bash
docker-compose up --build
```

This will start:
- **app** - Spring Boot application (port 8080)
- **postgres** - PostgreSQL database (port 5432)
- **redis** - Redis cache (port 6379)

**Environment Variables for Docker Compose:**
The docker-compose setup uses the following environment variables:
- `SPRING_PROFILES_ACTIVE=postgres` - Activates PostgreSQL profile
- `DB_HOST=postgres` - Database host
- `DB_PORT=5432` - Database port
- `DB_NAME=assessment` - Database name
- `DB_USER=assessment` - Database user
- `DB_PASSWORD=assessment` - Database password

**Note:** These are example credentials for local development only. Do not use in production.

### Stopping Docker Compose
```bash
docker-compose down
```

To remove volumes as well:
```bash
docker-compose down -v
```

## Transactional Demo

The `StockService.decreaseStock()` method demonstrates Spring's `@Transactional` behavior:

1. **Success Case**: When stock is sufficient
   - Product quantity is decreased
   - StockLog entry is created
   - Both changes are committed together

2. **Failure Case**: When stock is insufficient
   - `InsufficientStockException` is thrown
   - Transaction is rolled back
   - No changes are persisted (neither product update nor stock log)

### Testing the Transactional Behavior

The integration tests in `StockServiceIntegrationTest` verify:
- ✅ Successful stock decrease with logging
- ✅ Transaction rollback on insufficient stock
- ✅ Transaction rollback when product not found
- ✅ Multiple stock decreases work correctly

Run the tests:
```bash
mvn test
```

## Database Configuration

### H2 (Default - for testing)
The default profile uses H2 in-memory database. Configuration in `application.properties`:
- URL: `jdbc:h2:mem:testdb`
- Console: `http://localhost:8080/h2-console`

### PostgreSQL (Production)
Activate with `--spring.profiles.active=postgres` or set `SPRING_PROFILES_ACTIVE=postgres` environment variable.

Configuration in `application-postgres.properties` uses environment variables:
- `DB_HOST` - default: localhost
- `DB_PORT` - default: 5432
- `DB_NAME` - default: assessment
- `DB_USER` - default: assessment
- `DB_PASSWORD` - default: assessment

## API Endpoints
(To be implemented - currently the application provides the service layer and persistence)

## Next Steps
1. **Run locally**: `mvn spring-boot:run` or `docker-compose up --build`
2. **Verify tests pass**: `mvn test`
3. **Add REST controllers**: Expose stock management via REST API
4. **Implement Redis caching**: Add caching for product lookups
5. **Add API documentation**: Integrate Swagger/OpenAPI
6. **Production deployment**: Configure production-grade database and secrets management
7. **Monitoring**: Add actuator endpoints and logging

## CI/CD
GitHub Actions workflow (`.github/workflows/ci.yml`) automatically:
- Builds the project with `mvn -DskipTests package`
- Runs all tests with `mvn test`
- Uploads test results as artifacts

## License
This is a practice/POC project for assessment demonstrations.
