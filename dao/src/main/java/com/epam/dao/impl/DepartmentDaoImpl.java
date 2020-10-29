package com.epam.dao.impl;

import com.epam.dao.DepartmentDao;
import com.epam.dao.annotations.InjectSql;
import com.epam.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

@Profile("jdbc")
@Repository("departmentDao")
public class DepartmentDaoImpl implements DepartmentDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDaoImpl.class);

    @InjectSql("sql/department/findAll.sql")
    private String selectAll;

    @InjectSql("sql/department/insert.sql")
    private String insert;

    @InjectSql("sql/department/findById.sql")
    private String selectById;

    @InjectSql("sql/department/update.sql")
    private String update;

    @InjectSql("sql/department/delete.sql")
    private String delete;

    @Autowired
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

    public int update(Department department) {
        LOGGER.trace("Update department {}", department);
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("name", department.getName());
        map.addValue("id", department.getId());
        namedParameterJdbcTemplate.update(update ,map);
        return department.getId();
    }

    public void delete(Integer id) {
        LOGGER.trace("Delete department with id: {}", id);
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", id);
        namedParameterJdbcTemplate.update(delete, map);
    }

    public int add(Department department) {
        LOGGER.trace("Add department: {}", department);
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("name", department.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insert ,map, keyHolder);
        Number number = keyHolder.getKey();
        assert number != null;
        return number.intValue();
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
