# Use lightweight Java 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Spring Boot jar
COPY target/cart-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app actually runs on (8083 for consistency)
EXPOSE 8083

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
