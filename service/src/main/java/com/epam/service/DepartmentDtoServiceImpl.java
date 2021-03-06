package com.epam.service;

import com.epam.dao.DepartmentDtoDao;
import com.epam.model.dto.DepartmentDto;
import com.epam.service_api.DepartmentDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
