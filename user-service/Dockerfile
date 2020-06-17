FROM java:8
EXPOSE 8091
ADD /target/user-service-0.0.1-SNAPSHOT.jar user-service.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-Dspring.datasource.url=jdbc:mysql://mysql-db:3306/users?allowPublicKeyRetrieval=true","-jar","user-service.jar"]