package com.epam.dto;

public class DepartmentDto {

    private Integer id;

    private String name;

    private Double averageSalary;

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

    public Double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(Double averageSalary) {
        this.averageSalary = averageSalary;
    }

    public DepartmentDto() {
    }

    public DepartmentDto(String name, Double averageSalary) {
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
