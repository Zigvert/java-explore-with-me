# === Stage 1: Build ===
FROM maven:3.9.3-eclipse-temurin-17 AS builder

WORKDIR /app

# Копируем родительский POM и модуль сервиса
COPY pom.xml ./            # родительский POM (Explore With Me)
COPY ewm-stats-service/pom.xml ./ewm-stats-service/pom.xml
COPY ewm-stats-service/src ./ewm-stats-service/src

# Собираем только модуль ewm-stats-service с учетом зависимостей родителя
RUN mvn clean package -pl ewm-stats-service -am -DskipTests

# === Stage 2: Runtime ===
FROM eclipse-temurin:17-jre

WORKDIR /app

# Копируем собранный JAR
COPY --from=builder /app/ewm-stats-service/target/ewm-stats-service-0.0.1-SNAPSHOT.jar ./ewm-stats-service.jar

# Открываем порт
EXPOSE 9090

# Запуск приложения
ENTRYPOINT ["java","-jar","ewm-stats-service.jar"]
