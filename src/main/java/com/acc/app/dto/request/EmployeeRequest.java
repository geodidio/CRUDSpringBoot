package com.acc.app.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeRequest {

    private String name;
    private String email;
    private String jobName;
    private int age;

}
