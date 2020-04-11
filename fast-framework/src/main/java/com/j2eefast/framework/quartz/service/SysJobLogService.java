package com.j2eefast.framework.quartz.service;

import java.util.Arrays;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.quartz.entity.SysJobLogEntity;
import com.j2eefast.framework.quartz.mapper.SysJobLogMapper;



/**
 * 定时任务日志服务实现类
 * @author zhouzhou
 * @date 2020-03-08 16:07
 */
@Service
public class SysJobLogService extends ServiceImpl<SysJobLogMapper,SysJobLogEntity> {
	
	/**
	 * 页面展示查询翻页
	 */
	public PageUtil findPage(Map<String, Object> params) {
		String jobName = (String) params.get("jobName");
		String jobGroup = (String) params.get("jobGroup");
		String beginTime = (String) params.get("beginTime");
		String endTime = (String) params.get("endTime");
		String status = (String) params.get("status");
		Page<SysJobLogEntity> page = this.baseMapper.selectPage(
				new Query<SysJobLogEntity>(params).getPage(),
				new QueryWrapper<SysJobLogEntity>()
				.like(ToolUtil.isNotEmpty(jobName), "job_name", jobName)
				.eq(ToolUtil.isNotEmpty(jobGroup), "job_group", jobGroup)
			    .eq(ToolUtil.isNotEmpty(status), "status", status)
				.apply(ToolUtil.isNotEmpty(beginTime)," date_format(create_time,'%y%m%d') "
						+ ">= date_format('"+beginTime+"','%y%m%d')")
			    .apply(ToolUtil.isNotEmpty(endTime)," date_format(create_time,'%y%m%d') "
			    		+ "<= date_format('"+endTime+"','%y%m%d')")
		);
		return new PageUtil(page);
	}
	
	/**
	 * 清空日志表
	 * @author zhouzhou
	 * @date 2020-03-08 20:37
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean cleanJobLog() {
		 this.baseMapper.cleanJobLog();
		 return true;
	}
	
	/**
	 * 根居主键批量删除
	 * @author zhouzhou
	 * @date 2020-03-08 20:45
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteBatchByIds(Long[] logIds) {
		return this.removeByIds(Arrays.asList(logIds));
	}
	
}
