package com.epam.dao.jpa;

import com.epam.dao.EmployeeDao;
import com.epam.model.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Profile("jpa")
@Repository("employeeDao")
public interface EmployeeDaoJpa extends CrudRepository<Employee, Integer>, EmployeeDao {

    @Modifying
    @Query(value = "update Employee e set e.firstName = :#{#employee.firstName}, e.lastName=:#{#employee.lastName}, " +
            " e.salary = :#{#employee.salary}, e.departmentId=:#{#employee.departmentId} " +
            " where e.id = :#{#employee.id}")
    public int update(@Param("employee") Employee employee);

}
