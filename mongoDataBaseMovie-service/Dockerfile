FROM java:8
EXPOSE 8088
ADD /target/mongoDataBaseMovie-service-0.0.1-SNAPSHOT.jar mongoDataBaseMovie-service.jar
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongodb:27017/movie","-jar","mongoDataBaseMovie-service.jar"]
#ENTRYPOINT ["java","-jar","mongoDataBaseMovie-service.jar"]
