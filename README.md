# amdocs

# Authorization service
 Authorization service will act as API Gateway for all requests
 It has 5 operations
 
# POST /login 
It requires username and password in the post body. 
User will be authenticated and token will be provided
Access token will be provide as result if operation is successful

# POST /profile 
Access token should be passed as input header
If user is authenticated user can create their own profile info.
Synchronous rest call between Authorization and Profile Service.

# PUT /profile 
Access token should be passed as input header
If user is authenticated they can update their own profile only.
Asynchronous rest call between Authorization and Profile Service.

# DELETE /profile
Access token should be passed as input header
If user is authenticated they can delete their own profile only.
Asynchronous rest call between Authorization and Profile Service.

# DELETE /createusercredentials
created for test purpose

# Steps to run this Service on local
1. Build and Run User Profile Service
2. Check kafka bootstrapAddress,topics in application.yml
3. Check User Profile Service url in application.yml
2. Build this service using command "mvn clean install"
3. Run this service using command "mvn spring-boot:run"

# Order of operations to be performed
1. POST /createusercredentials
2. POST /login 
3. POST /profile 
4. PUT /profile 
5. DELETE /profile 

 
 