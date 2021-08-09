package com.payoneer.jobmanagement.management;

import static com.payoneer.jobmanagement.TestData.createJob;
import static com.payoneer.jobmanagement.job.JobPriority.BURNED_OUT;
import static com.payoneer.jobmanagement.job.JobPriority.RARE;
import static com.payoneer.jobmanagement.job.JobPriority.WELL_DONE;
import static com.payoneer.jobmanagement.job.JobState.QUEUED;
import static com.payoneer.jobmanagement.job.JobType.API_CALL;
import static com.payoneer.jobmanagement.job.JobType.EMAIL;
import static com.payoneer.jobmanagement.job.JobType.JENKINS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.payoneer.jobmanagement.job.JobRepository;

@SpringBootTest
class JobServiceTest {

    @Autowired
    private JobService jobService;

    @MockBean
    private JobRepository jobRepository;

    @Test
    void shouldGetJob_whenMultipleJobs_thenGetJobWithHighestPriority() {

        var jobs = List.of(
                createJob(QUEUED, API_CALL, BURNED_OUT),
                createJob(QUEUED, JENKINS, WELL_DONE),
                createJob(QUEUED, EMAIL, RARE));

        when(jobRepository.findNotFinishedJobs()).thenReturn(jobs);

        var highestPriorityJob = jobService.getJobToProcess();

        assertThat(highestPriorityJob.getJobPriority()).isEqualTo(RARE);
    }
}
