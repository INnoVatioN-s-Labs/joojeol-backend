# 주절주절 Backend (joojeol-backend)

토스 앱 내 익명 소통 미니앱 **주절주절**의 백엔드 서버입니다.

## 🛠 Tech Stack

- **Language**: Java 17
- **Framework**: Spring Boot 3.x (with Spring Security, Spring Data JPA)
- **Database**: PostgreSQL (Supabase)
- **Build Tool**: Gradle

## 📂 Project Structure

```
src/main/java/com/assignment/joojeolbackend/
├── config/          # 설정 관련 클래스 (Security, WebMvc 등)
├── controller/      # API 요청 처리
├── service/         # 비즈니스 로직
├── domain/          # 도메인 엔티티
└── repository/      # 데이터베이스 접근
```

## 🚀 Getting Started ....

### Prerequisites

- Java 17+
- PostgreSQL (Supabase)

### Configuration

`src/main/resources/application.yml` 파일을 생성하고 데이터베이스 정보를 입력해야 합니다.

```yaml
spring:
    profiles:
        active: dev # dev 또는 prod
---
# Dev Profile
spring:
    config:
        activate:
            on-profile: dev
    datasource:
        url: jdbc:postgresql://<DEV_DB_HOST>:<PORT>/joojeol-dev
        username: ...
        password: ...

---
# Prod Profile
spring:
    config:
        activate:
            on-profile: prod
    datasource:
        url: jdbc:postgresql://<PROD_DB_HOST>:<PORT>/joojeol-prod
        username: ...
        password: ...
```

### Run

```bash
./gradlew bootRun
```
