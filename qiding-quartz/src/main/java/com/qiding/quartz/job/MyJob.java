package com.qiding.quartz.job;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author <qiding@kuaishou.com>
 * Created on 2020-10-22
 */
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        System.out.println(new Date());
    }
}
