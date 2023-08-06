FROM openjdk:17
ADD ./target/SocialMedia-0.0.1-SNAPSHOT.jar socialmedia.jar
ENTRYPOINT ["java", "-jar", "socialmedia.jar"]
EXPOSE 8080