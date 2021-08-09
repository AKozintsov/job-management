package com.payoneer.jobmanagement.action;

import static com.payoneer.jobmanagement.job.JobState.FAILED;
import static com.payoneer.jobmanagement.job.JobState.QUEUED;
import static com.payoneer.jobmanagement.job.JobState.RUNNING;
import static com.payoneer.jobmanagement.job.JobState.SUCCESS;

import org.springframework.stereotype.Component;

import com.payoneer.jobmanagement.exception.EmailSenderJobException;
import com.payoneer.jobmanagement.job.Job;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("EMAIL")
public class EmailSenderJob implements JobAction {

    @Override
    public Job execute(Job job) {

        var jobState = job.getState();

        if (jobState == QUEUED) {
            try {
                emailAction(job);
            } catch (EmailSenderJobException e) {
                job.setState(FAILED);
            }
        } else if (jobState == RUNNING) {
            job.setState(SUCCESS);
            log.info("job {} in {} state is done", job.getJobType(), job.getState());
        }
        return job;
    }

    private void emailAction(Job job) throws EmailSenderJobException {
        job.setState(RUNNING);
        try {
            log.info("job {} in {} state performing SEND EMAIL action", job.getJobType(), job.getState());
            Thread.sleep(3000);
        } catch (Exception e) {
            throw new EmailSenderJobException(String.format("Error during job processing, %s", e.getMessage()));
        }

    }
}
