FROM java:8
EXPOSE 8087
ADD /target/movie-commentary-service-0.0.1-SNAPSHOT-spring-boot.jar movie-commentary-service.jar
ENTRYPOINT ["java","-jar","movie-commentary-service.jar -Dspring.datasource.url=jdbc:postgresql://postgres_container:5432/comments"]