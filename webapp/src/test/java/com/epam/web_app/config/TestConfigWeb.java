package com.epam.web_app.config;

import com.epam.dao.DepartmentDao;
import com.epam.dao.DepartmentDtoDao;
import com.epam.dao.EmployeeDao;
import com.epam.dao.impl.DepartmentDaoImpl;
import com.epam.dao.impl.DepartmentDtoDaoImpl;
import com.epam.dao.impl.EmployeeDaoImpl;
import com.epam.service.DepartmentDtoServiceImpl;
import com.epam.service.DepartmentServiceImpl;
import com.epam.service.EmployeeServiceImpl;
import com.epam.service_api.DepartmentDtoService;
import com.epam.service_api.DepartmentService;
import com.epam.service_api.EmployeeService;
import com.epam.web_app.DepartmentController;
import com.epam.web_app.EmployeeController;
import com.epam.web_app.validator.DepartmentValidator;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class TestConfigWeb {

    @Bean
    public DepartmentValidator departmentValidator(){
        return new DepartmentValidator();
    }

    @Bean
    public EmployeeController employeeController(){
        return new EmployeeController(departmentService(), employeeService());
    }

    @Bean
    public DepartmentController departmentController(){
        return new DepartmentController(departmentDtoService(), departmentService());
    }

    @Bean
    public DepartmentService departmentService(){
        return new DepartmentServiceImpl(departmentDao());
    }

    @Bean
    public EmployeeService employeeService(){
        return new EmployeeServiceImpl(employeeDao());
    }

    @Bean
    public DepartmentDtoService departmentDtoService(){
        return new DepartmentDtoServiceImpl(departmentDtoDao());
    }

    @Bean
    public DepartmentDao departmentDao(){
        return new DepartmentDaoImpl(jdbcTemplate());
    }

    @Bean
    public DepartmentDtoDao departmentDtoDao(){
        return new DepartmentDtoDaoImpl(jdbcTemplate());
    }

    @Bean
    public EmployeeDao employeeDao(){
        return new EmployeeDaoImpl(jdbcTemplate());
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate(){
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    public DataSource dataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:mem:test_db;MODE=MySQL;DB_CLOSE_DELAY=-1");
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }


}
