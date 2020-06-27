package com.epam.impl;

import com.epam.Department;
import com.epam.DepartmentDao;
import com.epam.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentDao departmentDao;

    public DepartmentServiceImpl(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public List<Department> getAll() {
        LOGGER.trace("getAll()");
        return departmentDao.getAll();
    }

    @Override
    public Optional<Department> getById(Integer id) {
        LOGGER.trace("getById(id:{})", id);
        return departmentDao.getById(id);
    }

    @Override
    public Department add(Department department) throws Exception {
        LOGGER.trace("add(department: {})", department);
        return departmentDao.add(department);
    }

    @Override
    public Department update(Department newDepartment, Integer id) throws Exception {
        LOGGER.trace("update(department: {} , id: {})", newDepartment, id);
        return departmentDao.update(newDepartment, id);
    }

    @Override
    public void delete(Integer id) {
        LOGGER.trace("delete (id: {})", id);
        departmentDao.delete(id);
    }
}
