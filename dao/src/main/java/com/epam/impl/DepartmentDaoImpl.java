package com.epam.impl;

import com.epam.Department;
import com.epam.DepartmentDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentDaoImpl implements DepartmentDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${department.selectAll.orderByName}")
    private String selectAll;

    @Value("${department.insert}")
    private String insert;

    @Value("${department.selectById}")
    private String selectById;

    @Value("${department.update}")
    private String update;

    @Value("${department.delete}")
    private String delete;

    public DepartmentDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Department> getAll() {
        return namedParameterJdbcTemplate.query(selectAll, new DepartmentMapper());
    }

    public Department getById(Integer id) {
        return null;
    }

    public void update(Department department, Integer id) {

    }

    public void delete(Integer id) {

    }

    public Department add(Department department) {
        return null;
    }

    private class DepartmentMapper implements RowMapper<Department> {

        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setName(resultSet.getString("name"));
            department.setId(resultSet.getInt("id"));
            return department;
        }
    }
}
