package com.epam;


import com.epam.dao.DepartmentDtoDao;
import com.epam.model.dto.DepartmentDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath:dao.xml"})
public class DepartmentDtoDaoImplTest {

    @Autowired
    private DepartmentDtoDao departmentDtoDao;

    @Test
    public void getAll(){
        List<DepartmentDto> list = departmentDtoDao.getAllWithAvgSalary();
        Assert.assertNotNull(list);
    }

}
