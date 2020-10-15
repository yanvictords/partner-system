FROM openjdk:11
ARG JAR_FILE=target/partner-java.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8130
ENTRYPOINT ["java", "-jar", "/app.jar"]