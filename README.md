# Test-task

## Start the project locally

#### 1.  Required to install

   * Java 8
   * H2 database

#### 2. How to run

1. You should create environmental variables that are defined in `application-dev.properties`

   All these variables you can set in Intellij Idea. For instance:

   ```properties
   spring.datasource.url=${DATABASE_URL}
   spring.datasource.username=${DATABASE_USER}
   spring.datasource.password=${DATABASE_PASSWORD}
   
   #Security
   jwt.tokenKey=${JWT_TOKEN_KEY}
   ```

2. If you did everything correctly, you should be able access swagger by this URL: http://localhost:8083/swagger-ui.html#/

#### 3. How to work with swagger UI in our project

1. Run the project (look up paragraph [How to run](#2-how-to-run)).

2. Use the following link to open Swagger UI: http://localhost:8083/swagger-ui.html#/

3. Use POST method with `/auth/sign-up` to create an account

4. Use POST method with `/auth/sign-in` to sign in. After entering the credentials you should receive access and refresh tokens

5. Copy the given access token and put it into Authentication Header. Press **Authorize** button

   ![authentication-swagger](https://github.com/VitaliyDzen/test-task/blob/master/docs-photos/authentication-swagger.png?raw=true)

   Insert the given token into input field. The scheme should be like this `Bearer <given_token>  `. Press **Authorize** button

   ![auth-bearer](https://github.com/VitaliyDzen/test-task/blob/master/docs-photos/auth-bearer.png?raw=true)

   