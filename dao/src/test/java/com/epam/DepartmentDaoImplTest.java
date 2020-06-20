package com.epam;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath:dao.xml"})
public class DepartmentDaoImplTest {

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    public void getAll(){
        List<Department> departments = departmentDao.getAll();
        Assert.assertNotNull(departments);
    }

    @Test
    public void add() throws Exception {
        Department department = new Department();
        department.setName("department1");
        Department resDepartment = departmentDao.add(department);
        Department searchDepartment = departmentDao.getById(resDepartment.getId()).orElseThrow(Exception::new);
        Assert.assertEquals(searchDepartment, resDepartment);
    }

    @Test
    public void getById() throws Exception {
        Department department = new Department();
        department.setName("department");

        Department addedDepartment = departmentDao.add(department);

        Department returnedDepartment = departmentDao.getById(addedDepartment.getId()).orElseThrow(Exception::new);

        Assert.assertEquals(returnedDepartment.getName(), "department");
        Assert.assertEquals(returnedDepartment.getId(), addedDepartment.getId());
    }

    @Test
    public void update() throws Exception {
        Department department1 = new Department();
        department1.setName("department1");

        Department department2 = new Department();
        department2.setName("department2");

        Department addedDepartment = departmentDao.add(department1);
        Department department = departmentDao.update(department2, addedDepartment.getId());

        Assert.assertEquals(department.getName(), "department2");
    }

    @Test
    public void delete() throws Exception {
        Department department = new Department();
        department.setName("department");

        Department addedDepartment = departmentDao.add(department);

        departmentDao.delete(addedDepartment.getId());

        Assert.assertFalse(departmentDao.getById(addedDepartment.getId()).isPresent());
    }

}
