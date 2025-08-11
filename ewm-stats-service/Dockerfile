FROM eclipse-temurin:21-jre-alpine

ARG JAR_FILE=target/ewm-stats-service-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
