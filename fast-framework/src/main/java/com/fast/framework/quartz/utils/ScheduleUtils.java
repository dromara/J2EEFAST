package com.fast.framework.quartz.utils;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fast.common.core.exception.RxcException;
import com.fast.framework.quartz.entity.SysJobEntity;
import com.fast.framework.utils.Constant;

/**
 * 定时任务工具类
 */
public class ScheduleUtils {
	 private final static Logger logger =
		 LoggerFactory.getLogger(ScheduleUtils.class);
	private final static String JOB_NAME = "TASK_";

	/**
	 * 获取触发器key
	 */
	public static TriggerKey getTriggerKey(Long jobId) {
		return TriggerKey.triggerKey(JOB_NAME + jobId);
	}

	/**
	 * 获取jobKey
	 */
	public static JobKey getJobKey(Long jobId) {
		return JobKey.jobKey(JOB_NAME + jobId);
	}

	/**
	 * 获取表达式触发器
	 */
	public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
		try {
			return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
		} catch (SchedulerException e) {
			logger.error("报错:",e);
			throw new RxcException("获取定时任务CronTrigger出现异常", e);
		}
	}

	/**
	 * 创建定时任务
	 */
	public static void createScheduleJob(Scheduler scheduler, SysJobEntity scheduleJob) {
		try {
			// 构建job信息
			JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(scheduleJob.getJobId()))
					.build();

			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
					.withMisfireHandlingInstructionDoNothing();

			// 按新的cronExpression表达式构建一个新的trigger
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getJobId()))
					.withSchedule(scheduleBuilder).build();

			// 放入参数，运行时的方法可以获取
			jobDetail.getJobDataMap().put(SysJobEntity.JOB_PARAM_KEY, scheduleJob);

			scheduler.scheduleJob(jobDetail, trigger);

			// 暂停任务
			if (scheduleJob.getStatus() == Constant.ScheduleStatus.PAUSE.getValue()) {
				pauseJob(scheduler, scheduleJob.getJobId());
			}
		} catch (SchedulerException e) {
			throw new RxcException("创建定时任务失败", e);
		}
	}

	/**
	 * 更新定时任务
	 */
	public static void updateScheduleJob(Scheduler scheduler, SysJobEntity scheduleJob) {
		try {
			TriggerKey triggerKey = getTriggerKey(scheduleJob.getJobId());

			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
					.withMisfireHandlingInstructionDoNothing();

			CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getJobId());

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();


			// 参数
			trigger.getJobDataMap().put(SysJobEntity.JOB_PARAM_KEY, scheduleJob);


			scheduler.rescheduleJob(triggerKey, trigger);

			// 暂停任务
			if (scheduleJob.getStatus() == Constant.ScheduleStatus.PAUSE.getValue()) {
				pauseJob(scheduler, scheduleJob.getJobId());
			}

		} catch (SchedulerException e) {
			logger.error("更新定时任务失败", e);
		}
	}

	/**
	 * 立即执行任务
	 */
	public static void run(Scheduler scheduler, SysJobEntity scheduleJob) {
		try {
			// 参数
			JobDataMap dataMap = new JobDataMap();
			dataMap.put(SysJobEntity.JOB_PARAM_KEY, scheduleJob);

			scheduler.triggerJob(getJobKey(scheduleJob.getJobId()), dataMap);
		} catch (SchedulerException e) {
			logger.error("立即执行定时任务失败", e);
		}
	}

	/**
	 * 暂停任务
	 */
	public static void pauseJob(Scheduler scheduler, Long jobId) {
		try {
			scheduler.pauseJob(getJobKey(jobId));
		} catch (SchedulerException e) {
			logger.error("暂停定时任务失败", e);
		}
	}

	/**
	 * 恢复任务
	 */
	public static void resumeJob(Scheduler scheduler, Long jobId) {
		try {
			scheduler.resumeJob(getJobKey(jobId));
		} catch (SchedulerException e) {
			logger.error("暂停定时任务失败", e);
		}
	}

	/**
	 * 删除定时任务
	 */
	public static void deleteScheduleJob(Scheduler scheduler, Long jobId) {
		try {
			scheduler.deleteJob(getJobKey(jobId));
		} catch (SchedulerException e) {
			logger.error("删除定时任务失败", e);
		}
	}
}
