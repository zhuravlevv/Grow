package com.epam.dao.impl;

import com.epam.dao.EmployeeDao;
import com.epam.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class EmployeeDaoImpl implements EmployeeDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDaoImpl.class);

    @Value("${employee.selectAll.orderByLastNameAndFirstName}")
    private String selectAll;

    @Value("${employee.selectById}")
    private String selectById;

    @Value("${employee.insert}")
    private String insert;

    @Value("${employee.update}")
    private String update;

    @Value("${employee.delete}")
    private String delete;

    @Value("{employee.selectByDepartmentId}")
    private String selectByDepartmentId;

    public EmployeeDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Employee> getAll() {
        LOGGER.trace("Get all employees");
        return namedParameterJdbcTemplate.query(selectAll, new EmployeeMapper());
    }

    @Override
    public Optional<Employee> getById(Integer id) {
        LOGGER.trace("Get employee with id = {}", id);
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id",id);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(namedParameterJdbcTemplate.query(selectById,map,new EmployeeMapper())));
    }

    @Override
    public Employee add(Employee employee) throws Exception {
        LOGGER.trace("Add employee: {}", employee);
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("first_name", employee.getFirstName());
        map.addValue("last_name", employee.getLastName());
        map.addValue("department_id", employee.getDepartmentId());
        map.addValue("salary", employee.getSalary());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insert, map, keyHolder);
        return getById((Integer)keyHolder.getKey()).orElseThrow(Exception::new);
    }

    @Override
    public Employee update(Employee employee, Integer id) throws Exception {
        LOGGER.trace("Update employee with id: {} to {}", id, employee);
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("first_name", employee.getFirstName());
        map.addValue("last_name", employee.getLastName());
        map.addValue("salary", employee.getSalary());
        map.addValue("department_id", employee.getDepartmentId());
        map.addValue("id", id);
        namedParameterJdbcTemplate.update(update, map);
        return getById(id).orElseThrow(Exception::new);
    }

    @Override
    public void delete(Integer id) {
        LOGGER.trace("Delete employee with id: {}", id);
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", id);
        namedParameterJdbcTemplate.update(delete, map);
    }

    @Override
    public List<Employee> getByDepartmentId(Integer id) {
        LOGGER.trace("Get all by department id");
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("department_id", id);
        return namedParameterJdbcTemplate.query("SELECT * FROM employee WHERE department_id = :department_id",
                map, new EmployeeMapper());
    }

    private static class EmployeeMapper implements RowMapper<Employee>{

        @Override
        public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
            Employee employee = new Employee();
            employee.setId(resultSet.getInt("id"));
            employee.setFirstName(resultSet.getString("first_name"));
            employee.setLastName(resultSet.getString("last_name"));
            employee.setSalary(resultSet.getDouble("salary"));
            employee.setDepartmentId(resultSet.getInt("department_id"));
            return employee;
        }
    }
}
