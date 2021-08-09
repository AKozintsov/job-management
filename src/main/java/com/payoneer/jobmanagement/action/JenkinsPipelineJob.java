package com.payoneer.jobmanagement.action;

import static com.payoneer.jobmanagement.job.JobState.FAILED;
import static com.payoneer.jobmanagement.job.JobState.QUEUED;
import static com.payoneer.jobmanagement.job.JobState.RUNNING;
import static com.payoneer.jobmanagement.job.JobState.SUCCESS;

import org.springframework.stereotype.Component;

import com.payoneer.jobmanagement.exception.JenkinsPipelineJobException;
import com.payoneer.jobmanagement.job.Job;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("JENKINS")
public class JenkinsPipelineJob implements JobAction {

    @Override
    public Job execute(Job job) {

        var jobState = job.getState();

        if (jobState == QUEUED) {
            try {
                jenkinsPipelineAction(job);
            } catch (JenkinsPipelineJobException e) {
                job.setState(FAILED);
            }
        } else if (jobState == RUNNING) {
            job.setState(SUCCESS);
            log.info("job {} in {} state is done", job.getJobType(), job.getState());
        }
        return job;
    }

    private void jenkinsPipelineAction(Job job) throws JenkinsPipelineJobException {
        job.setState(RUNNING);
        try {
            log.info("job {} in {} state performing JENKINS PIPELINE action", job.getJobType(), job.getState());
            Thread.sleep(3000);
        } catch (Exception e) {
            throw new JenkinsPipelineJobException(String.format("Error during job processing, %s", e.getMessage()));
        }
    }
}
