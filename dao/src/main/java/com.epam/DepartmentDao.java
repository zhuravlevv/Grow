package com.epam;

import java.util.List;

public interface DepartmentDao {

    List<Department> getAll();

    Department getById(Integer id);

    void update(Department department , Integer id);

    void delete(Integer id);

    Department add(Department department);
}
