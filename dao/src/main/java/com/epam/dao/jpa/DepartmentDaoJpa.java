package com.epam.dao.jpa;

import com.epam.dao.DepartmentDao;
import com.epam.model.Department;
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
@Repository("departmentDao")
public interface DepartmentDaoJpa extends CrudRepository<Department, Integer>, DepartmentDao {

    @Query("select d from Department d")
    public List<Department> getAll();

    @Query("select d from Department d where d.id = ?1")
    public Optional<Department> getById(@Param("id") Integer id);

    @Modifying
    @Query(value = "update department set name = :#{#newDepartment.name} where id = :#{#newDepartment.id}", nativeQuery = true)
    public int update(@Param("newDepartment") Department department) ;

    @Modifying
    @Query(value = "insert into department (name) values (:#{#department.name})", nativeQuery = true)
    public int add(@Param("department") Department department);

    @Modifying
    @Query("delete from Department d where d.id = :#{#id}")
    public void delete(@Param("id") Integer id);
}
