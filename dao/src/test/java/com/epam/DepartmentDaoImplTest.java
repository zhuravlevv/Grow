package com.epam;

import com.epam.config.TestConfigDao;
import com.epam.dao.DepartmentDao;
import com.epam.model.Department;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={TestConfigDao.class})
@Sql({"classpath:schema-h2.sql", "classpath:data-h2.sql"})
public class DepartmentDaoImplTest {

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    public void getAll(){
        List<Department> departments = departmentDao.getAll();
        assertNotNull(departments);
    }

    @Test
    public void add() throws Exception {
        Department department = new Department();
        department.setName("department1");
        Department resDepartment = departmentDao.save(department);
        Department searchDepartment = departmentDao.getById(resDepartment.getId()).orElseThrow(Exception::new);
        assertEquals(searchDepartment, resDepartment);
    }

    @Test
    public void getById() throws Exception {
        Department department = new Department();
        department.setName("department");

        Department addedDepartment = departmentDao.save(department);

        Department returnedDepartment = departmentDao.getById(addedDepartment.getId()).orElseThrow(Exception::new);

        assertEquals(returnedDepartment.getName(), "department");
        assertEquals(returnedDepartment.getId(), addedDepartment.getId());
    }

    @Test
    public void update() throws Exception {
        Department department1 = new Department();
        department1.setName("department1");

        Department department2 = new Department();
        department2.setName("department2");

        Department addedDepartment = departmentDao.save(department1);
        department2.setId(addedDepartment.getId());
        int departmentId = departmentDao.update(department2);
        Department department = departmentDao.getById(departmentId).orElseThrow(Exception::new);
        assertEquals(department.getName(), "department2");
    }

    @Test
    public void delete() throws Exception {
        Department department = new Department();
        department.setName("department");

        Department addedDepartment = departmentDao.save(department);

        departmentDao.delete(addedDepartment.getId());

        assertFalse(departmentDao.getById(addedDepartment.getId()).isPresent());
    }

}
