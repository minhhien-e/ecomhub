# Stage 1: Build the application
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .

COPY ./infrastructure ./infrastructure
COPY ./application ./application
COPY ./domain ./domain
COPY ./shared ./shared


RUN chmod +x gradlew
RUN ./gradlew :infrastructure:bootJar -x test

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/infrastructure/build/libs/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]