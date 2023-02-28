FROM openjdk:17
EXPOSE 3000
ADD target/catalogApi.jar /catalogApp.jar
ENTRYPOINT ["java","-jar","/catalogApp.jar"]