# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY . .
RUN mvn clean install -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/ewm-main-service/target/ewm-main-service-0.0.1-SNAPSHOT.jar ./main-service.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "main-service.jar"]
