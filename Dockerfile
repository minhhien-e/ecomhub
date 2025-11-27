FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy toàn bộ project
COPY . .

# Fix CRLF Windows + cấp quyền
RUN apk add --no-cache dos2unix \
    && dos2unix gradlew \
    && chmod +x gradlew

RUN ./gradlew bootJar -x test

# Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=0 /app/build/libs/*.jar app.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "app.jar"]