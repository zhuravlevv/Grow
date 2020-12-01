package com.epam;

import com.epam.config.TestConfigService;
import com.epam.dao.DepartmentDtoDao;
import com.epam.model.dto.DepartmentDto;
import com.epam.service.DepartmentDtoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.util.Assert.notNull;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {TestConfigService.class})
public class DepartmentDtoServiceImplMockTest {

    @InjectMocks
    private DepartmentDtoServiceImpl departmentDtoService;

    @Mock
    private DepartmentDtoDao departmentDtoDao;

    @Test
    public void getAll(){
        Mockito.when(departmentDtoDao.findAllWithAvgSalary()).thenReturn(new LinkedList<>());

        List<DepartmentDto> departments = departmentDtoService.getAllWithAvgSalary();
        notNull(departments);
    }

}
