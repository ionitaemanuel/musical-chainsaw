Spring security/scheduled jobs exercises


Spring security
1. Include spring security in the spring boot.
2. define an role enum containing 2 roles: USER, ADMIN
3. create an in memory authentication mechanism with basic auth that contains 3 users:
    1. two users should have the role USER
    2. one user should have the role ADMIN
4. protect create and update user in such that only admins can perform those operations
5. protect the rest of the endpoints to be users or admin only
6. make /stats publicly available and without any authentication

Scheduled job
1. Extend your stats counter map to include besides the counter itself 
   also a lastUpdated field, which will be a date (Instand/OffsetDateTime)
2. Create a new class that will have:
    1.  a scheduled job that will run every minute and will log in the console output 
        the current stats. make the output formatted that it will say:
        "user with id=X has been searched for Y times" and a new line for each user id.
    2.  a second scheduled job that will run every 5 minutes and will search 
        the current track counter and reset/remove the stats for users not being 
        searched for in the past 2 minutes