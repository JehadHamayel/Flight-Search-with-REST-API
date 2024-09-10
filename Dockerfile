# Use the official Gradle image with JDK 17 for the build stage
FROM openjdk:17-jdk-oracle
LABEL authors="Jehad.Hamayel"

COPY build/libs/*.jar testapp.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","testapp.jar"]

