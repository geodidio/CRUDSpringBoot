package com.acc.app.service.impl;

import com.acc.app.domain.Employee;
import com.acc.app.domain.Job;
import com.acc.app.dto.request.EmployeeRequest;
import com.acc.app.dto.request.JobRequest;
import com.acc.app.dto.request.OrgJobRequest;
import com.acc.app.dto.response.EmployeeResponse;
import com.acc.app.dto.response.JobResponse;
import com.acc.app.exeption.BadRequestException;
import com.acc.app.exeption.NotFoundException;
import com.acc.app.exeption.UniqueConstraintViolationException;
import com.acc.app.repository.EmployeeRepository;
import com.acc.app.repository.JobRepository;
import com.acc.app.service.OrgManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.acc.app.util.Constants.*;

@Service
@Slf4j
public class OrgManagementImpl implements OrgManagement {

    @Autowired
    JobRepository jobRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getEmployeesByJobName(String jobName) {
        return employeeRepository.findByJobName(jobName);
    }



    @Override
    public void addJob(OrgJobRequest org) throws Exception {
        log.info(CREATING_JOB);
        try {
            boolean isValidRequest = org.getJobs().stream().anyMatch(e -> e.getName().isEmpty());
            if (isValidRequest) {
                throw new BadRequestException(INVALID_REQUEST);
            }
            setJobs(org);
            log.info(JOB_CREATED);
        } catch (Exception ex) {
            log.error("Error :{}", ex.getMessage());
            throw (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException)
                    ? new UniqueConstraintViolationException(JOB_ALREADY_EXISTS)
                    : ex;
        }
    }

    @Override
    public List<JobResponse> getJobs() throws NotFoundException {
        log.info("Job created");
        List<Job> jobs = jobRepository.findAll();
        if (jobs.isEmpty()) {
            log.info(NO_JOBS_WERE_FOUND);
            throw new NotFoundException(NO_JOBS_WERE_FOUND);
        }
        return jobs.stream().map(e -> {
            ArrayList<EmployeeResponse> EmployeeResponse = new ArrayList<EmployeeResponse>();
            JobResponse jobResponse = new JobResponse(e.getId(), e.getName(), e.getSalary());
            e.getEmployees().forEach(b -> {
                EmployeeResponse.add(new EmployeeResponse(b.getName(), b.getName(), b.getAge()));
            });
            jobResponse.setEmployees(EmployeeResponse);

            return jobResponse;
        }).sorted().toList();


    }

    private void setJobs(OrgJobRequest org) {
        Set<String> duplicatedJob = new HashSet<String>();
        Stream<JobRequest> ignoreDuplicated = org.getJobs().stream().filter(e -> duplicatedJob.add(e.getName()));
        List<Job> mappedJob = ignoreDuplicated.map(e -> {
            return new Job(e.getName(), e.getSalary());
        }).toList();
        jobRepository.saveAll(new ArrayList<>(mappedJob));
        duplicatedJob.clear();
    }

    @Override
    public void addEmp(OrgJobRequest employees) throws Exception {
        log.info("creating job");
        try {

            boolean isValidRequest = employees.getEmployees().stream().anyMatch(e -> e.getName().isBlank());
            if (isValidRequest) {
                throw new BadRequestException(INVALID_REQUEST);
            }
            setEmployees(employees);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    private void setEmployees(OrgJobRequest employees) throws BadRequestException {
        List<Employee> employeesToAdd = new ArrayList<>();
        for (EmployeeRequest employeeRequest : employees.getEmployees()) {
            Job job = jobRepository.findByName(employeeRequest.getJobName());
            if (job == null) {
                throw new BadRequestException(INVALID_JOB);
            }
            Employee employee = new Employee();
            employee.setName(employeeRequest.getName());
            employee.setEmail(employeeRequest.getEmail());
            employee.setAge(employeeRequest.getAge());
            employee.setJob(job);
            employeesToAdd.add(employee);
        }
        employeeRepository.saveAll(employeesToAdd);
        employeesToAdd.clear();
    }

    @Override
    @Transactional
    public Job updateJob(int id, Job jobDetails) throws NotFoundException {
        Job job = jobRepository.findById(id).orElseThrow(() -> new NotFoundException(NO_JOBS_ID + id));
        job.setName(jobDetails.getName());
        job.setSalary(jobDetails.getSalary());
        return jobRepository.save(job);
    }

    @Override
    public void deleteJob(int id) throws NotFoundException {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NO_JOBS_ID + id));
        jobRepository.delete(job);
    }

    @Override
    public void deleteEmp(int id) throws NotFoundException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NO_EMP_ID + id));
        employeeRepository.delete(employee);
    }
}
