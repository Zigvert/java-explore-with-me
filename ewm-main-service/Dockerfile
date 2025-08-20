# Stage 1: сборка через Maven + JDK 21
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Копируем все pom.xml и исходники проекта
COPY . .

# Скачиваем зависимости и собираем все модули
RUN mvn clean install -DskipTests

# Stage 2: runtime с JRE 21
FROM eclipse-temurin:21-jre
WORKDIR /app

# Копируем собранный jar main-service
COPY --from=build /app/ewm-main-service/target/ewm-main-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
