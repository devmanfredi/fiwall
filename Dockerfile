FROM adoptopenjdk/openjdk11:alpine
VOLUME /tmp
COPY target/fiwall-*.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]