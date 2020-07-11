package com.epam.dao.impl;

import com.epam.dao.DepartmentDao;
import com.epam.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DepartmentDaoImpl implements DepartmentDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDaoImpl.class);

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
        LOGGER.trace("Get all departments");
        return namedParameterJdbcTemplate.query(selectAll, new DepartmentMapper());
    }

    public Optional<Department> getById(Integer id) {
        LOGGER.trace("Get department with id = {}", id);
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", id);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(namedParameterJdbcTemplate.query(selectById, map, new DepartmentMapper())));
    }

    public Department update(Department department, Integer id) throws Exception {
        LOGGER.trace("Update department with id: {} to {}", id, department);
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("name", department.getName());
        map.addValue("id", id);
        namedParameterJdbcTemplate.update(update ,map);
        return getById(id).orElseThrow(Exception::new);
    }

    public void delete(Integer id) {
        LOGGER.trace("Delete department with id: {}", id);
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", id);
        namedParameterJdbcTemplate.update(delete, map);
    }

    public Department add(Department department) throws Exception {
        LOGGER.trace("Add department: {}", department);
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("name", department.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insert ,map, keyHolder);
        Number number = keyHolder.getKey();
        assert number != null;
        Integer key = number.intValue();
        return getById(key).orElseThrow(Exception::new);
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
