package com.epam;

import com.epam.Department;
import com.epam.DepartmentDao;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath:dao.xml"})
public class DepartmentDaoImplTest {

    @Autowired
    private DepartmentDao departmentDao;


    @Test
    public void getAll(){
        List<Department> departments = departmentDao.getAll();
        assertNotNull(departments);
    }
    

}
