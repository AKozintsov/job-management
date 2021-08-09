package com.payoneer.jobmanagement.management;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import org.springframework.stereotype.Service;

import com.payoneer.jobmanagement.job.Job;
import com.payoneer.jobmanagement.job.JobRepository;
import com.payoneer.jobmanagement.job.JobState;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public Job getJobToProcess() {

        var jobs = jobRepository.findNotFinishedJobs();

        jobs.forEach(job -> {
            if (job.getState() == null) {
                job.setState(JobState.QUEUED);
            }
        });
        saveJobs(jobs);

        var queue = new PriorityQueue<>(Comparator.comparing(Job::getJobPriority));
        queue.addAll(jobs);
        return queue.poll();
    }

    public void saveJob(Job job) {
        jobRepository.save(job);
    }

    public void saveJobs(List<Job> jobs) {
        jobRepository.saveAll(jobs);
    }

}
