package com.epam;

import com.epam.dto.DepartmentDto;

import java.util.List;

public interface DepartmentDtoService {

    public List<DepartmentDto> getAllWithAvgSalary();
}
