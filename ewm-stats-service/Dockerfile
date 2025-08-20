# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy project files
COPY pom.xml .
COPY ewm-stats-dto/pom.xml ewm-stats-dto/
COPY ewm-stats-dto/src ewm-stats-dto/src
COPY ewm-stats-client/pom.xml ewm-stats-client/
COPY ewm-stats-client/src ewm-stats-client/src
COPY ewm-stats-service/pom.xml ewm-stats-service/
COPY ewm-stats-service/src ewm-stats-service/src

# Build and install modules to local Maven repository
RUN mvn clean install -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/ewm-stats-service/target/ewm-stats-service-0.0.1-SNAPSHOT.jar ./ewm-stats-service.jar

# Expose the application port
EXPOSE 9090

# Run the application
ENTRYPOINT ["java", "-jar", "ewm-stats-service.jar"]