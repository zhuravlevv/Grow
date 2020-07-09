package com.epam.rest.exception;

public class EmployeeNotFoundException extends RuntimeException{

    public EmployeeNotFoundException(Integer id) {
        super("Employee id not found : " + id);
    }

}
