package com.acc.app.dto.response;


import com.acc.app.domain.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobResponse implements Comparable<JobResponse> {

    private Integer id;
    private String name;
    private double salary;
    private List<EmployeeResponse> employees;

    public JobResponse(Integer id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Override
    public int compareTo(JobResponse other) {
        return this.name.compareTo(other.name);
    }
}