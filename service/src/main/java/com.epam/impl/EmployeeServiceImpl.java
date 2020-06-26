package com.epam.impl;

import com.epam.EmployeeDao;

public class EmployeeServiceImpl {

    private final EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }
}
