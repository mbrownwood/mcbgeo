# mcbgeo

## Contents of this file

* Overview
* Build
* Run
  * standalone
  * docker
* Usage
* Commits

## Overview

This is a Spring Boot project.

It provides an API to find people who are listed
as either living in London, or whose current coordinates are within
50 miles of London.

It makes use of the API provided by https://bpdts-test-app.herokuapp.com/.  

It first calls https://bpdts-test-app.herokuapp.com/city/London/users to 
get users listed as living in London.  

It then calls https://bpdts-test-app.herokuapp.com/users to get all users.  
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

## Build

This is a maven project. To build the project from command line use:

```
mvn clean verify
```

This will package the deployable JAR file and check for any dependency vulnerabilities.

## Run

### standalone

To run the application standalone use:

```
java -jar target/mcbgeo-1.2.0-SNAPSHOT.jar
```

This will start the application on localhost:8080

### docker

To run the application in a container use:

```
docker-compose up --build -d
```

This will start the application on localhost:8080

To bring down use:

```
docker-compose down
```

To follow logs use:

```
docker-compose logs -f
```

See https://docs.docker.com/compose/ for more info on Docker Compose.

## Usage

Once started try:

[http://localhost:8080/spec-api.yaml](http://localhost:8080/spec-api.yaml)

[http://localhost:8080/v1/users/london/area](http://localhost:8080/v1/users/london/area)

A `Postman` collection is also provided at the root of the project.

## Commits

When committing use the conventional commit specification
https://www.conventionalcommits.org/

To manage this locally, enable a client side git hook.

To enable a client side git hook, each of us will need to configure our local git projects.  
For each of our projects, at `.git/hooks` create a file called `commit-msg`
(I'm assuming one doesn't already exist) and copy this in:

```
#!/usr/bin/env bash

conventional_commit_regex="^(build|chore|ci|docs|feat|fix|perf|refactor|revert|style|test)(\(.+\))?!?: .+$"

commit_message=$(cat "$1")

if [[ "$commit_message" =~ $conventional_commit_regex ]]; then
  echo -e "Commit message meets Conventional Commit standards..."
  exit 0
fi

echo -e "The commit message does not meet the Conventional Commit standard"
echo "An example of a valid message is: "
echo "  feat(login): add the 'remember me' button"
echo "More details at: https://www.conventionalcommits.org/en/v1.0.0/#summary"

exit 1
```

Make `commit-msg` executable.  Something like
```
chmod 755 commit-msg
```