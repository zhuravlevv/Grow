package com.epam.dao.jpa;

import com.epam.model.Department;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("jpa")
@Repository("departmentDao")
public interface DepartmentDaoJpa extends CrudRepository<Department, Integer> {

    public List<Department> getAll();
    public Department getById(Integer id);
    public Department update(Integer id, Department newDepartment);
    public Department create(Department department);
    public void deleteById(Integer id);
}
