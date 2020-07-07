FROM java:8
EXPOSE 8086
ADD /target/dataBaseMovie-0.0.1-SNAPSHOT-spring-boot.jar dataBaseMovie.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-Dspring.datasource.url=jdbc:postgresql://postgres-db-movie:5432/movies","-Dspring.flyway.locations=/home/user/flyway", "-jar","dataBaseMovie.jar"]
