package com.acc.app.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponse {

    public EmployeeResponse(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    private Integer id;
    private String name;
    private String email;
    private int age;
}