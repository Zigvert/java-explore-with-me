# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY . .
RUN mvn clean install -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/ewm-stats-service/target/ewm-stats-service-0.0.1-SNAPSHOT.jar ./stats-service.jar

# Устанавливаем postgresql-client для использования pg_isready
RUN apt-get update && apt-get install -y postgresql-client

EXPOSE 9090
ENTRYPOINT ["sh", "-c", "until pg_isready -h stats-db -p 5432 -U stats -d stats; do echo 'Waiting for stats-db...'; sleep 2; done; java -jar stats-service.jar"]