package com.payoneer.jobmanagement.action;

import static com.payoneer.jobmanagement.job.JobState.FAILED;
import static com.payoneer.jobmanagement.job.JobState.QUEUED;
import static com.payoneer.jobmanagement.job.JobState.RUNNING;
import static com.payoneer.jobmanagement.job.JobState.SUCCESS;

import org.springframework.stereotype.Component;

import com.payoneer.jobmanagement.exception.APICallJobException;
import com.payoneer.jobmanagement.job.Job;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("API_CALL")
public class APICallJob implements JobAction {

    @Override
    public Job execute(Job job) {

        var jobState = job.getState();

        if (jobState == QUEUED) {
            try {
                apiCallAction(job);
            } catch (APICallJobException e) {
                job.setState(FAILED);
            }
        } else if (jobState == RUNNING) {
            job.setState(SUCCESS);
            log.info("job {} in {} state is done", job.getJobType(), job.getState());
        }
        return job;
    }

    private void apiCallAction(Job job) throws APICallJobException {
        job.setState(RUNNING);
        try {
            log.info("job {} in {} state performing API CALL action", job.getJobType(), job.getState());
            Thread.sleep(3000);
        } catch (Exception e) {
            throw new APICallJobException(String.format("Error during job processing, %s", e.getMessage()));
        }
    }
}
