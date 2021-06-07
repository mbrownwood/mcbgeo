# mcbgeo

## To Build

This is a maven project. To build the project from command line use:

```
mvn clean verify
```

This will package the deployable JAR file and check for any dependency vulnerabilities.

## To Run

To run the application, once the project has been built, use:

```
java -jar target/mcbgeo-1.1.0-SNAPSHOT.jar
```

This will start the application on localhost:8080

Once started try:

[http://localhost:8080/spec-api.yaml](http://localhost:8080/spec-api.yaml)

[http://localhost:8080/v1/users/london/area](http://localhost:8080/v1/users/london/area)
