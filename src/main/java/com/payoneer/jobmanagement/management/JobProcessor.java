package com.payoneer.jobmanagement.management;

import java.util.Map;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.payoneer.jobmanagement.job.Job;
import com.payoneer.jobmanagement.action.JobAction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobProcessor {

    private final JobService jobService;

    private final Map<String, JobAction> jobActions;

    @Scheduled(fixedDelayString = "${job.delay-ms}")
    public void processJob() {

        Optional.ofNullable(jobService.getJobToProcess()).ifPresentOrElse(
                this::executeJob,
                () -> log.info("All jobs have been processed"));
    }

    private void executeJob(Job job)  {
        var jobAction = jobActions.get(job.getJobType().name());

        log.info("job {} in {} state started processing", job.getJobType(), job.getState());

        jobService.saveJob(jobAction.execute(job));
    }

}
