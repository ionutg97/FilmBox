FROM java:8
EXPOSE 8084
ADD /target/movie-splitMovie-service-0.0.1-SNAPSHOT.jar splitMovie-service.jar
ENTRYPOINT ["java","-jar","splitMovie-service.jar"]