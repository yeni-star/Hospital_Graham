FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/bff-hospital-graham-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/wallet src/main/resources/wallet

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]