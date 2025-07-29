FROM mcr.microsoft.com/openjdk/jdk:17-ubuntu as base

EXPOSE 8080

ADD target/library-microservice.jar library-microservice.jar

ENTRYPOINT ["java", "-jar", "/library-microservice.jar"]