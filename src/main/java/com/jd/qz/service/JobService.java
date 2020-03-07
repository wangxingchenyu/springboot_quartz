package com.jd.qz.service;

import com.jd.qz.job.MyJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zhilei.wang
 * @Date: 2019/12/23 14:54
 * @Version 1.0
 */
@Service
public class JobService {
    @Autowired
    Scheduler scheduler;
    int count = 0;

    public void executeQuartzTask() {
        JobKey jobKey = new JobKey("j1", "jgroup");
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity(jobKey).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("t1", "tgroup").startNow().
                withSchedule(CronScheduleBuilder.cronSchedule("*/2 * * * * ?"))
                .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
            scheduler.shutdown(true);
            count++;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
