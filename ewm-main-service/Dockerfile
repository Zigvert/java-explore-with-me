# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY . .
RUN mvn clean install -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/ewm-main-service/target/ewm-main-service-0.0.1-SNAPSHOT.jar ./main-service.jar

# Устанавливаем postgresql-client и curl
RUN apt-get update && apt-get install -y postgresql-client curl

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "until pg_isready -h main-db -p 5432 -U main -d main; do echo 'Waiting for main-db...'; sleep 2; done; until curl -f http://stats-server:9090/actuator/health; do echo 'Waiting for stats-server...'; sleep 2; done; java -jar main-service.jar"]
