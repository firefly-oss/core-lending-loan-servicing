# Core Lending Loan Servicing Microservice

## Overview

The Core Lending Loan Servicing Microservice is a critical component of the Firefly platform, responsible for managing loan servicing operations. This service handles various aspects of loan management after disbursement, including repayment tracking, loan servicing case management, rate changes, and other loan servicing events.

## Architecture

The microservice follows a modular architecture with clear separation of concerns:

### Components

- **core-lending-loan-servicing-interfaces**: Contains DTOs, interfaces, enums, and API definitions
- **core-lending-loan-servicing-models**: Contains data models, entities, and repositories for database access
- **core-lending-loan-servicing-core**: Contains business logic, service implementations, and mappers
- **core-lending-loan-servicing-web**: Contains web controllers, REST endpoints, and application configuration

### Key Features

- Loan servicing case management
- Loan repayment tracking and scheduling
- Loan disbursement processing
- Loan rate change management
- Loan servicing event tracking

## Technologies

- **Java 21**: Core programming language
- **Spring Boot**: Application framework
- **Spring WebFlux**: Reactive web framework
- **R2DBC**: Reactive Relational Database Connectivity for database access
- **Maven**: Dependency management and build tool
- **Docker**: Containerization
- **OpenAPI/Swagger**: API documentation
- **Reactor**: Reactive programming library

## Prerequisites

- Java Development Kit (JDK) 21
- Maven 3.8+
- Docker (for containerized deployment)
- Access to the Firefly platform infrastructure

## Setup and Installation

### Local Development

1. Clone the repository:
   ```bash
   git clone https://github.com/your-organization/firefly-oss.git
   cd firefly-oss/core-lending-loan-servicing
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run -pl core-lending-loan-servicing-web
   ```

4. The application will be available at http://localhost:8080

### Docker Deployment

1. Build the Docker image:
   ```bash
   mvn clean package
   docker build -t core-lending-loan-servicing:latest .
   ```

2. Run the Docker container:
   ```bash
   docker run -p 8080:8080 core-lending-loan-servicing:latest
   ```

## API Documentation

The API documentation is available through Swagger UI when the application is running:

- Local: http://localhost:8080/swagger-ui.html
- Development: http://core.catalis.vc/loan-servicing/swagger-ui.html

## Development Guidelines

### Code Structure

- Follow the modular architecture pattern
- Keep business logic in the core module
- Define interfaces in the interfaces module
- Keep database entities in the models module
- Implement REST controllers in the web module

### Coding Standards

- Follow Java coding conventions
- Use reactive programming patterns with Reactor
- Write unit tests for all business logic
- Document public APIs with OpenAPI annotations
- Use DTOs for data transfer between layers

## Testing

### Unit Tests

Run unit tests with:
```bash
mvn test
```

### Integration Tests

Run integration tests with:
```bash
mvn verify
```

## Deployment

### Development Environment

The service is deployed at http://core.catalis.vc/loan-servicing in the development environment.

### Production Deployment

For production deployment, follow the Firefly platform deployment guidelines.

## Contributing

1. Create a feature branch from the main branch
2. Implement your changes
3. Write tests for your changes
4. Ensure all tests pass
5. Submit a pull request