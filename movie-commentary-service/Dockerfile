FROM java:8
EXPOSE 8087
ADD /target/movie-commentary-service-0.0.1-SNAPSHOT-spring-boot.jar movie-commentary-service.jar
ENTRYPOINT ["java","-Dspring.datasource.url=jdbc:postgresql://postgres-db:5432/comments","-Dspring.flyway.locations=/home/user/flyway","-jar","movie-commentary-service.jar"]