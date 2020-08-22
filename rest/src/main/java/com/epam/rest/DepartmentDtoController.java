package com.epam.rest;

import com.epam.model.dto.DepartmentDto;
import com.epam.service_api.DepartmentDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentDtoController {

    private final DepartmentDtoService departmentDtoService;

    private final Logger LOGGER = LoggerFactory.getLogger(DepartmentDtoController.class);

    public DepartmentDtoController(DepartmentDtoService departmentDtoService) {
        this.departmentDtoService = departmentDtoService;
    }

    @GetMapping("/department_avg")
    public List<DepartmentDto> getAll(){
        LOGGER.trace("getAll()");
        return departmentDtoService.getAllWithAvgSalary();
    }
}
