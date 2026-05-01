#  Rate Limiter + API Gateway

##  Project Overview

This project is a **Spring Boot API Gateway** that provides:

*  JWT-based Authentication
*  Rate Limiting (Token Bucket Algorithm)
*  API Request Logging & Monitoring
*  MongoDB Atlas Integration
*  Deployed on Render

---

##  Tech Stack

* **Backend:** Spring Boot (Java 17)
* **Database:** MongoDB Atlas
* **Security:** Spring Security + JWT
* **Build Tool:** Maven
* **Deployment:** Render

---

##  Features

### 1️⃣ Authentication

* User Registration
* User Login
* JWT Token generation
* Secured endpoints using Bearer Token

---

### 2️⃣ API Gateway Routing

* Routes requests to internal services
* Example:

  ```
  /api/gateway/service-a
  ```

---

### 3️⃣ Rate Limiting

* Token Bucket Algorithm
* Configurable:

  * max tokens
  * refill rate
* Returns:

  ```
  429 Too Many Requests
  ```

---

### 4️⃣ Logging & Monitoring

* Stores API logs in MongoDB
* Tracks:

  * userEmail
  * endpoint
  * method
  * response status
  * response time
  * rate limit violations

---

##  API Endpoints

###  Auth APIs

| Method | Endpoint           | Description     |
| ------ | ------------------ | --------------- |
| POST   | /api/auth/register | Register user   |
| POST   | /api/auth/login    | Login & get JWT |

---

###  Gateway API

| Method | Endpoint               | Description           |
| ------ | ---------------------- | --------------------- |
| GET    | /api/gateway/service-a | Sample routed service |

---

###  Monitoring APIs

| Method | Endpoint                     | Description           |
| ------ | ---------------------------- | --------------------- |
| GET    | /api/monitor/logs/recent     | Recent logs           |
| GET    | /api/monitor/logs/user       | Logs by user email    |
| GET    | /api/monitor/logs/violations | Rate limit violations |

---

##  Configuration

### application.properties

```properties
server.port=${PORT:8080}

spring.data.mongodb.uri=${MONGO_URI}

gateway.auth.jwt-secret=${JWT_SECRET}
gateway.auth.jwt-expiration-seconds=3600

gateway.rate-limit.max-tokens=100
gateway.rate-limit.refill-rate=10
```

---

## Setup MongoDB Atlas

Go to MongoDB Atlas website
Create a free cluster
Create a database user (username and password)
Go to Network Access and allow IP address (0.0.0.0/0 for testing)
Click Connect → Drivers → copy connection string
Replace username and password in connection string

Example:
mongodb+srv://username@cluster.mongodb.net/apigateway

##  Environment Variables

Set in Render:

* `MONGO_URI`= your_mongodb_connection_string
* `JWT_SECRET`= your_secret_key
* `PORT`= 8080

---

## Setup Instructions

1. Clone the Repository
   git clone https://github.com/your-username/api-gateway.git
   cd api-gateway
2. Configure Environment Variables
   Set environment variables in your system or IDE
3. Run the Application
   mvn spring-boot
4. Access API
   http://localhost:8080

##  Deployment

###  Backend URL

https://your-deployment.onrender.com

###  Swagger UI

https://your-deployment.com/swagger-ui/index.html

##  MongoDB Collections

* `api_logs` → stores request logs
* `rate_limit_entries` → stores rate limiting data

---

## Project Structure

apigateway/
│
├── config/                                          # SecurityConfig, SwaggerConfig, GatewayProperties
├── filter/                                          # JwtAuthFilter, RateLimitFilter, LoggingFilter
├── security/                                        # JwtUtil
├── dto/                                             # AuthResponse, ApiResponse, LoginRequest, RegisterRequest
├── exception/                                       # CustomException, GlobalExceptionHandler
├── controller/                                      # AuthController, GatewayController, MonitoringController
├── service/                                         # AuthService, GatewayRoutingService, RateLimitService, LoggingService
├── repo/                                            # UserRepository, RateLimitRepository, ApiLogRepository
├── model/                                           # User, RateLimitEntry, ApiLog

