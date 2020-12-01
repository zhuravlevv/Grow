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

    @Modifying
    @Query(value = "update Department d set d.name = :#{#newDepartment.name} where d.id = :#{#newDepartment.id}")
    public int update(@Param("newDepartment") Department department) ;

}
