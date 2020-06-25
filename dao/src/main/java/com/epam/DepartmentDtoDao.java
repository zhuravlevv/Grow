package com.epam;

import com.epam.dto.DepartmentDto;

import java.util.List;

public interface DepartmentDtoDao {

    public List<DepartmentDto> getAllWithAvgSalary();

}
