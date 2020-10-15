FROM openjdk:11
ARG JAR_FILE=target/campaign-java.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8140
ENTRYPOINT ["java", "-jar", "/app.jar"]