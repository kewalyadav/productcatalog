# Product-Catalog-Spring-Boot-Application

 Product Catalog Crud Application with H2, JPA, Spring Boot. 
 
# Product Catalog System

This project based on the Spring Boot project and used packages:

<ul>
<li>Spring Boot</li>
<li>Spring Data</li>
<li>H2</li>
<li>Maven</li>
</ul>

## Installation

### 1. Clone the application

<pre> $ git clone https://github.com/kewalyadav/productcatalog.git </pre>

### 2. Database Configuration

H2 In-memory DB is used as the database so no addition DB configuration needed.
The project is created with Maven.

### 3. Launch

To run the application locally:

<pre>mvn clean install</pre>

<pre>mvn spring-boot:run</pre>

Use above two commands to run the application.

Application runs from <b>http://localhost:8080</b> and it will open Swagger UI <b>http://localhost:8080/swagger-ui/index.html</b>

H2 DB can be accessed from <b>http://localhost:8080/h2</b>
