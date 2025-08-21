FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY ewm-stats-service/target/ewm-stats-service-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
