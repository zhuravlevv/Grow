package com.epam;

import com.epam.dao.DepartmentDtoDao;
import com.epam.model.dto.DepartmentDto;
import com.epam.service.DepartmentDtoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DepartmentDtoServiceImplMockTest {

    @InjectMocks
    private DepartmentDtoServiceImpl departmentDtoService;

    @Mock
    private DepartmentDtoDao departmentDtoDao;

    @Test
    public void getAll(){
        Mockito.when(departmentDtoDao.getAllWithAvgSalary()).thenReturn(new LinkedList<>());

        List<DepartmentDto> departments = departmentDtoService.getAllWithAvgSalary();
        Assert.notNull(departments);
    }

}
