# 1) BUILD STAGE
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

COPY . .

RUN apk add --no-cache dos2unix \
    && dos2unix gradlew \
    && chmod +x gradlew

RUN ./gradlew bootJar -x test


# 2) RUNTIME STAGE
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy JAR từ stage build
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8088
ENTRYPOINT ["java", "-jar", "app.jar"]
