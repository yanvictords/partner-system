FROM maven:3.6-jdk-11 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

FROM openjdk:11
COPY --from=build /usr/src/app/target/partner-java.jar /usr/app/partner-java.jar
EXPOSE 8130
ENTRYPOINT ["java", "-jar", "/usr/app/partner-java.jar"]