FROM amazoncorretto:21-alpine

# Копируем jar из модуля ewm-main-service
COPY ewm-main-service/target/*.jar /app.jar

# Копируем wait-for-it.sh из корня проекта
COPY .github/workflows/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Точка входа
ENTRYPOINT ["/bin/sh", "-c", "/wait-for-it.sh ewm-main-db:5432 --timeout=60 --strict -- java -jar /app.jar"]
