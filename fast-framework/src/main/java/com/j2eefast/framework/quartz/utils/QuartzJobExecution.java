package com.j2eefast.framework.quartz.utils;

import com.j2eefast.framework.quartz.entity.SysJobEntity;
import org.quartz.JobExecutionContext;

/**
 * <p></p>
 *
 * @author: zhouzhou
 * @date: 2020-05-29 10:51
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public class QuartzJobExecution extends AbstractQuartzJob {
	@Override
	protected void doExecute(JobExecutionContext context, SysJobEntity sysJob) throws Exception{
		JobInvokeUtil.invokeMethod(sysJob);
	}
}
