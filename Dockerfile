
FROM openjdk:17-oracle
EXPOSE 8080
RUN mkdir /app
COPY /build/libs/*.jar /app/posts-api.jar
#ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=production", "/app/posts-api.jar"]
ENTRYPOINT ["java","-jar", "/app/posts-api.jar"]