DROP TABLE IF EXISTS department;
CREATE TABLE department(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE
);

INSERT into department VALUES (2, 'NAME');

DROP TABLE IF EXISTS employee;
CREATE TABLE employee(
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    department_id INT,
    salary DECIMAL
)