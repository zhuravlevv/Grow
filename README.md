#Grow Spring Boot project

##Requirements

For building and running the application you need:

- JDK 1.8
- Maven 3
##Build

To build project go to Project folder and execute
```
mvn clean install  
```

##Running the Spring Boot application
###To start web:
```
cd webapp
mvn spring-boot:run
```
###To start REST:
```
cd rest
mvn spring-boot:run
```

Application will be available at:

```
http://localhost:8081
```