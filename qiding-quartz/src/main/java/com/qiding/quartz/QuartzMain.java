package com.qiding.quartz;

import java.sql.Time;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.print.attribute.standard.JobName;
import javax.swing.text.DateFormatter;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;
import org.quartz.impl.StdSchedulerFactory;

import com.qiding.quartz.job.MyJob;
import com.qiding.quartz.listener.MyJobListener;
import com.qiding.quartz.listener.MyTriggerListener;

/**
 * Hello world!
 */
public class QuartzMain {
    public static void main(String[] args) throws SchedulerException, InterruptedException {


        Long currentTime = Instant.now().toEpochMilli();

        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());

        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(dateTime));


        System.out.println(new Date());
        //1.factoryName 不一样
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.getListenerManager().addTriggerListener(new MyTriggerListener("qiding"));
        scheduler.getListenerManager().addJobListener(new MyJobListener("xxxx"));
        scheduler.start();
        //任务结束

        JobKey jobKey = new JobKey("qi");
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                .withDescription("job 名称")
                .withIdentity(jobKey)
                .storeDurably()
                .build();

        TriggerKey triggerKey = new TriggerKey("qi");
        Trigger trigger = TriggerBuilder.newTrigger()
                .withDescription("trigger 名称")
                .startAt(new Date(currentTime + 2000))
                .withIdentity(triggerKey)
                .forJob(jobKey)
                .build();

        scheduler.addJob(jobDetail, true);
        scheduler.scheduleJob(trigger);





        TimeUnit.SECONDS.sleep(10);

        System.out.println("Hello World!");
    }
}
