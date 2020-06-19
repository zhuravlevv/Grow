package com.epam.impl;

import com.epam.Department;
import com.epam.DepartmentDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

    public Optional<Department> getById(Integer id) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", id);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(selectById, map, new DepartmentMapper()));
    }

    public Department update(Department department, Integer id) throws Exception {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("name", department.getName());
        map.addValue("id", id);
        namedParameterJdbcTemplate.update(update ,map);
        return getById(id).orElseThrow(Exception::new);
    }

    public void delete(Integer id) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", id);
        namedParameterJdbcTemplate.update(delete, map);
    }

    public Department add(Department department) throws Exception {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("name", department.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insert ,map, keyHolder);
        return getById((Integer) keyHolder.getKey()).orElseThrow(Exception::new);
    }

    private static class DepartmentMapper implements RowMapper<Department> {

        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setName(resultSet.getString("name"));
            department.setId(resultSet.getInt("id"));
            return department;
        }
    }
}
