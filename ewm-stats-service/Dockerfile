FROM eclipse-temurin:17-jre-alpine
ARG JAR_FILE=target/ewm-stats-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
ENTRYPOINT ["/wait-for-it.sh", "stats-db:5432", "--timeout=60", "--", "java", "-jar", "/app.jar"]