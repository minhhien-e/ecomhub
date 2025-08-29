FROM eclipse-temurin:17-jre-focal

WORKDIR /app

COPY target/inventory-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8087

ENTRYPOINT ["java","-jar","app.jar"]
