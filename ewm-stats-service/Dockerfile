# === Stage 1: Build ===
FROM maven:3.9.3-eclipse-temurin-21 AS builder

WORKDIR /app

# Копируем родительский проект
COPY ../explore-with-me /app/explore-with-me

# Копируем сам сервис
COPY pom.xml .
COPY src ./src

# Сборка без запуска тестов
RUN mvn clean package -DskipTests

# === Stage 2: Runtime ===
FROM eclipse-temurin:21-jre

WORKDIR /app

# Копируем jar из builder
COPY --from=builder /app/target/ewm-stats-service-0.0.1-SNAPSHOT.jar ./ewm-stats-service.jar

# Указываем порт
EXPOSE 9090

# Команда запуска
ENTRYPOINT ["java","-jar","ewm-stats-service.jar"]
