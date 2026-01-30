# 주절주절 Backend (joojeol-backend)

토스 앱 내 익명 소통 미니앱 **주절주절**의 백엔드 서버입니다.

## 🛠 Tech Stack

- **Language**: Java 17
- **Framework**: Spring Boot 3.x (with Spring Security, Spring Data JPA)
- **Database**: PostgreSQL (Supabase) + **Redis** (Session & Caching)
- **Infrastructure**: Docker, Docker Compose (Optimized for EC2 t3.micro)
- **Build Tool**: Gradle

## 📂 Project Structure

```
src/main/java/com/assignment/joojeolbackend/
├── config/          # 설정 (Security, Redis, WebMvc 등)
├── controller/      # API 요청 처리
├── service/         # 비즈니스 로직
├── domain/          # 도메인 엔티티
└── repository/      # 데이터베이스 접근
```

## 🚀 Getting Started ....

### Prerequisites

- Java 17+
- Docker & Docker Compose
- PostgreSQL (Supabase or Local)

### Configuration

`src/main/resources/application.yml` 설정이 필요합니다. (Redis는 Docker 실행 시 자동 설정됨)

### Run (Docker Compose) - 권장

EC2 t3.micro 환경을 고려하여 **Backend + Redis**의 경량화된 구성으로 실행됩니다.

```bash
# 1. 애플리케이션 빌드 (Test 제외)
./gradlew clean build -x test

# 2. Docker Compose 실행 (Backend + Redis)
docker-compose up -d --build
```

### Run (Local)

로컬에서 개발 시에도 Redis가 필요합니다. Docker로 Redis만 띄우고 실행하세요.

```bash
# Redis만 실행
docker-compose up -d redis

# Spring Boot 실행
./gradlew bootRun
```
