package com.epam.model.dto;

import java.math.BigDecimal;

public class DepartmentDto {

    private Integer id;

    private String name;

    private BigDecimal averageSalary;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(BigDecimal averageSalary) {
        this.averageSalary = averageSalary;
    }

    public DepartmentDto() {
    }

    public DepartmentDto(String name, BigDecimal averageSalary) {
        this.name = name;
        this.averageSalary = averageSalary;
    }

    @Override
    public String toString() {
        return "DepartmentDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", averageSalary=" + averageSalary +
                '}';
    }
}
