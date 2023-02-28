FROM openjdk:17
EXPOSE 3000
#WORKDIR /app
#ADD target/*.jar catalogapp.jar
ADD target/catalogApi.jar /catalogapp.jar
ENTRYPOINT ["java","-jar","/catalogapp.jar"]
#CMD ["java","-jar","/app/catalogapp.jar"]