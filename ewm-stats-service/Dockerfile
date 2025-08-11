FROM eclipse-temurin:21-jre

ARG JAR_FILE=target/ewm-stats-service-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /app.jar
COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

ENTRYPOINT ["bash", "/wait-for-it.sh", "stats-db:5432", "--timeout=90", "--strict", "--", "java", "-jar", "/app.jar", "--server.address=0.0.0.0"]
