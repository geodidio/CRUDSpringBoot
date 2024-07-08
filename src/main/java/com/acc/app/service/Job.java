package com.acc.app.service;


import com.acc.app.dto.request.OrgJobRequest;
import com.acc.app.dto.response.JobResponse;
import com.acc.app.exeption.NotFoundException;

import java.util.List;

public interface Job {
    void addJob(OrgJobRequest employees) throws Exception;
    List<JobResponse> getJobs() throws NotFoundException;
    com.acc.app.domain.Job updateJob(int jobId, com.acc.app.domain.Job jobDetails) throws NotFoundException;
    void deleteJob(int id) throws NotFoundException;
}
