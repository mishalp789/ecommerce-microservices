# 🛒 E-Commerce Microservices Backend

A production-style e-commerce backend built using **Java 17**, **Spring Boot 3**, and **Microservices Architecture**. This project demonstrates service discovery, API Gateway, JWT authentication, inter-service communication, caching, messaging, fault tolerance, containerization, CI, and testing.

---

## 📌 Features

- User Registration & Login (JWT Authentication)
- Product Management (CRUD)
- Order Management
- Inventory Management
- API Gateway
- Service Discovery (Eureka)
- OpenFeign Communication
- Redis Caching
- RabbitMQ Event Messaging
- Circuit Breaker (Resilience4j)
- Swagger API Documentation
- Docker & Docker Compose
- Unit Testing (JUnit + Mockito)
- GitHub Actions CI

---

# 🏗️ Architecture

```text
                           Client
                              │
                              ▼
                      API Gateway (8080)
                              │
          ┌───────────────────┴───────────────────┐
          ▼                                       ▼
   Product Service                         Order Service
      (8081)                                 (8083)
          │                                       │
          │<────────── OpenFeign ────────────────┘
          │
          ▼
    PostgreSQL (product_db)

   Auth Service (8082)
          │
          ▼
   PostgreSQL (auth_db)

Order Service
      │
      ▼
RabbitMQ
      │
      ▼
Order Event Listener

Redis Cache
      ▲
      │
Product Service

             Eureka Server (8761)
                    ▲
                    │
      All Services Register Here
```

---

# 🛠️ Tech Stack

| Category | Technology |
|----------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Security | Spring Security, JWT |
| Database | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| API Docs | Swagger / OpenAPI |
| Service Discovery | Eureka Server |
| API Gateway | Spring Cloud Gateway |
| Communication | OpenFeign |
| Cache | Redis |
| Messaging | RabbitMQ |
| Fault Tolerance | Resilience4j |
| Testing | JUnit 5, Mockito |
| Build Tool | Maven |
| Containerization | Docker, Docker Compose |
| CI | GitHub Actions |

---

# 📂 Project Structure

```text
ecommerce-microservices
│
├── api-gateway
├── auth-service
├── product-service
├── order-service
├── eureka-server
│
├── docker-compose.yml
├── init.sql
└── README.md
```

---

# 🚀 Services

| Service | Port |
|----------|------|
| API Gateway | 8080 |
| Product Service | 8081 |
| Auth Service | 8082 |
| Order Service | 8083 |
| Eureka Server | 8761 |
| PostgreSQL | 5432 |
| Redis | 6379 |
| RabbitMQ | 5672 |
| RabbitMQ Dashboard | 15672 |

---

# 🔐 Authentication

### Register

```
POST /auth/register
```

### Login

```
POST /auth/login
```

Returns

```json
{
    "token":"JWT_TOKEN"
}
```

Use the token in requests

```
Authorization: Bearer <JWT_TOKEN>
```

---

# 📦 Product APIs

| Method | Endpoint |
|---------|----------|
| GET | /products |
| GET | /products/{id} |
| POST | /products |
| PUT | /products/{id} |
| DELETE | /products/{id} |
| PUT | /products/{id}/decrease-stock |

---

# 🛒 Order APIs

| Method | Endpoint |
|---------|----------|
| POST | /orders |
| GET | /orders |

---

# 📚 Swagger

After running the project:

```
http://localhost:8080/swagger-ui.html
```

or

```
http://localhost:8081/swagger-ui/index.html
```

---

# 🐳 Running with Docker

Clone repository

```bash
git clone <repository-url>
```

Go to project

```bash
cd ecommerce-microservices
```

Build

```bash
docker compose up --build
```

---

# 🧪 Running Tests

```bash
mvn test
```

---

# ⚡ CI Pipeline

GitHub Actions automatically

- Build all services
- Run Unit Tests
- Verify project compiles successfully

---

# 📈 Microservices Communication

```text
Client

↓

Gateway

↓

Order Service

↓

OpenFeign

↓

Product Service

↓

Redis

↓

PostgreSQL
```

---

# 📨 Event Driven Flow

```text
Create Order

↓

Save Order

↓

Publish Event

↓

RabbitMQ

↓

Order Event Listener

↓

Log Event
```

---

# 💾 Cache Flow

```text
GET Product

↓

Redis

↓

Cache Hit?

Yes
↓

Return Product

No
↓

PostgreSQL

↓

Save to Redis

↓

Return Product
```

---

# 🛡️ Fault Tolerance

If Product Service is unavailable:

```
Order Service

↓

Circuit Breaker

↓

Fallback Method

↓

503 Service Unavailable
```

---

# 🧪 Unit Testing

Implemented using

- JUnit 5
- Mockito

Tested

- Product Service
- Auth Service
- Order Service

Including

- Success cases
- Exception cases
- RabbitMQ event publishing
- Redis integration logic
- Service communication

---

# ✨ Future Improvements

- Kubernetes Deployment
- Centralized Config Server
- ELK Stack Logging
- Prometheus & Grafana Monitoring
- Notification Service
- Payment Service
- Email Service

---

# 👨‍💻 Author

**Mishal**

Java Backend Developer

- Java
- Spring Boot
- Microservices
- PostgreSQL
- Docker
- Redis
- RabbitMQ