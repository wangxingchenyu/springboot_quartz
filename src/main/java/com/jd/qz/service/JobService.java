package com.jd.qz.service;

import com.jd.qz.job.MyJob;
import com.jd.qz.job.MyJob2;
import com.jd.qz.job.MyJob5;
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
	private String JOB_GROUP_NAME = "GROUP01";
	private String TRIGGER_GROUP_NAME = "GRIGGER_GROUP01";

	public void executeQuartzTask() {
		/**
		 * job
		 * jobDetail
		 * trigger
		 */
		JobKey jobKey = new JobKey("j11", "jgroup1");
		System.out.println("hello quartz");
		JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity(jobKey).build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("t11", "tgroup1").startNow().
				withSchedule(CronScheduleBuilder.cronSchedule("*/2 * * * * ?"))
				.build();
		try {
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			scheduler.shutdown(true);
			count++;
			// scheduler.pauseTrigger();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}


	public void executeQuartzTask1() {

		System.out.println("执行了....................");
		JobKey jobKey = new JobKey("one1", "twop");

		JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity(jobKey).build();

		SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForever(1);
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("t11112", "tgroup12").startNow().
				withSchedule(simpleScheduleBuilder)
				.build();

		try {
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			scheduler.shutdown(true);
			count++;
			// scheduler.pauseTrigger();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public void executeQuartzTask2() {

		JobKey jobKey = new JobKey("one34", "twop");

		JobDetail jobDetail = JobBuilder.newJob(MyJob2.class).withIdentity(jobKey).build();

		SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForever(1).repeatForever();
		Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity("t111123", "tgroup12").startNow().
				withSchedule(simpleScheduleBuilder)
				.build();

		try {
			//scheduler.scheduleJob(jobDetail, trigger);
			//scheduler.start();
			//scheduler.shutdown(true);
			// scheduler.pauseTrigger();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// 暂停服务
	public void pauseJob() {


		TriggerKey triggerKey = new TriggerKey("t11", "tgroup1");

		// scheduler.pauseAll(); 暂停所有的任务
		try {
			scheduler.pauseTrigger(triggerKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	// 重启任务
	public void restartJob() throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey("t11", "tgroup1");
		scheduler.resumeTrigger(triggerKey);
	}


	/**
	 * @param jobName
	 * @Description: 修改一个任务的触发时间(使用默认的任务组名 ， 触发器名 ， 触发器组名)
	 */
	public void modifyJobTime(String jobName, String cron) {
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, JOB_GROUP_NAME);


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


	/**
	 * @param oldJobName ：原任务名
	 * @param jobName
	 * @param jobclass
	 * @param cron
	 * @Description:修改任务，（可以修改任务名，任务类，触发时间） 原理：移除原来的任务，添加新的任务
	 * @date 2018年5月23日 上午9:13:10
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void modifyJob(String oldJobName, String jobName, Class jobclass, String cron, String TRIGGER_GROUP_NAME) {
		/*
		 * removeJob(oldJobName);
		 * addJob(jobName, jobclass, cron);
		 * System.err.println("修改任务"+oldJobName);
		 */
		TriggerKey triggerKey = TriggerKey.triggerKey(oldJobName, TRIGGER_GROUP_NAME);
		JobKey jobKey = JobKey.jobKey(oldJobName, JOB_GROUP_NAME);
		try {
			Trigger trigger = (Trigger) scheduler.getTrigger(triggerKey);
			if (trigger == null) {
				return;
			}
			scheduler.pauseTrigger(triggerKey);// 停止触发器
			scheduler.unscheduleJob(triggerKey);// 移除触发器
			scheduler.deleteJob(jobKey);// 删除任务
			System.err.println("移除任务:" + oldJobName);

			JobDetail job = JobBuilder.newJob(jobclass).withIdentity(jobName, JOB_GROUP_NAME).build();
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
			// 按新的cronExpression表达式构建一个新的trigger
			Trigger newTrigger = TriggerBuilder.newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME)
					.withSchedule(scheduleBuilder).build();

			// 交给scheduler去调度
			scheduler.scheduleJob(job, newTrigger);

			// 启动
			if (!scheduler.isShutdown()) {
				scheduler.start();
				System.err.println("添加新任务:" + jobName);
			}
			System.err.println("修改任务【" + oldJobName + "】为:" + jobName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}


	/**
	 * @param triggerName
	 * @param triggerGroupName
	 * @Description: 修改一个任务的触发时间
	 */
	public void modifyJobTime1(String triggerName, String triggerGroupName, String cron) {
		TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
		try {

			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(cron)) {
				// trigger已存在，则更新相应的定时设置
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
				// 按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
				// 按新的trigger重新设置job执行
				scheduler.resumeTrigger(triggerKey);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param jobName
	 * @param jobGroupName
	 * @Description:暂停一个任务
	 */
	public void pauseJob(String jobName, String jobGroupName) {
		JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
		try {
			scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param jobName
	 * @Description:恢复一个任务(使用默认组名)
	 */
	public void resumeJob(String jobName) {
		JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
		try {

			scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param jobName
	 * @param jobGroupName
	 * @Description:恢复一个任务
	 * @date 2018年5月17日 上午9:56:09
	 */
	public void resumeJob(String jobName, String jobGroupName) {
		JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
		try {
			scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description:启动所有定时任务
	 */
	public void startJobs() {
		try {
			scheduler.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description 关闭所有定时任务
	 */
	public void shutdownJobs() {
		try {

			if (!scheduler.isShutdown()) {
				scheduler.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * @param jobName
	 * @Description: 立即运行任务，这里的立即运行，只会运行一次，方便测试时用。
	 * @date 2018年5月17日 上午10:03:26
	 */
	public void triggerJob1(String jobName) {
		JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
		try {
			scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param jobName 触发器名
	 * @Description: 获取任务状态
	 * NONE: 不存在
	 * NORMAL: 正常
	 * PAUSED: 暂停
	 * COMPLETE:完成
	 * ERROR : 错误
	 * BLOCKED : 阻塞
	 * @date 2018年5月21日 下午2:13:45
	 */
	public String getTriggerState(String jobName) {
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
		String name = null;
		try {
			Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
			name = triggerState.name();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return name;
	}


	/**
	 * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名,立即执行
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void addJob() {
		try {
			String jobName = "newJobName";
			JobDetail jobDetail = JobBuilder.newJob(MyJob5.class).withIdentity(jobName, JOB_GROUP_NAME).build();
			// 表达式调度构建器
			// CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
			SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForever(2).repeatForever();
			// 按新的cronExpression表达式构建一个新的trigger
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME)
					.withSchedule(simpleScheduleBuilder).build();

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


}
