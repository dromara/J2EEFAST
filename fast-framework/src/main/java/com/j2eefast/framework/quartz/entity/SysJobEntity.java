package com.j2eefast.framework.quartz.entity;


import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.j2eefast.common.core.base.entity.BaseEntity;

import java.util.Date;

/**
 * 定时任务
 * @author zhouzhou
 * @date 2020-03-08 15:33
 */
@TableName("sys_job")
public class SysJobEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 任务调度参数key
	 */
	public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";// JOB_PARAM_KEY

	/**
	 * 任务id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long jobId;

	@NotBlank(message = "名称不能为空")
	private String jobName;


	@NotBlank(message = "任务分组不能为空")
	private  String jobGroup;

	/**
	 * spring bean名称
	 */
	@NotBlank(message = "bean名称不能为空")
	private String beanName;

	/**
	 * 方法名
	 */
	@NotBlank(message = "方法名称不能为空")
	private String methodName;

	/**
	 * 参数
	 */
	private String params;

	/**
	 * cron表达式
	 */
	@NotBlank(message = "cron表达式不能为空")
	private String cronExpression;

	/**
	 * 下次运行的时间
	 */
	@TableField(exist = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	private Date nextDate;

	/**
	 * 逻辑删除 是否删除
	 */
	@TableLogic
	private String delFlag;
	
	/**
	 * 任务状态
	 */
	private String status;

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * 设置：任务id
	 * 
	 * @param jobId 任务id
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public Long getJobId() {
		return jobId;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 设置：cron表达式
	 * 
	 * @param cronExpression cron表达式
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/**
	 * 获取：cron表达式
	 * 
	 * @return String
	 */
	public String getCronExpression() {
		return cronExpression;
	}

	public Date getNextDate() {
		return nextDate;
	}

	public void setNextDate(Date nextDate) {
		this.nextDate = nextDate;
	}
}
