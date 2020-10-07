#Grow Project

##Requirements

For building and running the application you need:
- JDK 1.8
- Maven 3

##Build

To build project go to Project folder and execute
```
mvn clean install  
```

##Running the application using Maven Jetty plugin

####To start web using Maven Jetty plugin

```
cd webapp
mvn jetty:run
```

Application will be available at:

```
http://localhost:8081
```

####To start REST using Maven Jetty plugin
```
cd rest
mvn jetty:run
```

Available REST endpoints:

#####Department_dto
find all departments with average salary
```
curl --request GET 'http://localhost:8080/department_avg'
```
#####Department
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
#####Employee
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

##Deploying the application on Tomcat
After
```
mvn clean install    
```
You will have two war-files
```
/rest/target/rest.war
/webapp/target/webapp.war
```
Copy them to your tomcat server in webapp folder. Restart tomcat. If everything is correct you can see the result at:
```
http://localhost:8080/webapp/departments
```