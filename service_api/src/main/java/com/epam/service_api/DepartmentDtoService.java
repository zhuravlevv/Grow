package com.epam.service_api;


import com.epam.model.dto.DepartmentDto;

import java.util.List;

public interface DepartmentDtoService {

    public List<DepartmentDto> getAllWithAvgSalary();
}
