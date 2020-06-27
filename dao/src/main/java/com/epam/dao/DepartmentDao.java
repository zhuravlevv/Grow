package com.epam.dao;

import com.epam.model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentDao {

    List<Department> getAll();

    Optional<Department> getById(Integer id);

    Department update(Department department , Integer id) throws Exception;

    void delete(Integer id);

    Department add(Department department) throws Exception;
}
