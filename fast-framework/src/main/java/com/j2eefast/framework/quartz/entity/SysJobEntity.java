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

//	/**
//	 * 任务调度参数key
//	 */
//	public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";// JOB_PARAM_KEY

	/**
	 * 任务id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	@NotBlank(message = "名称不能为空")
	private String jobName;


	@NotBlank(message = "任务分组不能为空")
	private  String jobGroup;

	/** 调用目标字符串 */
	private String invokeTarget;

	/**
	 * cron表达式
	 */
	@NotBlank(message = "cron表达式不能为空")
	private String cronExpression;

	/** cron计划策略 */
	private String misfirePolicy;

	/** 是否并发执行（0允许 1禁止） */
	private String concurrent;

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

	public String getInvokeTarget() {
		return invokeTarget;
	}

	public void setInvokeTarget(String invokeTarget) {
		this.invokeTarget = invokeTarget;
	}

	public String getConcurrent() {
		return concurrent;
	}

	public void setConcurrent(String concurrent) {
		this.concurrent = concurrent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMisfirePolicy() {
		return misfirePolicy;
	}

	public void setMisfirePolicy(String misfirePolicy) {
		this.misfirePolicy = misfirePolicy;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}


	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
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
