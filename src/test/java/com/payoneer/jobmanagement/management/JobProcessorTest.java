package com.payoneer.jobmanagement.management;

import static com.payoneer.jobmanagement.job.JobPriority.RARE;
import static com.payoneer.jobmanagement.job.JobState.QUEUED;
import static com.payoneer.jobmanagement.job.JobState.SUCCESS;
import static com.payoneer.jobmanagement.job.JobType.API_CALL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.awaitility.Durations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.payoneer.jobmanagement.TestData;
import com.payoneer.jobmanagement.action.APICallJob;
import com.payoneer.jobmanagement.action.JobAction;

@SpringBootTest
class JobProcessorTest {

    @Autowired
    private JobProcessor jobProcessor;

    @Autowired
    private APICallJob apiCallJob;

    @MockBean
    private JobService jobService;

    @MockBean
    private Map<String, JobAction> jobActions;


    @Test
    void shouldProcessJob_whenStatusQueued_thenStatusSuccess() {

        var job = TestData.createJob(QUEUED, API_CALL, RARE);

        when(jobService.getJobToProcess()).thenReturn(job);
        when(jobActions.get(job.getJobType().name())).thenReturn(apiCallJob);

        await()
                .atMost(Durations.TEN_SECONDS)
                .untilAsserted(() -> assertThat(job.getState()).isEqualTo(SUCCESS));
    }


}
