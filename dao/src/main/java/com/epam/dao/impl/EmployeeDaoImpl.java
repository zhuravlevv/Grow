package com.epam.dao.impl;

import com.epam.dao.EmployeeDao;
import com.epam.dao.annotations.InjectSql;
import com.epam.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Profile("jdbc")
@Repository("employeeDao")
public class EmployeeDaoImpl implements EmployeeDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDaoImpl.class);

    @InjectSql("sql/employee/findAll.sql")
    private String selectAll;

    @InjectSql("sql/employee/findById.sql")
    private String selectById;

    @InjectSql("sql/employee/insert.sql")
    private String insert;

    @InjectSql("sql/employee/update.sql")
    private String update;

    @InjectSql("sql/employee/delete.sql")
    private String delete;

    @InjectSql("sql/employee/findByDepartmentId.sql")
    private String selectByDepartmentId;


    public EmployeeDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Employee> findAll() {
        LOGGER.trace("Get all employees");
        return namedParameterJdbcTemplate.query(selectAll, new EmployeeMapper());
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        LOGGER.trace("Get employee with id = {}", id);
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id",id);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(namedParameterJdbcTemplate.query(selectById,map,new EmployeeMapper())));
    }

    @Override
    public Employee save(Employee employee) {
        LOGGER.trace("Add employee: {}", employee);
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("first_name", employee.getFirstName());
        map.addValue("last_name", employee.getLastName());
        map.addValue("department_id", employee.getDepartmentId());
        map.addValue("salary", employee.getSalary());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insert, map, keyHolder);
        Number key = keyHolder.getKey();
        assert key != null;
        return findById(key.intValue()).get();
    }

    @Override
    public int update(Employee employee) {
        LOGGER.trace("Update employee {}", employee);
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("first_name", employee.getFirstName());
        map.addValue("last_name", employee.getLastName());
        map.addValue("salary", employee.getSalary());
        map.addValue("department_id", employee.getDepartmentId());
        map.addValue("id", employee.getId());
        namedParameterJdbcTemplate.update(update, map);
        return employee.getId();
    }

    @Override
    public void deleteById(Integer id) {
        LOGGER.trace("Delete employee with id: {}", id);
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", id);
        namedParameterJdbcTemplate.update(delete, map);
    }

    @Override
    public List<Employee> findByDepartmentId(Integer id) {
        LOGGER.trace("Get all by department id");
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("department_id", id);
        return namedParameterJdbcTemplate.query(selectByDepartmentId,
                map, new EmployeeMapper());
    }

    private class EmployeeMapper implements RowMapper<Employee>{

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
