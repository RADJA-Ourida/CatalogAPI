FROM openjdk:17
EXPOSE 3000
ADD target/catalogApi.jar /appdockerimag.jar
ENTRYPOINT ["java","-jar","/appdockerimag.jar"]