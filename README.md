# CatalogAPI


This application is an API for an online catalog with different products and the possibility to add add items to cart and pay to bye them.
In this application we also perform the CI/CD and the deployement to Google Cloud Registry and Google Kubernetes Engine.
# Requirments :
- Any IDE, Intellij for example
- JDK 17
-  Docker
- PostgreSql
- Git
- An account in Google cloud Plateform, create a project and activate the invoicing
- Have a cluster on Google Kubernetes Engine



## To run this application in local machine:
- clone the project and run it
- On the btowser past this :   http://localhost:8000/api/products
## To run unit tests and Integration tests, paste this on the terminal :
- Unit tests:  mvn test
- Integration tests:  mvn verify -P integration-test -Dtest=*IntTest


## To dockerise this application in Local:
Two ways to do this :
- With Docker-compose, run this on terminal :

      ./mvnw clean package -Dskiptest
      
      docker compose up 

- with Dockerfile only: 

      ./mvnw clean package -Dskiptest 
   
      docker build -t catalogapp .  //To create the image and store it in Docker

      docker run -p 9000:8000 catalogapp


## The end pointes of the application on Docker :
http://localhost:9000/api/hello

http://localhost:9000/api/products

http://localhost:9000/api/newProduct //Perform this from postman



## TO Deploy in Google Cloud Platform

The workflow is trigerred automaticly on each push to the main branch.

If the deployement is successfuly done, we find in GKE -> services & Ingres the URL to our application

   
  
      
       
 

