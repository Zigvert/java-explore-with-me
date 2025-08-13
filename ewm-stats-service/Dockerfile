# Используем JDK 21 на Alpine
FROM eclipse-temurin:21-jdk-alpine

# Рабочая директория внутри контейнера
WORKDIR /app

# Копируем собранный JAR в контейнер
COPY target/ewm-stats-service-0.0.1-SNAPSHOT.jar app.jar

# Команда запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
