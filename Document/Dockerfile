# Sử dụng image nhẹ Java 17
FROM eclipse-temurin:17-jdk-alpine

# Tạo thư mục làm việc trong container
WORKDIR /app

# Copy file JAR từ thư mục target
COPY target/cart-service-0.0.1-SNAPSHOT.jar app.jar

# Mở port container chạy ứng dụng (8083)
EXPOSE 8083

# Câu lệnh khởi chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
