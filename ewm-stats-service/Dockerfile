# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY . .
RUN mvn clean install -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/ewm-stats-service/target/ewm-stats-service-0.0.1-SNAPSHOT.jar ./stats-service.jar
COPY wait-for-it.sh ./
RUN chmod +x wait-for-it.sh

EXPOSE 9090
ENTRYPOINT ["./wait-for-it.sh", "stats-db:5432", "--timeout=60", "--strict", "--", "java", "-jar", "stats-service.jar"]
