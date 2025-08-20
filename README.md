# 🏨 Hotel Management System - Microservices

This project is a **Microservices-based Hotel Management System**, designed with **Spring Boot, Spring Cloud, and Eureka Server**.  
Each service runs independently and communicates via REST APIs and Feign Clients.  
The system demonstrates service-to-service communication, fault tolerance, and distributed architecture.

---

## ✨ Features

- **Microservices Architecture** → Each service runs independently on different branches.  
- **Service Communication** → RestTemplate and FeignClient used for inter-service communication.  
- **Resilience & Fault Tolerance** → Implemented Resilience4j (Circuit Breaker, Retry, Fallback).  
  - Provides dummy data when a service is unavailable.  
- **Databases** → SQL (MySQL/PostgreSQL) + NoSQL (MongoDB) across different services.  
- **Exception Handling** →  
  - Custom Exceptions per service.  
  - Global Exception Handler for consistent responses.  
- **Service Discovery** → Eureka Server for service registration and discovery.  
- **Spring Cloud** → For configuration and microservice management.  
- **Externalised Configuration** →
  - Centralised Spring Cloud Config Server to externalise configuration.
  - All environment-specific properties (dev, test, prod) are stored in Git/Config Repo.
  - Enables dynamic refresh (@RefreshScope) without restarting services.

---

## 🛠️ Tech Stack

- **Backend:** Java 8, Spring Boot, Spring Cloud, Spring Data JPA  
- **Service Discovery:** Eureka Server  
- **Resilience:** Resilience4j  
- **Databases:** MySQL / PostgreSQL, MongoDB  
- **Build Tool:** Maven  
- **Communication:** RestTemplate, OpenFeign  

---

## 📂 Microservices Overview

### 1. User Service  
- Manages user details.  
- Communicates with Hotel Service & Rating Service.  

### 2. Hotel Service  
- Handles hotel details.  
- Stores hotel data in SQL/NoSQL.  

### 3. Rating Service  
- Manages user ratings for hotels.  
- Exposes ratings via REST API.  

### 4. Eureka Server  
- Service Registry for all microservices.  

---

## 🔄 Flow

1. A request is sent to **User Service**.  
2. User Service fetches ratings from **Rating Service**.  
3. For each rating, it fetches hotel details from **Hotel Service**.  
4. **Resilience4j** ensures fallback responses if services are down.  

---

## 🚀 How to Run

1. Clone the repository.  
2. Start **Eureka Server**.  
3. Run all microservices (User, Hotel, Rating).  
4. Test APIs using Postman / Browser.  

---
