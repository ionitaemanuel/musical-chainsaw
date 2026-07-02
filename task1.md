Develop a Spring Boot application that manages a database of users. The application should provide a web interface using Thymeleaf to display user data and offer API endpoints to perform CRUD operations. Additionally, use AOP to track the number of times each user is retrieved by ID.
1. Project Setup
   Task: Initialize a new Spring Boot project.
* Use Spring Initializr to create a new project with dependencies for Spring Web, Spring Data JPA, Thymeleaf, and h2 ( in memory db).
2. Define the User Entity
   Task: Create a User entity class.
* Use @Entity annotation to define a User class with attributes such as id, name, email, etc.
* Use @Id and @GeneratedValue for the primary key.
3. Create Repository Layer
   Task: Develop the UserRepository interface.
* Use @Repository to annotate the UserRepository interface that extends JpaRepository, enabling CRUD operations on the user data.
4. Service Layer
   Task: Implement the UserService class.
* Use @Service to create a UserService class that will use UserRepository to access and manage user data.
* Implement methods for CRUD operations.
5. Controller Layer
   Task: Build UserControllers for web and API interactions.
* Create a UserViewController with @Controller for web views using Thymeleaf templates to list users.
    * GET / - retrieve all users as HTML output
* Develop a UserApiController with @RestController for CRUD API endpoints returning data in JSON format.
* you should have 6 endpoints:
    * GET /users - retrieve all users
    * GET /users/{id} - retrieve user by id
    * PUT /users/{id} - update user
    * DELETE /users/{id} - delete user
    * GET /stats - retrieve map of user names and how many times have they been looked up
    * PUT /stats/reset - resets to 0 all stats
6. Thymeleaf Templates
   Task: Design Thymeleaf templates for user data presentation.
* Create HTML templates to display a list of users.
* there should be a field in the template that can be read from application.yml and passed along to the template engine. (java properties class containing a field title and can be read from app.title)
7. Implement AOP for Tracking
   Task: Use AOP to monitor access to the user details.
* Define an @Aspect component to log and count how many times each 'get user by id' endpoint is accessed.
* Store counts as a Map (optional, you can store this in a db) and create a separate 'stats' endpoint to display these statistics.
* the service responsible for keeping track of the user get by id stats should be defined as a Bean in a java configuration