# Sử dụng hình ảnh cơ sở Maven để xây dựng ứng dụng
FROM maven:3.8.3-openjdk-17 AS build

# Sao chép các tệp tin từ thư mục cục bộ vào hình ảnh
COPY . /app

# Thiết lập thư mục làm việc
WORKDIR /app

# Xây dựng ứng dụng
RUN mvn clean package -DskipTests

# Sử dụng hình ảnh cơ sở OpenJDK để chạy ứng dụng đã xây dựng
FROM openjdk:17-alpine

# Sao chép file JAR đã xây dựng từ giai đoạn trước vào hình ảnh
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /app/app.jar

# Mở cổng mạng
EXPOSE 8080

# Chạy ứng dụng Spring Boot
CMD ["java", "-jar", "/app/app.jar"]
