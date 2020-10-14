package com.epam;

import com.epam.config.TestConfigDao;
import com.epam.dao.DepartmentDtoDao;
import com.epam.model.dto.DepartmentDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={TestConfigDao.class})
@TestPropertySource(locations = "classpath:dao.properties")
@Sql({"classpath:schema-h2.sql", "classpath:data-h2.sql"})
public class DepartmentDtoDaoImplTest {

    @Autowired
    private DepartmentDtoDao departmentDtoDao;

    @Test
    public void getAll(){
        List<DepartmentDto> list = departmentDtoDao.getAllWithAvgSalary();
        assertNotNull(list);
    }

}
