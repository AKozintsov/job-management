package com.payoneer.jobmanagement.action;

import com.payoneer.jobmanagement.job.Job;

public interface JobAction {

    Job execute(Job job);

}
