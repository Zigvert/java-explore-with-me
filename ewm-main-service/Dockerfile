FROM amazoncorretto:21-alpine

# Устанавливаем bash и curl (для wait-for-it.sh)
RUN apk add --no-cache bash curl

# Копируем собранный jar
COPY target/*.jar app.jar

# Копируем скрипт ожидания БД
COPY .github/workflows/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Ждём БД и запускаем сервис с разворачиванием переменных окружения
ENTRYPOINT ["/bin/sh", "-c", "/wait-for-it.sh ewm-main-db:5432 --timeout=60 --strict -- java $JAVA_OPTS -jar /app.jar"]
