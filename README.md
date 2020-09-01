# DropWizard Test Task

Creating database
---
Create database locally or run docker command:

`docker run -p 5432:5432 -e POSTGRES_PASSWORD=password postgres`

Steps to launch
---
1. Run `mvn clean install` to build your application
1. Migrate the database (create required tables):
```java -jar target/dropwizard-test-task-1.0.0.jar db migrate config-local.yml```

4. Start application with `java -jar target/dropwizard-test-task-1.0.0.jar server config-local.yml`
5. Now you can access the application on `http://localhost:8080/v1/name`

JWT
---
To generate JWT token go to the `http://localhost:8080/v1/jwt/generate`. Then enter the following credentials:

Login: `login` \n
Password: `password`

You can then access protected resource with JWT token by link `http://localhost:8080/v1/auth`

Deploy with Docker Compose
---
To run application with docker compose, run following in the root directory where docker-compose.yml file exists

`docker-compose up`

To stop and remove the containers run following

`docker-compose down` 
