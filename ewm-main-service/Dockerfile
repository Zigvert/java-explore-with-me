FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY ewm-service/pom.xml .
COPY ewm-service/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/ewm-service-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
