package com.epam.dao;


import com.epam.model.dto.DepartmentDto;

import java.util.List;

public interface DepartmentDtoDao {

    public List<DepartmentDto> getAllWithAvgSalary();

}
