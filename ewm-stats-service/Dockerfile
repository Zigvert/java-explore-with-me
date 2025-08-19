# Stage 1: Build
FROM maven:3.9.3-eclipse-temurin-21 AS builder

WORKDIR /app

# Копируем весь проект (родительский POM и модули)
COPY pom.xml .
COPY ewm-stats-dto/pom.xml ewm-stats-dto/
COPY ewm-stats-dto/src ewm-stats-dto/src
COPY ewm-stats-client/pom.xml ewm-stats-client/
COPY ewm-stats-client/src ewm-stats-client/src
COPY ewm-stats-service/pom.xml ewm-stats-service/
COPY ewm-stats-service/src ewm-stats-service/src

# Сборка всех модулей и установка в локальный Maven-репозиторий
RUN mvn clean install -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre

WORKDIR /app

# Копируем готовый JAR сервиса
COPY --from=builder /app/ewm-stats-service/target/ewm-stats-service-0.0.1-SNAPSHOT.jar ./ewm-stats-service.jar

EXPOSE 9090

ENTRYPOINT ["java","-jar","ewm-stats-service.jar"]
