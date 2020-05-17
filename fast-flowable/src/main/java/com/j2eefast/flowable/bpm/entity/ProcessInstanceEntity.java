package com.j2eefast.flowable.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>流程实例</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-24 10:09
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Data
@TableName("act_hi_procinst")
public class ProcessInstanceEntity  implements Serializable {
	/**
	 * 流程实例id
	 */
	@TableId(value = "ID_")
	private String processInstanceId;
	/**
	 * 流程定义id
	 */
	@TableField(value = "PROC_DEF_ID_")
	private String processDefinitionId;
	/**
	 * 激活状态 1激活 2挂起
	 */
	@TableField(exist = false)
	private int suspensionState;
	/**
	 * 表单名称
	 */
	@TableField(value = "NAME_")
	private String formName;
	/**
	 * 表单主键
	 */
	@TableField(value = "BUSINESS_KEY_")
	private String businessKey;

	@TableField(exist = false)
	private String applyType;
	/**
	 * 开始时间
	 */
	@TableField(value = "START_TIME_")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	private Date startTime;
	/**
	 * 结束时间
	 */
	@TableField(value = "END_TIME_")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	private Date endTime;


	/**
	 * 审批人
	 */
	@TableField(exist = false)
	private String approver;

	/**
	 * 实例状态
	 */
	@TableField(exist = false)
	private String status;
	/**
	 * 发起人
	 */
	@TableField(exist = false)
	private String starter;
	/**
	 * 发起人id
	 */
	@TableField(exist = false)
	private String starterId;

	@TableField(exist = false)
	private String prefixUrl;

	@TableField(exist = false)
	private String approvalType;

	/**
	 * 系统标识
	 */
	@TableField(exist = false)
	private String systemSn;
}
