# KEA School Administration

This application was our final mandatory assignment on the 3. semester in the computer science study programme at KEA. 
We had to build an application that will administrate the schools teachers, courses, students and student applications. The application will get courses from a REST service.

## Requirments
-CRUD for course
-Three types of users: student, teacher and admin.
-Get courses from a REST service and intergrate with our own information.
-Dynamically alter the webpages by what kind of user that is logged in, example: student cant enter applications.

## Application structure
-Spring Boot MVC
-Spring Security
-Spring JPA (build database from the models in java)
-Dynamic webpages using jQuery
-MySQL for database

## Project structure
We used Scrum for managing the project, and all the related practices, example scrumboard, planning poker etc.
We also used UML to create diagrams, for the big design decisions.

## Manual
1. Clone the repo.
2. Build the project with IntelliJ
3. Run the KEA_ADMIN_DB_POPULATE script
4. Open browser and navigate to localhost:8080
5. Log in:
  admin: username: Jarlen, password: 123
  student: username: Paul69, password: 123
  teacher: username: MuskelMartin, password: 123

