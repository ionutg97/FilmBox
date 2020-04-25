FROM java:8
EXPOSE 8086
ADD /target/dataBaseMovie-0.0.1-SNAPSHOT.jar dataBaseMovie.jar
ENTRYPOINT ["java","-jar","dataBaseMovie.jar"]


#"-Dspring.flyway.locations=home/user/sql/postgres",
#"-Dspring.datasource.url=jdbc:postgresql://postgres:5432/movies"
#-Dspring.datasource.url=jdbc:postgresql://postgres_container:5432/comments