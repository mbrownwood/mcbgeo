# mcbgeo

## Overview

This is a Spring Boot project.

It provides an API to find people who are listed
as either living in London, or whose current coordinates are within
50 miles of London.

It makes use of the API provided by https://dwp-techtest.herokuapp.com/.  

It first calls http://dwp-techtest.herokuapp.com/city/London/users to 
get users listed as living in London.  

It then calls http://dwp-techtest.herokuapp.com/users to get all users.  
Each user's coordinates are then checked to see if they are in within 50 miles of London.

A duplicate check is then performed to ensure that only distinct users are identified.

It's necessary to use a geometric point in London to act as a reference when
determining a 50 mile radius.  A geometric point for the centre of London was determined using
https://www.latlong.net/

A third party library is used to provide geometric capabilities for when 
determining a geometric point for London, a geometric circle of London, 
a geometric point for a user, and whether the user is within the geometric
circle:

```
<groupId>com.tomtom.speedtools</groupId>
<artifactId>geo</artifactId>
```

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

## Usage

Once started try:

[http://localhost:8080/spec-api.yaml](http://localhost:8080/spec-api.yaml)

[http://localhost:8080/v1/users/london/area](http://localhost:8080/v1/users/london/area)

A `Postman` collection is also provided at the root of the project.
