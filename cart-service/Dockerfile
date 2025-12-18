#FROM eclipse-temurin:17-jdk-alpine
#
#WORKDIR /app
#
#COPY target/cartservice-0.0.1-SNAPSHOT.jar app.jar
#
#EXPOSE 9099
#
#ENTRYPOINT ["java", "-jar", "app.jar"]
# --- Stage 1: Build bằng Maven & Java 24 ---
# Lưu ý: Dùng image maven có sẵn JDK 24
# File: Dockerfile
FROM maven:3.9-eclipse-temurin-24 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:24-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]