package com.epam.service.exception;

public class DepartmentNotFoundException extends RuntimeException {

    public DepartmentNotFoundException(Integer id) {
        super("Department id not found : " + id);
    }

}
