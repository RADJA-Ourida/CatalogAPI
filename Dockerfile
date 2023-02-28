FROM openjdk:17
EXPOSE 3000
ADD target/catalogApi.jar /catalogapp.jar
ENTRYPOINT ["java","-jar","/catalogapp.jar"]