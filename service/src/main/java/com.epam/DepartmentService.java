package com.epam;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    public List<Department> getAll();

    public Optional<Department> getById(Integer id);

    public Department add(Department department) throws Exception;

    public Department update(Department newDepartment, Integer id) throws Exception;

    public void delete(Integer id);
}
