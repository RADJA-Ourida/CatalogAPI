FROM openjdk:17
EXPOSE 3000
WORKDIR /app
ADD target/*.jar catalogapp.jar
#ENTRYPOINT ["java","-jar","/app/catalogapp.jar"]
CMD ["java","-jar","/app/catalogapp.jar"]