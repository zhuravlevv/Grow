package com.epam.service;

import com.epam.dao.DepartmentDao;
import com.epam.model.Department;
import com.epam.service.exception.DepartmentNotFoundException;
import com.epam.service.exception.EmployeeNotFoundException;
import com.epam.service.exception.GlobalServiceException;
import com.epam.service_api.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
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
        return departmentDao.findAll();
    }

    @Override
    public Optional<Department> getById(Integer id) {
        LOGGER.trace("getById(id:{})", id);
        return departmentDao.findById(id);
    }

    @Transactional
    @Override
    public Department add(Department department) {
        LOGGER.trace("add(department: {})", department);
        try {
            return departmentDao.save(department);
        }
        catch (DataAccessException e){
            throw new GlobalServiceException("Couldn't save department", e);
        }
    }

    @Transactional
    @Override
    public Department update(Department newDepartment) {
        LOGGER.trace("update(department: {})", newDepartment);
        try {
            departmentDao.update(newDepartment);
            return getById(newDepartment.getId())
                    .orElseThrow(() -> new DepartmentNotFoundException(newDepartment.getId()));
        }
        catch (DataAccessException e){
            throw new GlobalServiceException("Couldn't save department", e);
        }
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        LOGGER.trace("delete (id: {})", id);
        departmentDao.deleteById(id);
    }
}
