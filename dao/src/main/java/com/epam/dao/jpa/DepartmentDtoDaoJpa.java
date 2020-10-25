package com.epam.dao.jpa;

import com.epam.model.dto.DepartmentDto;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("jpa")
@Repository("departmentDtoDao")
public interface DepartmentDtoDaoJpa extends CrudRepository<DepartmentDto, Integer> {

    public List<DepartmentDto> getAll();
}
