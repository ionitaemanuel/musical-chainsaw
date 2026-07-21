Task 4 :
Migrate from SQL to NoSQL (MongoDB)

Replace the H2 and JPA implementation with MongoDB (configure it using Docker). Use the Mongo Spring repository interface and an ODM (Object-Document Mapping).
Add audit fields on the User entity.
Add Liquibase for MongoDB and add an initial schema for data seeding (e.g., add 10 initial users).
Add another migration to edit all users as follows: add a new field isStaff which is true for users with a @pitchtech.com email
(or other field up to you, you can be creative here). HINT: You can use a class that implements CustomTaskChange and ApplicationContextAware for this.
