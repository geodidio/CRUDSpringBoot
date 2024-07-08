package com.acc.app.dto.request;

import lombok.Getter;
import java.util.List;

@Getter
public class OrgJobRequest {

    private List<EmployeeRequest> employees;
    private List<JobRequest> jobs;

}
