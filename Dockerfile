FROM adoptopenjdk/openjdk11:alpine
COPY target/fiwall-*.jar app.jar
WORKDIR usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]