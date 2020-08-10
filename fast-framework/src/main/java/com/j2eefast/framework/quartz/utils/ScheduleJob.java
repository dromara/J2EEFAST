//package com.j2eefast.framework.quartz.utils;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
//import com.j2eefast.common.core.manager.AsyncManager;
//import com.j2eefast.common.core.utils.BeanUtil;
//import com.j2eefast.common.core.utils.SpringUtil;
//import com.j2eefast.framework.manager.factory.AsyncFactory;
//import com.j2eefast.framework.quartz.entity.SysJobEntity;
//import com.j2eefast.framework.quartz.entity.SysJobLogEntity;
//import com.j2eefast.framework.quartz.service.SysJobLogService;
//import org.apache.commons.lang3.StringUtils;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.quartz.QuartzJobBean;
//import com.j2eefast.framework.utils.Constant;
//
///**
// * 定时任务
// */
//public class ScheduleJob extends QuartzJobBean {
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	private ExecutorService service = Executors.newSingleThreadExecutor();
//
//
//	@Override
//	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
//
//		SysJobEntity sysJob = new SysJobEntity();
//
//		//修改通过拷贝类实现
//		BeanUtil.copyBeanProp(sysJob, context.getMergedJobDataMap().get(SysJobEntity.JOB_PARAM_KEY));
//
//		// 数据库保存执行记录
//		SysJobLogEntity log = new SysJobLogEntity();
//		log.setJobId(sysJob.getJobId());
//		log.setBeanName(sysJob.getBeanName());
//		log.setMethodName(sysJob.getMethodName());
//		log.setParams(sysJob.getParams());
//		log.setJobName(sysJob.getJobName());
//		log.setJobGroup(sysJob.getJobGroup());
//		// 任务开始时间
//		long startTime = System.currentTimeMillis();
//
//		try {
//			// 执行任务
//			logger.info("任务准备执行，任务ID：" + sysJob.getJobId());
//			ScheduleRunnable task = new ScheduleRunnable(sysJob.getBeanName(), sysJob.getMethodName(),
//					sysJob.getParams());
//			Future<?> future = service.submit(task);
//
//			future.get();
//
//			// 任务执行总时长
//			long times = System.currentTimeMillis() - startTime;
//			log.setTimes((int) times);
//			// 任务状态 0：成功 1：失败
//			log.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
//
//			logger.info("任务执行完毕，任务ID：" + sysJob.getJobId() + "  总共耗时：" + times + "毫秒");
//		} catch (Exception e) {
//			logger.error("任务执行失败，任务ID：" + sysJob.getJobId(), e);
//
//			// 任务执行总时长
//			long times = System.currentTimeMillis() - startTime;
//			log.setTimes((int) times);
//
//			// 任务状态 0：成功 1：失败
//			log.setStatus(Constant.ScheduleStatus.PAUSE.getValue());
//			log.setError(StringUtils.substring(e.toString(), 0, 2000));
//		}finally {
//			// 保存数据库
//			//AsyncManager.me().execute(AsyncFactory.jobLog(log));
//			SpringUtil.getBean(SysJobLogService.class).save(log);
//		}
//		//
//
//	}
//}
