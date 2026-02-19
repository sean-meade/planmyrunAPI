FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
COPY . .

RUN ./mvnw bootJar --no-daemon

FROM eclipse-temurin:21-jre-jammy

EXPOSE 8080

COPY /target/planmyrunAPI-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]