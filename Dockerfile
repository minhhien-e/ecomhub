FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY target/log-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "app.jar"]

