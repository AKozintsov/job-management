package com.payoneer.jobmanagement;

import com.payoneer.jobmanagement.job.Job;
import com.payoneer.jobmanagement.job.JobPriority;
import com.payoneer.jobmanagement.job.JobState;
import com.payoneer.jobmanagement.job.JobType;

public class TestData {

    public static Job createJob(JobState jobState, JobType jobType, JobPriority jobPriority) {
        return Job.builder()
                  .state(jobState)
                  .jobType(jobType)
                  .jobPriority(jobPriority)
                  .build();
    }
}
