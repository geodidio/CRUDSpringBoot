package com.acc.app.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "job", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@Getter
@Setter
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double salary;


    @OneToMany(mappedBy = "job")
    private List<Employee> employees;


    public Job(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public void addEmployees(List<Employee> employees) {
        this.employees = employees;
        for (Employee e : employees) {
            e.setJob(this);
        }
    }


}