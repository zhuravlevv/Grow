package com.epam.service;

import com.epam.dao.DepartmentDao;
import com.epam.model.Department;
import com.epam.service_api.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
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

    @Transactional
    @Override
    public Department add(Department department) throws Exception {
        LOGGER.trace("add(department: {})", department);
        int id = departmentDao.add(department);
        return getById(id).orElseThrow(Exception::new);
    }

    @Transactional
    @Override
    public Department update(Department newDepartment) throws Exception {
        LOGGER.trace("update(department: {})", newDepartment);
        departmentDao.update(newDepartment);
        return getById(newDepartment.getId()).orElseThrow(Exception::new);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        LOGGER.trace("delete (id: {})", id);
        departmentDao.delete(id);
    }
}
