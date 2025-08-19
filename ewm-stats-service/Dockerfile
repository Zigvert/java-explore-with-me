# === Stage 1: Build ===
FROM maven:3.9.3-eclipse-temurin-17 AS builder

WORKDIR /app

# Копируем весь проект (родительский POM и все подпроекты)
COPY ../.. /app

# Сборка только модуля ewm-stats-service без тестов
RUN mvn clean install -pl ewm-stats-service -am -DskipTests

# === Stage 2: Runtime ===
FROM eclipse-temurin:17-jre

WORKDIR /app

# Копируем собранный JAR сервиса
COPY --from=builder /app/ewm-stats-service/target/ewm-stats-service-0.0.1-SNAPSHOT.jar ./ewm-stats-service.jar

# Открываем порт
EXPOSE 9090

# Запуск приложения
ENTRYPOINT ["java", "-jar", "ewm-stats-service.jar"]
