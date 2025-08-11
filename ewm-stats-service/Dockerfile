FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar", "--server.address=0.0.0.0"]
