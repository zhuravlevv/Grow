# Departments Management Spring Boot project

## Requirements

For building and running the application you need:

- JDK 1.8
- Maven 3
## Build

To build project go to Project folder and execute
```
mvn clean install  
```

## Running the Spring Boot application
### To start web:
```
cd webapp
mvn spring-boot:run
```
or
```
cd webapp
java -jar target/webapp-1.0-SNAPSHOT.jar
```
### To start REST
#### Development Profile (runs h2 database)
```
cd rest
mvn spring-boot:run -Pdev
```
or
```
cd rest
java -jar -Dspring.profiles.active=dev target/rest-1.0-SNAPSHOT.jar
```
#### Production Profile (runs mysql database)
```
cd rest
mvn spring-boot:run -Pprod
```
or
```
cd rest
java -jar -Dspring.profiles.active=prod target/rest-1.0-SNAPSHOT.jar
```
Application will be available at:

```
http://localhost:8081
```

#### Available REST endpoints:	
##### Department_dto	
find all departments with average salary	
```	
curl --request GET 'http://localhost:8080/department_avg'	
```
##### Department	
find all departments	
```	
curl --request GET 'http://localhost:8080/department'	
```
find department by id	
```	
curl --request GET 'http://localhost:8080/department/1'	
```
create department	
```	
curl --request POST 'http://localhost:8080/department' \	
--data-raw '{	
    "name": "IT"	
}'	
```
update department by id	
```	
curl --request PUT 'http://localhost:8080/department/1' \	
--data-raw '{	
    "name": "Security"	
}'	
```
remove department by id	
```	
curl --request DELETE 'http://localhost:8080/department/1'	
```
##### Employee	
find all employees	
```	
curl --request GET 'http://localhost:8080/employee'	
```
find employee by id	
```	
curl --request GET 'http://localhost:8080/employee/1'	
```
create employee	
```	
curl --request POST 'http://localhost:8080/employee' \	
--data-raw '{	
    "firstName": "Jhon",	
    "lastName": "Smith",	
    "salary": "952.45",	
    "departmentId": "1"	
}'	
```
update employee by id	
```	
curl --request PUT 'http://localhost:8080/employee/1' \	
--data-raw '{	
    "firstName": "Jhon",	
    "lastName": "Smith",	
    "salary": "952.45",	
    "departmentId": "1"	
}'	
```
remove employee by id	
```	
curl --request DELETE 'http://localhost:8080/employee/1'	
```