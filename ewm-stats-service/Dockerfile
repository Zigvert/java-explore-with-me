# Используем базовый образ с JDK 21 Alpine для компактности
FROM eclipse-temurin:21-jdk-alpine

# Рабочая директория внутри контейнера
WORKDIR /app

# Копируем собранный jar в контейнер
COPY target/ewm-stats-service-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт 9090
EXPOSE 9090

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
