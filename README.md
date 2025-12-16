# 📋 Task Management Microservices
![Java 17](https://img.shields.io/badge/Java%2017-%23ED8B00.svg?style=flat&logo=openjdk&logoColor=white)
![Spring Boot 4](https://img.shields.io/badge/spring%20boot-%236DB33F.svg?style=flat&logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/spring%20cloud-%236DB33F.svg?style=flat&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat&logo=springsecurity&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=flat&logo=postgresql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=flat&logo=hibernate&logoColor=white)
![Keycloak](https://img.shields.io/badge/Keycloak-ADD8E6?style=flat&logo=keycloak&logoColor=black)
![JWT](https://img.shields.io/badge/JWT-black?style=flat&logo=JSON%20web%20tokens)
![OAuth2](https://img.shields.io/badge/oauth%202.0-eb5424?style=flat&logo=oauth&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=flat&logo=docker&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=flat&logo=apachemaven&logoColor=white)
![Lombok](https://img.shields.io/badge/Project%20Lombok-bc0230?style=flat&logo=lombok&logoColor=white)


## 📖 Introduction

The **Task Management System** is a comprehensive solution built on a **Microservices architecture**, designed to deliver high performance and strong scalability.
The project leverages modern **cloud-native technologies** to ensure reliability, fault tolerance, and efficient request processing.

## 🏗️ Architecture & Tech Stack

The system is designed as a collection of independent services that communicate with each other via **REST APIs** and **Message Queues**.

| Component               | Technology                      | Description                                                                                                                                                                                      |
|:------------------------|:--------------------------------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Backend Services**    | **Java 17 + Spring Boot 4.0.0** | Uses a stable **Java LTS** version with the latest **Spring Boot 4.0.0** framework. **Lombok** is integrated to reduce boilerplate code and accelerate development speed.                        |
| **API Gateway**         | **Spring Cloud Gateway**        | Acts as the single entry point to the system, built on **WebFlux** (Non-blocking I/O) to handle high request throughput and perform intelligent routing to microservices.                        |
| **Service Discovery**   | **Netflix Eureka**              | Service registry that enables microservices (`user`, `task`, `notification`, `gateway`) to automatically discover and communicate with each other without hard-coded IP configurations.          |
| **Identity & Security** | **Keycloak + OAuth2 / JWT**     | Centralized identity management (**Identity Provider**). Uses **OAuth2 Resource Server** to secure APIs, handling authentication and authorization via **JWT**.                                  |
| **Database**            | **PostgreSQL**                  | A robust relational database deployed as a container (`postgres:15-alpine`). **Spring Data JPA** is used for safe and efficient data access.                                                     |
| **Containerization**    | **Docker + Docker Compose**     | Packages the entire application and infrastructure into isolated containers. Uses `docker-compose` to define and launch the whole system (network `microservice-network`) with a single command. |
| **Build Tool**          | **Maven**                       | Manages project dependencies and build lifecycle. The **multi-module** structure simplifies management of individual services.                                                                   |

## 🧩 Architecture Overview

```
    Client[Client (Web/Mobile)] -->|HTTPS| Traefik[Traefik Gateway]
    
    subgraph K8s_Cluster [Kubernetes Cluster]
        %% Routing từ Gateway vào các Service cụ thể
        Traefik -->|Route /auth, /users| UserService[user-service]
        Traefik -->|Route /tasks| TaskService[task-service]
        Traefik -->|Route /notif| NotifService[notification-service]
        
        %% Kết nối Database
        UserService -->|Read/Write| DB_User[(Postgres User DB)]
        TaskService -->|Read/Write| DB_Task[(Postgres Task DB)]
        
        %% Giao tiếp bất đồng bộ qua Kafka
        TaskService -.->|Publish Event| Kafka{Apache Kafka}
        Kafka -.->|Consume Event| NotifService
        
        %% Caching
        TaskService -->|Cache| Redis[(Redis)]
    
```

## 🛠 Installation

## 1. Installing
### Docker, Postgrel, Setup uv

    Cài đặt dependencies và khóa phiên bản:
  ```bash
  uv pip install --system --no-cache -r requirements_Tracking.txt
  uv lock
  ````

## 2. Running
  ```bash
    docker-compose up -d --build
  ```
## 4. Stopping
  ```bash
    docker-compose down # hoặc là Crl + C cho nhanh rồi xóa Image
  ```

## 💬 Khắc Phục Lỗi (Troubleshooting)

### 🔴 Lỗi 1: Bind for 0.0.0.0:80 failed
  ```
    Nguyên nhân: Cổng 80 thường bị chiếm bởi Windows System (IIS) hoặc Skype.

    Giải pháp: Dự án này đã được cấu hình chuyển sang cổng 8080. Hãy truy cập localhost:8080. 
  ```
### 🔴 Lỗi 2: OS Error 5 / Access Denied
  ```
      Nguyên nhân: Windows khóa quyền truy cập thư mục .venv khi map volume từ máy thật vào Docker.

      Giải pháp: Xóa thư mục ảo và build lại
      docker-compose down
      # Xóa thủ công thư mục task_management/account-service/.venv
      docker-compose up -d --build
  ```
### 🔴 Lỗi 3: Frontend bị lỗi CORS
  ```
    Giải pháp: Kiểm tra file app/main.py, đảm bảo URL của Frontend (ví dụ http://localhost:3000) đã được thêm vào biến allow_origins.
  ```

## 🧰 Project Structure

```
    microservices_task_management/
    ├── api-gateway/           # 🛡️ API GATEWAY (Entry Point)
    │                          # - Routes incoming requests to the appropriate services.
    │                          # - Performs initial token validation (if Gateway Filters are configured).
    │
    ├── eureka-server/         # 🧭 SERVICE DISCOVERY
    │                          # - Acts as the system's service registry.
    │                          # - Microservices self-register and discover each other dynamically.
    │
    ├── user-service/          # 👤 USER SERVICE
    │                          # - Resource Server secured by Keycloak.
    │                          # - Handles User CRUD operations and synchronizes user data with Keycloak.
    │
    ├── task-service/          # 📋 TASK SERVICE (Core Business Logic)
    │                          # - Manages Boards, Columns, Cards, and Labels.
    │                          # - Contains the core business logic of the Project Management system.
    │
    ├── notification-service/  # 🔔 NOTIFICATION SERVICE
    │                          # - Listens to events and sends emails/notifications.
    │
    ├── docker/                # 🐳 INFRASTRUCTURE CONFIGURATION
    │   └── postgres/          # - Database initialization scripts (init.sql).
    │
    └── docker-compose.yml     # 🚀 ORCHESTRATION
                               # - The central file that orchestrates and manages all containers.

```

```
    user-service/ (src/main/java/...)
    ├── config/                     # ⚙️ CONFIGURATION LAYER
    │   ├── SecurityConfig.java     # - Defines the security boundary.
    │   │                           # - Configures OAuth2 Resource Server and Security Filter Chain.
    │   └── KeycloakConfig.java     # - Configures the client connection to the Keycloak Admin API.
    │
    ├── controller/                 # 🌐 CONTROLLER / INTERFACE LAYER
    │   └── UserController.java     # - Endpoints: POST /users, GET /me.
    │                               # - Responsibility: Receive requests → Call Service → Return DTOs.
    │                               # - Contains no business logic, only request routing.
    │
    ├── service/                    # 🧠 BUSINESS LOGIC LAYER
    │   ├── UserService.java        # - Interface defining business behaviors.
    │   └── impl/                   # - Implementation of business logic.
    │       └── UserServiceImpl.java# - Core logic implementation.
    │                               # - Fetches data via Repository → Processes logic → Maps to DTOs.
    │                               # - Manages transactions using @Transactional.
    │
    ├── repository/                 # 🔌 DATA ACCESS LAYER (DAO)
    │   └── UserRepository.java     # - Direct interaction with PostgreSQL via JPA.
    │                               # - Contains only query methods (findByEmail, existsBy...).
    │
    ├── model/ (or entity)          # 🗄️ DATA MODEL / ENTITY LAYER
    │   └── User.java               # - One-to-one mapping with the `users` database table.
    │                               # - Defines columns, primary keys, and ORM relationships.
    │
    ├── dto/                        # 📝 DATA TRANSFER OBJECTS
    │   ├── UserDTO.java            # - Data returned to the client (hides sensitive fields).
    │   ├── CreateUserForm.java     # - Input data from registration forms (validated with @NotNull, @Email).
    │   └── TokenResponse.java      # - Token response payload format.
    │
    ├── mapper/                     # 🔄 MAPPING / CONVERTER LAYER
    │   └── UserMapper.java         # - Translates between Entity (DB) and DTO (API).
    │
    ├── exception/                  # 🛡️ GLOBAL EXCEPTION HANDLING
    │   └── GlobalExceptionHandler.java
    │                               # - Centralized error handling for the entire application.
    │                               # - Captures exceptions (NotFound, BadRequest) and returns standardized JSON responses.
    │
    └── UserServiceApplication.java # 🟢 APPLICATION ENTRY POINT
                                    # - Bootstraps the Spring context.
                                    # - Enables Eureka Client registration.
```

## 🌐 Port Mapping

The system uses **Spring Cloud Gateway** as the primary gateway for routing incoming requests.

| Service                      | URL / Host                                               | Mô tả                                        |
|------------------------------|----------------------------------------------------------|----------------------------------------------|
| API Gateway                  | [http://localhost:8080](http://localhost:8080)           | Cổng chính (Frontend gọi vào đây)            |
| Eureka Dash                  | [http://localhost:8081](http://localhost:8081)           | Dashboard quản lý Gateway                    |
| User Service                 | [http://localhost:8080/user](http://localhost:8080/user) | Truy cập User service qua Gateway            |
| Task Service                 | [http://localhost:8080/task](http://localhost:8080/task) | Truy cập User service qua Gateway            |
| Dev Direct API (User, Task,) | [http://localhost:8081, 8082]()                          | Truy cập trực tiếp container (chỉ dev/debug) |

## ✅ Quick Command Summary

| Action       | Command                                    | Description                                  |
|--------------|--------------------------------------------|----------------------------------------------|
| Start        | `docker-compose up`                        | Start the project                            |
| Start        | `docker-compose up -d` ❌                   | Run in detached (background) mode            |
| Rebuild      | `docker-compose up -d --build` 👍          | Rebuild and restart after config/lib changes |
| Stop         | `docker-compose down -v`                   | Stop and remove containers and volumes       |
| View Logs    | `docker-compose logs -f`                   | Follow logs in real time                     |
| Shell Access | `docker-compose exec account-service bash` | Access the container shell                   |

## 🤝 Contributions
All contributions (Pull Requests) are welcome.

## 📄 License
[MIT](LICENSE)

*Please do not use this project for commercial purposes.