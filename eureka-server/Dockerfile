# Sử dụng image JDK 17 chính thức
FROM azul/zulu-openjdk:17

# Tạo thư mục ứng dụng trong container
WORKDIR /app

# Sao chép file JAR vào thư mục làm việc trong container
COPY target/eureka-server-0.0.1-SNAPSHOT.jar /app/eureka-server-0.0.1-SNAPSHOT.jar

# Chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "eureka-server-0.0.1-SNAPSHOT.jar"]
