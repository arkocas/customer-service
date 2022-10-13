FROM openjdk:11
EXPOSE 8080
LABEL maintainer = "alirizakocas"
ARG JAR_FILE=target/customer-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} customer-service.jar
ENTRYPOINT ["java", "-jar", "/customer-service.jar"]
