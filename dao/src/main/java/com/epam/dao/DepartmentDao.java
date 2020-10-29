package com.epam.dao;

import com.epam.model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentDao {

    public List<Department> getAll();

    public Optional<Department> getById(Integer id);

    public int update(Department department);

    public void delete(Integer id);

    public int add(Department department);
}
