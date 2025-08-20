# Stage 1: Build stats-service with its dependencies
FROM maven:3.9.9-eclipse-temurin-21 AS builder-stats

WORKDIR /app

# Копируем корневой pom.xml и зависимости
COPY pom.xml .

COPY ewm-stats-dto/pom.xml ewm-stats-dto/
COPY ewm-stats-dto/src ewm-stats-dto/src

COPY ewm-stats-client/pom.xml ewm-stats-client/
COPY ewm-stats-client/src ewm-stats-client/src

COPY ewm-stats-service/pom.xml ewm-stats-service/
COPY ewm-stats-service/src ewm-stats-service/src

# Собираем и устанавливаем модули в локальный Maven репозиторий
RUN mvn clean install -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder-stats /app/ewm-stats-service/target/ewm-stats-service-0.0.1-SNAPSHOT.jar ./stats-service.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "stats-service.jar"]
