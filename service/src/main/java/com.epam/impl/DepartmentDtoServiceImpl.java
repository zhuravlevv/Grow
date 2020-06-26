package com.epam.impl;

import com.epam.DepartmentDtoDao;
import com.epam.DepartmentDtoService;
import com.epam.dto.DepartmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

public class DepartmentDtoServiceImpl implements DepartmentDtoService {

    private final static Logger LOGGER = LoggerFactory.getLogger(DepartmentDtoService.class);

    private final DepartmentDtoDao departmentDtoDao;

    public DepartmentDtoServiceImpl(DepartmentDtoDao departmentDtoDao) {
        this.departmentDtoDao = departmentDtoDao;
    }

    @Override
    public List<DepartmentDto> getAllWithAvgSalary() {
        LOGGER.trace("Get all DepartmentsDto with average salary");
        return departmentDtoDao.getAllWithAvgSalary();
    }
}
