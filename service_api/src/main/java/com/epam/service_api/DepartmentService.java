package com.epam.service_api;

import com.epam.model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    public List<Department> getAll();

    public Optional<Department> getById(Integer id);

    public Department add(Department department) throws Exception;

    public Department update(Department newDepartment) throws Exception;

    public void delete(Integer id);
}
