package com.jd.qz.configuration;

import com.jd.qz.job.MyJob3;
import com.jd.qz.job.MyJob4;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 王志雷
 * @Date: 2020/5/22 22:45
 */
@Configuration
public class QuartzConfiguration {
	// 使用jobDetail包装job
	@Bean
	public JobDetail myJobDetail() {
		return JobBuilder.newJob(MyJob3.class).withIdentity("myJob").storeDurably().build();
	}

	// 把jobDetail注册到trigger上去
	@Bean
	public Trigger myJobTrigger() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(15).repeatForever();
		// 简单的任务处理
		return TriggerBuilder.newTrigger()
				.forJob(myJobDetail())
				.withIdentity("myJobTrigger")
				.withSchedule(scheduleBuilder)
				.build();
	}

	// 使用jobDetail包装job
	@Bean
	public JobDetail myCronJobDetail() {
		return JobBuilder.newJob(MyJob4.class).withIdentity("myCronJob").storeDurably().build();
	}

	// 把jobDetail注册到Cron表达式的trigger上去
	@Bean
	public Trigger CronJobTrigger() {
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");

		return TriggerBuilder.newTrigger()
				.forJob(myCronJobDetail())
				.withIdentity("myCronJobTrigger")
				.withSchedule(cronScheduleBuilder)
				.build();
	}















}
