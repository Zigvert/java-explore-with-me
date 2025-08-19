# === Stage 1: build jar ===
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Копируем pom.xml и скачиваем зависимости
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# === Stage 2: runtime image ===
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Копируем jar из builder stage
COPY --from=builder /app/target/ewm-stats-service-*.jar app.jar

# Переменные окружения (можно переопределить через docker-compose)
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
