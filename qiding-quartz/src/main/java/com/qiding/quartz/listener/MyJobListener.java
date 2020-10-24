package com.qiding.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * @author <qiding@kuaishou.com>
 * Created on 2020-10-22
 */
public class MyJobListener  implements JobListener {
    private String myJobName;

    public MyJobListener(String myJobName) {
        this.myJobName = myJobName;
    }

    @Override
    public String getName() {
        return myJobName;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("MyJobListener.context");

    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("MyJobListener.jobExecutionVetoed");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println("MyJobListener.jobWasExecuted");

    }
}
