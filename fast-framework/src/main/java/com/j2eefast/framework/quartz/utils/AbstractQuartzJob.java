package com.j2eefast.framework.quartz.utils;

import com.j2eefast.common.core.utils.BeanUtil;
import com.j2eefast.common.core.utils.ExceptionUtil;
import com.j2eefast.common.core.utils.SpringUtil;
import com.j2eefast.framework.quartz.entity.SysJobEntity;
import com.j2eefast.framework.quartz.entity.SysJobLogEntity;
import com.j2eefast.framework.quartz.service.SysJobLogService;
import com.j2eefast.framework.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
/**
 * 抽象quartz调用
 *
 * @author ruoyi
 * @author zhouzhou 二次修改
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {

	/**
	 * 线程本地变量
	 */
	private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		SysJobEntity sysJob = new SysJobEntity();
		//修改通过拷贝类实现
		BeanUtil.copyBeanProp(sysJob, context.getMergedJobDataMap().get(JobInvokeUtil.TASK_PROPERTIES));
		try {
			before(context, sysJob);
			if (sysJob != null) {
				doExecute(context, sysJob);
			}
			after(context, sysJob, null);
		}
		catch (Exception e){
			after(context, sysJob, e);
		}
	}

	/**
	 * 执行前
	 *
	 * @param context 工作执行上下文对象
	 * @param sysJob 系统计划任务
	 */
	protected void before(JobExecutionContext context, SysJobEntity sysJob)
	{
		threadLocal.set(new Date());
	}

	/**
	 * 执行后
	 *
	 * @param context 工作执行上下文对象
	 * @param sysJob 系统计划任务
	 */
	protected void after(JobExecutionContext context, SysJobEntity sysJob, Exception e){
		Date startTime = threadLocal.get();
		threadLocal.remove();
		final SysJobLogEntity sysJobLog = new SysJobLogEntity();
		sysJobLog.setJobName(sysJob.getJobName());
		sysJobLog.setJobGroup(sysJob.getJobGroup());
		sysJobLog.setJobId(sysJob.getId());
		sysJobLog.setInvokeTarget(sysJob.getInvokeTarget());
		long runMs = new Date().getTime() - startTime.getTime();
		sysJobLog.setTimes((int) runMs);
		if (e != null) {
			log.error("任务执行失败，任务ID：" + sysJobLog.getJobId(), e);
			sysJobLog.setStatus(Constant.ScheduleStatus.PAUSE.getValue());
			String errorMsg = StringUtils.substring(ExceptionUtil.getExceptionMessage(e), 0, 2000);
			sysJobLog.setError(errorMsg);
		}
		else{
			// 任务状态 0：成功 1：失败
			sysJobLog.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
			sysJobLog.setError("执行成功!");
		}
		// 写入数据库当中
		SpringUtil.getBean(SysJobLogService.class).save(sysJobLog);
	}

	/**
	 * 执行方法，由子类重载
	 *
	 * @param context 工作执行上下文对象
	 * @param sysJob 系统计划任务
	 * @throws Exception 执行过程中的异常
	 */
	protected abstract void doExecute(JobExecutionContext context, SysJobEntity sysJob) throws Exception;
}
