package com.epam.service.impl;

import com.epam.dao.DepartmentDtoDao;
import com.epam.model.dto.DepartmentDto;
import com.epam.service.DepartmentDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
