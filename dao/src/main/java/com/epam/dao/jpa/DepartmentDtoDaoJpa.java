package com.epam.dao.jpa;

import com.epam.dao.DepartmentDtoDao;
import com.epam.model.dto.DepartmentDto;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("jpa")
@Repository("departmentDtoDao")
public interface DepartmentDtoDaoJpa extends CrudRepository<DepartmentDto, Integer>, DepartmentDtoDao {

    @Query(value = "select d.id as id, d.name as name, avg(e.salary) as average_salary" +
            " from department d left join employee e" +
            " on e.department_id = d.id" +
            " group by d.id, d.name" +
            " order by d.id", nativeQuery = true)
    public List<DepartmentDto> getAllWithAvgSalary();
}
