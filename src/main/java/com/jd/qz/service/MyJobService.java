package com.jd.qz.service;

import com.jd.qz.job.MyJob6;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author: zhilei.wang
 * @Date: 2019/12/23 14:54
 * @Version 1.0
 */
@Service
public class MyJobService {
	@Autowired
	Scheduler scheduler;

	public void doJob(String cron) {
		try {
			String jobName = "newJobName2";
			String jobGroup = "jobGroup03";
			JobDetail jobDetail = JobBuilder.newJob(MyJob6.class).withIdentity(jobName, jobGroup).build();

			CronScheduleBuilder cronScheduleBuilder;
			if (!StringUtils.isEmpty(cron)) {
				cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
			} else {
				cronScheduleBuilder = CronScheduleBuilder.cronSchedule("*/2 * * * * ?");
			}

			// 按新的cronExpression表达式构建一个新的trigger
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup)
					.withSchedule(cronScheduleBuilder).build();

			// 交给scheduler去调度
			scheduler.scheduleJob(jobDetail, trigger);

			// 启动
			if (!scheduler.isShutdown()) {
				scheduler.start();
				System.err.println("添加任务:" + jobName);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	public void modifyJobTime(String cron) {

		String jobName = "newJobName2";
		String jobGroup = "jobGroup03";

		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);


		try {
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			if (trigger == null) {
				// 如果不存在，则直接返回
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(cron)) {
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
				// 按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
				// 按新的trigger重新设置job执行
				scheduler.rescheduleJob(triggerKey, trigger);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}


	}
}
