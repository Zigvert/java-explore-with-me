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

# Копируем JAR
COPY --from=builder /app/ewm-main-service/target/ewm-main-service-0.0.1-SNAPSHOT.jar ./main-service.jar

# Копируем скрипт wait-for-it.sh
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 8080

# Ждем запуска базы и потом стартуем сервис
ENTRYPOINT ["/wait-for-it.sh", "main-db:5432", "--timeout=60", "--strict", "--", "java", "-jar", "main-service.jar"]
