package com.j2eefast.framework.quartz.utils;

import com.j2eefast.framework.quartz.entity.SysJobEntity;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（禁止并发执行）
 *
 * @author ruoyi
 * @author zhouzhou 二次修改
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {

	@Override
	protected void doExecute(JobExecutionContext context, SysJobEntity sysJob) throws Exception {
		JobInvokeUtil.invokeMethod(sysJob);
	}
}

