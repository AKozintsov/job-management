package com.payoneer.jobmanagement.management;

import static com.payoneer.jobmanagement.job.JobType.*;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.payoneer.jobmanagement.job.Job;
import com.payoneer.jobmanagement.job.JobPriority;
import com.payoneer.jobmanagement.job.JobType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JobManagementStartup {


    private final JobService jobService;

    @PostConstruct
    public void initializeJobs() {
        var apiJobBurnedOut = createJob(API_CALL, JobPriority.BURNED_OUT);

        var apiJobRare = createJob(API_CALL, JobPriority.RARE);

        var emailJobWellDone = createJob(EMAIL, JobPriority.WELL_DONE);

        var jenkinsJobRare = createJob(JENKINS, JobPriority.RARE);

        jobService.saveJobs(List.of(apiJobBurnedOut, apiJobRare, emailJobWellDone, jenkinsJobRare));
    }

    private Job createJob(JobType jobType, JobPriority jobPriority) {
        return Job.builder()
                  .jobType(jobType)
                  .jobPriority(jobPriority)
                  .build();
    }
}
