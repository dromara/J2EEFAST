package com.j2eefast.framework.quartz.service;


import java.util.Arrays;
import java.util.Map;

import com.j2eefast.framework.quartz.utils.JobInvokeUtil;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.quartz.entity.SysJobEntity;
import com.j2eefast.framework.quartz.mapper.SysJobMapper;
import com.j2eefast.framework.quartz.utils.ScheduleUtils;
import com.j2eefast.framework.utils.Constant;
import com.j2eefast.framework.utils.CronUtils;


/**
 * 定时任务服务实现类
 * @author zhouzhou
 * @date 2020-03-08 16:07
 */
@Service
public class SysJobService extends ServiceImpl<SysJobMapper,SysJobEntity>{

	@Autowired
	private Scheduler scheduler;
	/**
	 * 页面展示查询翻页
	 */
	public PageUtil findPage(Map<String, Object> params) {
		String jobName = (String) params.get("jobName");
		String jobGroup = (String) params.get("jobGroup");
		String status = (String) params.get("status");
		Page<SysJobEntity> page = this.baseMapper.selectPage(new Query<SysJobEntity>(params).getPage(),
				new QueryWrapper<SysJobEntity>().like(ToolUtil.isNotEmpty(jobName), "job_name", jobName)
						.like(ToolUtil.isNotEmpty(jobGroup), "job_group", jobGroup)
						.eq(ToolUtil.isNotEmpty(status), "status", status)
		);
		page.getRecords().forEach(r->{
			r.setNextDate(CronUtils.getNextExecution(r.getCronExpression()));
		});
		return new PageUtil(page);
	}
	
	
	/**
	 * 新增任务
	 * @author zhouzhou
	 * @date 2020-03-08 16:33
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean add(SysJobEntity sysJob) {

		if(this.save(sysJob)){
			ScheduleUtils.createScheduleJob(scheduler, sysJob);
			return true;
		}

		return false;
	}

	@Transactional
	public boolean updateSysJob(SysJobEntity job) throws Exception {
		SysJobEntity properties = this.getById(job.getId());
		if (this.updateById(job)){
			updateSchedulerJob(job, properties.getJobGroup());
			return true;
		}
		return false;
	}


	/*** 更新任务
     * @param job 任务对象
     * @param jobGroup 任务组名
     */
	public void updateSchedulerJob(SysJobEntity job, String jobGroup) throws Exception{

		Long jobId = job.getId();
		// 判断是否存在
		JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);

		if (scheduler.checkExists(jobKey)){
			// 防止创建时存在数据问题 先移除，然后在执行创建操作
			scheduler.deleteJob(jobKey);
		}

		ScheduleUtils.createScheduleJob(scheduler, job);
	}

	/**
	 * 立即运行任务
	 *
	 * @param job 调度信息
	 */
	@Transactional
	public void run(Long[] jobIds) throws SchedulerException{
		for(Long id : jobIds){
			SysJobEntity tmpObj = this.getById(id);
			// 参数
			JobDataMap dataMap = new JobDataMap();
			dataMap.put(JobInvokeUtil.TASK_PROPERTIES, tmpObj);
			scheduler.triggerJob(ScheduleUtils.getJobKey(id, tmpObj.getJobGroup()), dataMap);
		}
	}


	/**
	 * 删除任务后，所对应的trigger也将被删除
	 * @param job 调度信息
	 */
	@Transactional
	public boolean deleteJob(SysJobEntity job) throws SchedulerException {
		Long jobId = job.getId();
		String jobGroup = job.getJobGroup();
		if (this.removeById(jobId)){
			scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
			return true;
		}
		return false;
	}
	
	
	@Transactional
	public void deleteBatchByIds(Long[] jobIds) throws SchedulerException {
		
		for (Long jobId : jobIds) {
			SysJobEntity job = this.getById(jobId);
			deleteJob(job);
		}
	}
	
	
	/**
	 * 批量更新状态
	 * @author zhouzhou
	 * @date 2020-03-08 16:58
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateBatchStatus(Long[] jobIds, String status) {
		
		return this.baseMapper.updateBatchStatus(status,jobIds);
		
	}


	/**
	 * 修改状态
	 * @param job
	 * @return
	 * @throws SchedulerException
	 */
	@Transactional
	public boolean changeStatus(SysJobEntity job) throws SchedulerException{
		String status = job.getStatus();
		if (Constant.ScheduleStatus.NORMAL.getValue().equals(status)) {
			return resumeJob(job);
		}
		else if (Constant.ScheduleStatus.PAUSE.getValue().equals(status)) {
			return pauseJob(job);
		}
		return false;
	}

	/**
	 * 恢复任务
	 *
	 * @param job 调度信息
	 */
	@Transactional
	public boolean resumeJob(SysJobEntity job) throws SchedulerException {
		Long jobId = job.getId();
		String jobGroup = job.getJobGroup();
		job.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
		if (this.updateById(job)){
			scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
			return true;
		}
		return false;
	}

	/**
	 * 暂停服务
	 * @param job
	 * @return
	 * @throws SchedulerException
	 */
	@Transactional
	public boolean pauseJob(SysJobEntity job) throws SchedulerException {
		Long jobId = job.getId();
		String jobGroup = job.getJobGroup();
		job.setStatus(Constant.ScheduleStatus.PAUSE.getValue());
		if (this.updateById(job)){
			scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
			return true;
		}
		return false;
	}
	
	
	/**
	 * 验证表达式是否有效
	 * @author zhouzhou
	 * @date 2020-03-08 17:01
	 */
	public boolean checkCronExpressionIsValid(String cronExpression) {
		return CronUtils.isValid(cronExpression);
	}

	
}
