FROM openjdk:11
COPY target/mcbgeo-*.jar /mcbgeo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/mcbgeo.jar"]
