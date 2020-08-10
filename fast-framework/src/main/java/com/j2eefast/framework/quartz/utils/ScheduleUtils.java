package com.j2eefast.framework.quartz.utils;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.framework.quartz.entity.SysJobEntity;
import com.j2eefast.framework.utils.Constant;

/**
 * 定时任务工具类
 */
public class ScheduleUtils {
	 private final static Logger logger =
		 LoggerFactory.getLogger(ScheduleUtils.class);
//	private final static String JOB_NAME = "TASK_";


	/**
	 * 获取触发器key
	 */
	public static TriggerKey getTriggerKey(Long jobId, String jobGroup){
		return TriggerKey.triggerKey(JobInvokeUtil.TASK_CLASS_NAME + jobId, jobGroup);
	}

	/**
	 * 得到quartz任务类
	 *
	 * @param scheduleJob 执行计划
	 * @return 具体执行任务类
	 */
	private static Class<? extends Job> getQuartzJobClass(SysJobEntity scheduleJob){
		boolean isConcurrent = "0".equals(scheduleJob.getConcurrent());
		return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
	}

	/**
	 * 构建任务键对象
	 */
	public static JobKey getJobKey(Long jobId, String jobGroup){
		return JobKey.jobKey(JobInvokeUtil.TASK_CLASS_NAME  + jobId, jobGroup);
	}

//	/**
//	 * 获取表达式触发器
//	 */
//	public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
//		try {
//			return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
//		} catch (SchedulerException e) {
//			logger.error("报错:",e);
//			throw new RxcException("获取定时任务CronTrigger出现异常", e);
//		}
//	}

	/**
	 * 创建定时任务
	 */
	public static void createScheduleJob(Scheduler scheduler, SysJobEntity scheduleJob) {
		try {
			Class<? extends Job> jobClass = getQuartzJobClass(scheduleJob);
			// 构建job信息
			Long jobId = scheduleJob.getId();
			String jobGroup = scheduleJob.getJobGroup();
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId, jobGroup)).build();

			// 表达式调度构建器
			CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
			cronScheduleBuilder = handleCronScheduleMisfirePolicy(scheduleJob, cronScheduleBuilder);

			// 按新的cronExpression表达式构建一个新的trigger
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(jobId, jobGroup))
					.withSchedule(cronScheduleBuilder).build();

			// 放入参数，运行时的方法可以获取
			jobDetail.getJobDataMap().put(JobInvokeUtil.TASK_PROPERTIES, scheduleJob);

			// 判断是否存在
			if (scheduler.checkExists(getJobKey(jobId, jobGroup))){
				// 防止创建时存在数据问题 先移除，然后在执行创建操作
				scheduler.deleteJob(getJobKey(jobId, jobGroup));
			}

			scheduler.scheduleJob(jobDetail, trigger);

			// 暂停任务
			if (scheduleJob.getStatus().equals(Constant.ScheduleStatus.PAUSE.getValue())){
				scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
			}
		} catch (Exception e) {
			throw new RxcException("创建定时任务失败", e);
		}
	}

//	/**
//	 * 更新定时任务
//	 */
//	public static void updateScheduleJob(Scheduler scheduler, SysJobEntity scheduleJob) {
//		try {
//			TriggerKey triggerKey = getTriggerKey(scheduleJob.getJobId());
//
//			// 表达式调度构建器
//			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
//					.withMisfireHandlingInstructionDoNothing();
//
//			CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getJobId());
//
//			// 按新的cronExpression表达式重新构建trigger
//			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
//
//
//			// 参数
//			trigger.getJobDataMap().put(SysJobEntity.JOB_PARAM_KEY, scheduleJob);
//
//
//			scheduler.rescheduleJob(triggerKey, trigger);
//
//			// 暂停任务
//			if (scheduleJob.getStatus() == Constant.ScheduleStatus.PAUSE.getValue()) {
//				pauseJob(scheduler, scheduleJob.getJobId());
//			}
//
//		} catch (SchedulerException e) {
//			logger.error("更新定时任务失败", e);
//		}
//	}
//
//	/**
//	 * 立即执行任务
//	 */
//	public static void run(Scheduler scheduler, SysJobEntity scheduleJob) {
//		try {
//			// 参数
//			JobDataMap dataMap = new JobDataMap();
//			dataMap.put(SysJobEntity.JOB_PARAM_KEY, scheduleJob);
//
//			scheduler.triggerJob(getJobKey(scheduleJob.getJobId()), dataMap);
//		} catch (SchedulerException e) {
//			logger.error("立即执行定时任务失败", e);
//		}
//	}
//
//	/**
//	 * 暂停任务
//	 */
//	public static void pauseJob(Scheduler scheduler, Long jobId) {
//		try {
//			scheduler.pauseJob(getJobKey(jobId));
//		} catch (SchedulerException e) {
//			logger.error("暂停定时任务失败", e);
//		}
//	}
//
//	/**
//	 * 恢复任务
//	 */
//	public static void resumeJob(Scheduler scheduler, Long jobId) {
//		try {
//			scheduler.resumeJob(getJobKey(jobId));
//		} catch (SchedulerException e) {
//			logger.error("暂停定时任务失败", e);
//		}
//	}
//
//	/**
//	 * 删除定时任务
//	 */
//	public static void deleteScheduleJob(Scheduler scheduler, Long jobId) {
//		try {
//			scheduler.deleteJob(getJobKey(jobId));
//		} catch (SchedulerException e) {
//			logger.error("删除定时任务失败", e);
//		}
//	}

	/**
	 * 设置定时任务策略
	 */
	public static CronScheduleBuilder handleCronScheduleMisfirePolicy(SysJobEntity job, CronScheduleBuilder cb)
			throws Exception{
		switch (job.getMisfirePolicy())
		{
			case JobInvokeUtil.MISFIRE_DEFAULT:
				return cb;
			case JobInvokeUtil.MISFIRE_IGNORE_MISFIRES:
				return cb.withMisfireHandlingInstructionIgnoreMisfires();
			case JobInvokeUtil.MISFIRE_FIRE_AND_PROCEED:
				return cb.withMisfireHandlingInstructionFireAndProceed();
			case JobInvokeUtil.MISFIRE_DO_NOTHING:
				return cb.withMisfireHandlingInstructionDoNothing();
			default:
				throw new Exception("The task misfire policy '" + job.getMisfirePolicy()
						+ "' cannot be used in cron schedule tasks");
		}
	}
}
