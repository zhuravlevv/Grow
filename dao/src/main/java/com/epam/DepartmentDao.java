package com.epam;

import com.epam.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentDao {

    List<Department> getAll();

    Optional<Department> getById(Integer id);

    Department update(Department department , Integer id) throws Exception;

    void delete(Integer id);

    Department add(Department department) throws Exception;
}
