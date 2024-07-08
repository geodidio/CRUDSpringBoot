package com.acc.app.service;

import com.acc.app.dto.request.OrgJobRequest;
import com.acc.app.exeption.NotFoundException;

import java.util.List;

public interface Employee {
    void addEmp(OrgJobRequest employees) throws Exception;
    List<com.acc.app.domain.Employee> getEmployeesByJobName(String jobName);

    void deleteEmp(int id) throws NotFoundException;

}
