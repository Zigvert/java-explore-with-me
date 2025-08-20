# Stage 1: Build main-service
FROM maven:3.9.9-eclipse-temurin-21 AS builder-main

WORKDIR /app

# Копируем корневой pom.xml и main-service
COPY pom.xml .
COPY ewm-main-service/pom.xml ewm-main-service/
COPY ewm-main-service/src ewm-main-service/src

# Собираем main-service и устанавливаем в локальный Maven репозиторий
RUN mvn clean install -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder-main /app/ewm-main-service/target/ewm-main-service-0.0.1-SNAPSHOT.jar ./main-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "main-service.jar"]
