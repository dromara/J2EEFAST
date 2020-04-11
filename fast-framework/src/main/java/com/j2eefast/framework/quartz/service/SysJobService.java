package com.j2eefast.framework.quartz.service;


import java.util.Arrays;
import java.util.Map;
import org.quartz.Scheduler;
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
	public void add(SysJobEntity sysJob) {

		this.save(sysJob);

		ScheduleUtils.createScheduleJob(scheduler, sysJob);
	}
	
	/**
	 * 更新任务
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean updateSysJob(SysJobEntity sysJob) {
		
		if(this.updateById(sysJob)) {
			ScheduleUtils.updateScheduleJob(scheduler, sysJob);
			return true;
		}
		
		return false;
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteBatchByIds(Long[] jobIds) {
		
		for (Long jobId : jobIds) {
			ScheduleUtils.deleteScheduleJob(scheduler, jobId);
		}

		// 删除数据
		return this.removeByIds(Arrays.asList(jobIds));
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
	 * 根居ID批量运行
	 * @author zhouzhou
	 * @date 2020-03-08 16:55
	 */
	@Transactional(rollbackFor = Exception.class)
	public void run(Long[] jobIds) {
		
		for (Long jobId : jobIds) {
			ScheduleUtils.run(scheduler, this.getById(jobId));
		}
		
	}
	
	/**
	 * 批量暂停
	 * @author zhouzhou
	 * @date 2020-03-08 16:59
	 */
	@Transactional(rollbackFor = Exception.class)
	public void pause(Long[] jobIds) {
		
		for (Long jobId : jobIds) {
			ScheduleUtils.pauseJob(scheduler, jobId);
		}
		
		updateBatchStatus(jobIds, Constant.ScheduleStatus.PAUSE.getValue());
	}
	

	/**
	 * 批量恢复任务
	 * @author zhouzhou
	 * @date 2020-03-08 17:00
	 */
	@Transactional(rollbackFor = Exception.class)
	public void resume(Long[] jobIds) {
		
		for (Long jobId : jobIds) {
			ScheduleUtils.resumeJob(scheduler, jobId);
		}
		
		updateBatchStatus(jobIds, Constant.ScheduleStatus.NORMAL.getValue());
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
