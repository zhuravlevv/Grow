package com.epam;

import com.epam.config.TestConfigService;
import com.epam.dao.impl.DepartmentDaoImpl;
import com.epam.model.Department;
import com.epam.service.DepartmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {TestConfigService.class})
public class DepartmentServiceImplMockTest {

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Mock
    private DepartmentDaoImpl departmentDao;

    @Test
    public void getAll(){

        Mockito.when(departmentDao.getAll()).thenReturn(new ArrayList<>());
        List<Department> departments = departmentService.getAll();

        assertNotNull(departments);
    }

    @Test
    public void getById() {
        Department department = new Department("department");
        department.setId(1);
        Mockito.when(departmentDao.getById(1)).thenReturn(Optional.of(department));

        Optional<Department> returnedDepartment = departmentService.getById(1);

        try {
            assertEquals(department, returnedDepartment.orElseThrow(Exception::new));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void add(){
        Department department = new Department("department");
        department.setId(1);

        try {
            Mockito.when(departmentDao.add(Mockito.any(Department.class))).thenReturn(department);
            Department returnedDepartment = departmentService.add(department);
            assertEquals(department, returnedDepartment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update(){
        Department department = new Department("department");
        department.setId(1);
        Department newDepartment = new Department("newDepartment");

        try {
            Mockito.when(departmentDao.update(Mockito.any(Department.class), Mockito.anyInt())).thenReturn(newDepartment);
            Department returnedDepartment = departmentService.update(newDepartment, 1);
            assertEquals(newDepartment, returnedDepartment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
