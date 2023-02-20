FROM openjdk:17
EXPOSE 3000
ADD target/catalogApi.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]