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

    @Query("select e from Employee  e")
    public List<Employee> getAll();

    @Query("select e from Employee e where e.id=:#{#id}")
    public Optional<Employee> getById(@Param("id") Integer id);

    @Modifying
    @Query(value = "insert into employee (first_name, last_name, salary, department_id) values" +
            " (:#{#employee.firstName}, :#{#employee.lastName}, :#{#employee.salary}, :#{#employee.departmentId})"
            , nativeQuery = true)
    public int add(@Param("employee") Employee employee);

    @Modifying
    @Query(value = "update employee set first_name = :#{#employee.firstName}, last_name=:#{#employee.lastName}, " +
            " salary = :#{#employee.salary}, department_id=:#{#employee.departmentId} " +
            " where id = :#{#employee.id}", nativeQuery = true)
    public int update(@Param("employee") Employee employee);

    @Modifying
    @Query("delete from Employee  e where e.id=:#{#id}")
    public void delete(@Param("id") Integer id);

    @Query("select e from Employee e where e.departmentId=:#{#id}")
    public List<Employee> getByDepartmentId(@Param("id") Integer id);
}
