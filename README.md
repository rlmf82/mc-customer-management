
**MC Customers**

Here you can find the information about how to execute this application and details of configuration.

**============================= EXECUTION ==============================**

To exeute this application run the command:

mvn spring-boot:run

**============================== Swagger ==============================**

Swagger documentation is available to support application use. The URL to Swagger is:

http://localhost:8081/v1/swagger-ui.html

**============================== DATABASE ==============================**

This application uses an H2 Database. You can access the structure through the URL below:

http://localhost:8081/v1/h2-console

As the application is configured to use the Flyway, all structures and initial data will be inserted at the first execution.

**============================== POSTMAN ==============================**

There is a postman collection in the root of this project called **Customers.postman_collection.json**
