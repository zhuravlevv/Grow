package com.epam.dao;

import com.epam.model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentDao {

    public List<Department> findAll();

    public Optional<Department> findById(Integer id);

    public int update(Department department);

    public void deleteById(Integer id);

    public Department save(Department department);
}
