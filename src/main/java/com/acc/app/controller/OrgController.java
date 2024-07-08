package com.acc.app.controller;

import com.acc.app.domain.Employee;
import com.acc.app.domain.Job;
import com.acc.app.dto.request.OrgJobRequest;
import com.acc.app.dto.response.JobResponse;
import com.acc.app.exeption.NotFoundException;
import com.acc.app.service.OrgManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acc")
public class OrgController {

    @Autowired
    private OrgManagement OrgManagement;

    @PostMapping("/job")
    public ResponseEntity<String> addJobs(@RequestBody OrgJobRequest request) throws Exception {
        OrgManagement.addJob(request);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    @PostMapping("/emp")
    public ResponseEntity<String> addEmployees(@RequestBody OrgJobRequest request) throws Exception {
        OrgManagement.addEmp(request);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    @GetMapping("/job/{jobName}/employees")
    public ResponseEntity<List<Employee>> getEmployeesByJobName(@PathVariable String jobName) {
        List<Employee> employees = OrgManagement.getEmployeesByJobName(jobName);
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<JobResponse>> getJobs() throws NotFoundException {
        List<JobResponse> jobs = OrgManagement.getJobs();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @PutMapping("/job/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable(value = "id") int jobId, @RequestBody Job jobDetails) throws NotFoundException {
        Job updatedJob = OrgManagement.updateJob(jobId, jobDetails);
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/job/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable(value = "id") int id) throws NotFoundException {
        OrgManagement.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/emp/{id}")
    public ResponseEntity<Void> deleteEmp(@PathVariable(value = "id") int id) throws NotFoundException {
        OrgManagement.deleteEmp(id);
        return ResponseEntity.noContent().build();
    }
}