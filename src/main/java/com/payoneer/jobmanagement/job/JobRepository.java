package com.payoneer.jobmanagement.job;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, UUID> {


    List<Job> findAllByStateIsNullOrStateIsIn(List<JobState> states);

    default List<Job> findNotFinishedJobs() {
        return findAllByStateIsNullOrStateIsIn(List.of(JobState.QUEUED, JobState.RUNNING));
    }
}
