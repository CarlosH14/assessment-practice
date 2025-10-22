# Assessment Practice - Study Endpoints

This repository provides a Spring Boot application with study endpoints, transactional demos, and examples for various software engineering assessment topics.

## Purpose

This project serves as a practical reference and study guide for:
- GRASP (General Responsibility Assignment Software Patterns) principles
- Java Collections Framework (Lists, Sets, Maps with thread-safe variants)
- Exception Handling best practices
- Database operations (Stored Procedures, Transactions, Optimization)
- Concurrency patterns (ExecutorService, @Async, CompletableFuture)
- Caching strategies (LRU cache implementation)
- Cloud services integration (AWS S3 guide)
- **Transactional Stock Management** - Demonstrates `@Transactional` behavior with rollback on exceptions
- **Product Management** - CRUD operations for products with stock tracking

## Project Structure

```
assessment-practice/
├── src/
│   ├── main/
│   │   ├── java/com/assessmentpractice/
│   │   │   ├── AssessmentPracticeApplication.java  # Main Spring Boot application
│   │   │   ├── StudyController.java                # REST endpoints
│   │   │   ├── StudyDocs.java                      # Study documentation
│   │   │   ├── entity/
│   │   │   │   ├── Product.java                    # JPA entity for products
│   │   │   │   └── StockLog.java                   # JPA entity for stock logs
│   │   │   ├── repository/
│   │   │   │   ├── ProductRepository.java
│   │   │   │   └── StockLogRepository.java
│   │   │   ├── service/
│   │   │   │   ├── CollectionService.java          # Collection examples
│   │   │   │   ├── CacheService.java               # LRU cache implementation
│   │   │   │   ├── ConcurrencyService.java         # Concurrency demos
│   │   │   │   └── StockService.java               # Transactional stock operations
│   │   │   └── exception/
│   │   │       └── InsufficientStockException.java
│   │   └── resources/
│   │       ├── application.properties              # Configuration (H2 default)
│   │       ├── application-postgres.properties     # PostgreSQL config
│   │       └── db/procs.sql                        # PostgreSQL examples
│   └── test/
│       └── java/com/assessmentpractice/
│           └── StockServiceIntegrationTest.java   # Integration tests
├── docker/
│   └── Dockerfile                                   # Container image
├── k8s/
│   └── deployment.yaml                             # Kubernetes manifests
├── .github/
│   └── workflows/
│       └── ci.yml                                  # GitHub Actions CI/CD
├── docker-compose.yml                              # Docker Compose setup
├── pom.xml                                         # Maven configuration
└── README.md
```

## Available Endpoints

Once the application is running, the following endpoints are available:

### Study Resources

- **GET /study/grasp** - GRASP design principles summary
- **GET /study/collections** - Java Collections Framework examples
- **GET /study/exceptions** - Exception handling best practices
- **GET /study/db/procs** - Stored procedures and SQL examples
- **GET /study/db/transaction-demo** - Transaction management guide
- **GET /study/concurrency/executor** - ExecutorService demonstration
- **GET /study/concurrency/exec/async** - @Async annotation example
- **GET /study/cloud/aws-s3-demo** - AWS S3 integration guide

### Interactive Endpoints

- **POST /study/cache/put?key={key}&value={value}** - Add entry to cache
- **GET /study/cache/get?key={key}** - Retrieve cached value

### Health & Monitoring

- **GET /actuator/health** - Application health status
- **GET /actuator/info** - Application information
- **GET /actuator/metrics** - Application metrics

## How to Run Locally

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- (Optional) Docker and Docker Compose for containerized deployment

### Build and Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/CarlosH14/assessment-practice.git
   cd assessment-practice
   ```

2. **Build the project**
   ```bash
   mvn clean package
   ```

3. **Run the application**
   ```bash
   java -jar target/assessment-practice-1.0.0.jar
   # Or using Maven
   mvn spring-boot:run
   ```

4. **Access the application**
   - Application: http://localhost:8080
   - H2 Console: http://localhost:8080/h2-console
   - Health Check: http://localhost:8080/actuator/health

### Skip Tests (if needed)

```bash
mvn clean package -DskipTests
```

## Running with Docker Compose (PostgreSQL + Redis)

```bash
docker-compose up --build
```

This will start:
- **app** - Spring Boot application (port 8080)
- **postgres** - PostgreSQL database (port 5432)
- **redis** - Redis cache (port 6379)

**Environment Variables:**
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
# Remove volumes as well
docker-compose down -v
```

## Docker Deployment

1. **Build the Docker image**
   ```bash
   mvn clean package
   docker build -f docker/Dockerfile -t assessment-practice:1.0.0 .
   ```

2. **Run the container**
   ```bash
   docker run -p 8080:8080 assessment-practice:1.0.0
   ```

## Kubernetes Deployment

```bash
kubectl apply -f k8s/deployment.yaml
```

This creates:
- Deployment with 2 replicas
- LoadBalancer service
- Health probes (liveness and readiness)

## Study Suggestions

### 1. GRASP Principles
- Review the `/study/grasp` endpoint to understand design principles
- Apply these principles when reviewing or writing code
- Focus on Information Expert, Low Coupling, and High Cohesion

### 2. Collections Framework
- Explore `/study/collections` to see different collection types
- Understand when to use ArrayList vs LinkedList
- Learn thread-safe collections (CopyOnWriteArrayList, ConcurrentHashMap)

### 3. Exception Handling
- Review best practices at `/study/exceptions`
- Practice writing custom exceptions
- Implement proper try-with-resources patterns

### 4. Database Operations
- Study the stored procedures in `src/main/resources/db/procs.sql`
- Practice writing transactional methods with `@Transactional`
- Review optimization techniques from `/study/db/procs`
- **Test transactional behavior**: Run `mvn test` to see `StockService` rollback demos

### 5. Concurrency
- Experiment with `/study/concurrency/executor` endpoint
- Understand the difference between ExecutorService and @Async
- Study the deadlock example in `ConcurrencyService` (DO NOT execute it!)

### 6. Caching
- Use the `/study/cache/put` and `/study/cache/get` endpoints
- Review the LRU cache implementation in `CacheService`
- Consider when caching is appropriate in your applications

### 7. Cloud Integration
- Review the AWS S3 guide at `/study/cloud/aws-s3-demo`
- Practice integrating with cloud services
- Understand authentication and authorization patterns

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

## Testing the Application

### Using curl

```bash
# Get GRASP principles
curl http://localhost:8080/study/grasp

# Get collections examples
curl http://localhost:8080/study/collections

# Cache operations
curl -X POST "http://localhost:8080/study/cache/put?key=mykey&value=myvalue"
curl "http://localhost:8080/study/cache/get?key=mykey"

# Async concurrency demo
curl http://localhost:8080/study/concurrency/exec/async
```

### Using Browser

Simply navigate to the endpoints in your web browser:
- http://localhost:8080/study/grasp
- http://localhost:8080/study/collections
- http://localhost:8080/study/exceptions

## Next Steps

### Immediate Actions
1. **Run locally**: `mvn spring-boot:run` or `docker-compose up --build`
2. **Verify tests pass**: `mvn test`
3. **Try the study endpoints**: Visit http://localhost:8080/study/grasp

### Optional Enhancements

1. **Add PostgreSQL Integration**
   - Set up docker-compose with PostgreSQL (already configured)
   - Update application.properties to use PostgreSQL
   - Test stored procedures from `procs.sql`

2. **Add Redis Caching**
   - Integrate Spring Cache with Redis (docker-compose ready)
   - Compare with the custom LRU cache implementation
   - Test distributed caching scenarios

3. **Enable Real Transaction Demos**
   - Create entities and repositories (already implemented)
   - Implement @Transactional service methods (StockService ready)
   - Test isolation levels and propagation behaviors

4. **Add REST Controllers**
   - Expose stock management via REST API
   - Add API documentation with Swagger/OpenAPI

5. **Add Monitoring**
   - Integrate Prometheus and Grafana
   - Set up custom metrics
   - Monitor application performance

6. **Extend Study Topics**
   - Add microservices patterns
   - Include API security examples (OAuth2, JWT)
   - Add messaging patterns (Kafka, RabbitMQ)

## CI/CD

GitHub Actions workflow (`.github/workflows/ci.yml`) automatically:
- Builds the project with `mvn -DskipTests package`
- Runs all tests with `mvn test`
- Uploads test results as artifacts

## Technologies Used

- **Spring Boot 3.1.5** - Application framework
- **Java 17** - Programming language
- **Maven** - Build tool
- **H2 Database** - In-memory database for demos
- **PostgreSQL** - Production database (configuration ready)
- **Docker** - Containerization
- **Kubernetes** - Orchestration

## Contributing

This is a study/practice repository. Feel free to:
- Add more examples
- Improve documentation
- Fix bugs or issues
- Suggest new topics

## License

This project is for educational purposes.

## FastAPI Project

This repository also contains a FastAPI project in the `fastapi-project/` directory for Python-based assessment practice.
