FROM azul/zulu-openjdk:17

WORKDIR /app

COPY target/notification-service-0.0.1-SNAPSHOT.jar /app/notification-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "notification-service-0.0.1-SNAPSHOT.jar"]