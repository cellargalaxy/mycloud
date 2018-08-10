FROM jdk8:latest

COPY target/mycloud-0.0.1-SNAPSHOT.jar mycloud.jar

ENTRYPOINT ["java","-jar","/mycloud.jar"]