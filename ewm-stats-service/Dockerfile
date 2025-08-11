# Используем базовый образ с JDK 17
FROM eclipse-temurin:17-jdk-alpine

# Указываем рабочую директорию
WORKDIR /app

# Копируем jar-файл сервиса статистики в контейнер
COPY target/ewm-stats-service-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт
EXPOSE 9090

# Запускаем сервис
ENTRYPOINT ["java", "-jar", "app.jar"]
