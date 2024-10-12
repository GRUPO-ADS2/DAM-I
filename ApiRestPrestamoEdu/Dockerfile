FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/ApiRestPrestamoEdu-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} Api_Prestamo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "Api_Prestamo.jar"]
