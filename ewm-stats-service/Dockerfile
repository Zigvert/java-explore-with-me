# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Копируем весь проект
COPY . .

# Сборка всех модулей
RUN mvn clean install -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

# Копируем собранный JAR
COPY --from=builder /app/ewm-stats-service/target/ewm-stats-service-0.0.1-SNAPSHOT.jar ./stats-service.jar

# Копируем скрипт wait-for-it.sh и даём права на исполнение
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Порт приложения
EXPOSE 9090

# Запуск с ожиданием базы
ENTRYPOINT ["/wait-for-it.sh", "stats-db:5432", "--", "java", "-jar", "stats-service.jar"]
