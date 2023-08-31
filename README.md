# Java Example Api

An Api using Java 17, Spring and the following packages:

- Spring Boot starter web (Version 3.1.3)
- In-memory database (h2)
- Log4j2
- Lombok
- Modelmapper
- Junit 5 tests and Jacoco plugin for coverage

### Endpoints

GET: `http://localhost:9090/api/v1/users`

Finds all active users.

GET: `http://localhost:9090/api/v1/users/{id}`

Gets an user by its id. Returns 404 if the user was not found.

GET: `http://localhost:9090/api/v1/users/references/{reference}`

Gets an user by its reference. Returns 404 if the user was not found.

POST: `http://localhost:9090/api/v1/users`

Creates an user. Example request body:

`
{
"firstName": "Foo",
"lastName": "Bar",
"email": "foobar@email.com"
}
`

Returns 201 is the user creation was successful.

PUT: `http://localhost:9090/api/v1/users/{id}`

Updates an existent user by it's id. Example request body:

`
{
"firstName": "Foo",
"lastName": "Bar",
"email": "foobar@email.com"
}

Returns 200 with the updated user if it was successful. This endpoint also allows to enable deleted (inactive) users.

DELETE: `http://localhost:9090/api/v1/users/{id}`

Deletes an existent user by it's id. The deletion is logical, so the user registry is preserved in the persistence, but it will be unavailable in the find endpoints. Later you can reactivate the user with the PUT endpoint.

Returns 200 with the deleted user if it was successful.

### Compile and run

To run using in-memory database, you can use Maven:

`mvn spring-boot:run -Dspring-boot.run.profiles=local`

### Run the tests

To run the test, use:

`mvn test`

You will find the coverage report visiting `/target/site/jacoco` directory.

### Create the Docker container

Create the package using the following command. The jar file will be created into the target directory:

`mvn package`

Build the image

`docker build . -t IMAGE_NAME --build-arg ARTIFACT=exampleapi --build-arg VERSION=0.0.1-SNAPSHOT --build-arg ENVIRONMENT=develop`

You can change the environment values according the project, the version you want to deploy, and the project environment.

- IMAGE_NAME: The project name (Ex: exampleapi)
- ARTIFACT and VERSION: Created jar artifact name and artifact version
- ENVIRONMENT: The environment to execute (affects configurations)
- PORT: Application port

Run the image:

`docker run -p PORT:PORT IMAGE_NAME`

### Q & A

TBD

### Changelog

0.0.1

* Initial version
