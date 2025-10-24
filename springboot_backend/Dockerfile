FROM maven:3.9.6-eclipse-temurin-17 AS builder

ARG JAR_FILE=build/libs/*.jarC

COPY pom.xml /app/
COPY src /app/src/


WORKDIR /app
RUN mvn clean install -DskipTests


# mvn spring-boot:run

FROM eclipse-temurin:17-jdk

COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]