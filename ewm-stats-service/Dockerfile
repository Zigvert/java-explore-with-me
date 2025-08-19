# === Stage 1: Build ===
FROM maven:3.9.3-eclipse-temurin-17 AS builder

WORKDIR /app

# Копируем родительский POM
COPY pom.xml ./pom.xml

# Копируем все модули (или только сервис, если родитель уже есть)
COPY ewm-stats-service ./ewm-stats-service

# Устанавливаем родительский POM в локальный репозиторий
RUN mvn install -N -f pom.xml

# Сборка сервиса с установленным родителем
RUN mvn clean package -DskipTests -f ewm-stats-service/pom.xml

# === Stage 2: Runtime ===
FROM eclipse-temurin:17-jre

WORKDIR /app

# Копируем собранный JAR сервиса
COPY --from=builder /app/ewm-stats-service/target/ewm-stats-service-0.0.1-SNAPSHOT.jar ./ewm-stats-service.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "ewm-stats-service.jar"]
