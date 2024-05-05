FROM openjdk:17-alpine
WORKDIR /app
COPY ./target/whimsy-short-url.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "whimsy-short-url.jar"]
