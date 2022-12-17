package com.csvtransform.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    @Autowired
    public JobCompletionNotificationListener() {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {

    }
}