package com.epam.dao.impl;

import com.epam.dao.DepartmentDtoDao;
import com.epam.model.dto.DepartmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@PropertySource("classpath:dao.properties")
public class DepartmentDtoDaoImpl implements DepartmentDtoDao {

    private Logger LOGGER = LoggerFactory.getLogger(DepartmentDtoDao.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${departmentDto.selectAllWithAvgSalary}")
    private String selectAllWithAvgSalary;

    public DepartmentDtoDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<DepartmentDto> getAllWithAvgSalary() {
        LOGGER.trace("Get all DepartmentDto with average salary");
        return namedParameterJdbcTemplate.query(selectAllWithAvgSalary, new DepartmentDtoMapper());
    }

    private class DepartmentDtoMapper implements RowMapper<DepartmentDto>{

        @Override
        public DepartmentDto mapRow(ResultSet resultSet, int i) throws SQLException {
            DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setId(resultSet.getInt("id"));
            departmentDto.setAverageSalary(BigDecimal.valueOf(resultSet.getDouble("average_salary")));
            departmentDto.setName(resultSet.getString("name"));
            return departmentDto;
        }
    }
}
