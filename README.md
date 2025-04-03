# **Movie Task**  

## **Overview**  
This is a **Java Spring Boot** and **Angular** application that fetch movies data from external api and import them. The backend is built using Spring Boot, and the frontend is developed using Angular.  

### Prerequisites  
Ensure you have the following installed:  
- **Java 17+**  
- **Node.js & npm**  
- **Angular CLI**  
- **Maven**  
- **MySQL**  

1. **Configure application.properties**  
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=root
spring.datasource.password=your_password

omdb.api.key=
security.jwt.token.secret-key=

spring.jpa.hibernate.ddl-auto=create  # This will create the tables (only run once) ... it is better to comment it after the app run for the first time 

2. **Execute the SQL file**  
admin_user_register.sql

this file will create the admin user and will add the admin role and also will create a row for the relation between them 




