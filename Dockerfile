FROM eclipse-temurin:21-jdk-alpine

# Thiết lập thư mục làm việc
WORKDIR /app


# Copy file JAR vào container
COPY target/*.jar app.jar

EXPOSE 8080


# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]