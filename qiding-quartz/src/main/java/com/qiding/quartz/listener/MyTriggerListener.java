package com.qiding.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

/**
 * @author <qiding@kuaishou.com>
 * Created on 2020-10-22
 */
public class MyTriggerListener implements TriggerListener {


    private String name;

    public MyTriggerListener(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        System.out.println(trigger.getDescription()+"==="+context.getJobDetail().getDescription()+"====start");
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        System.out.println("MyTriggerListener.vetoJobExecution");
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        System.out.println("MyTriggerListener.triggerMisfired");
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context,
            CompletedExecutionInstruction triggerInstructionCode) {
        System.out.println("MyTriggerListener.triggerComplete");
    }
}
