FROM java:8
EXPOSE 8761
ADD /target/discovery-server-0.0.1-SNAPSHOT-spring-boot.jar discovery-server.jar
ENTRYPOINT ["java","-jar","discovery-server.jar"]