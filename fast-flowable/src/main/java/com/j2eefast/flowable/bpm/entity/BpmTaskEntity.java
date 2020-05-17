package com.j2eefast.flowable.bpm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * <p>任务</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-25 14:19
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Data
@TableName("act_ru_task")
public class BpmTaskEntity {

	/**
	 * 任务id
	 */
	@TableId(value = "ID_",type = IdType.INPUT)
	private String taskId;

	/**
	 * 任务名称
	 */
	@TableField(value = "NAME_")
	private String taskName;
	/**
	 * 审批人
	 */
	@TableField(exist = false)
	private String approver;
	/**
	 * 审批人id
	 */
	@TableField(exist = false)
	private String approverId;
	/**
	 * 表单名称
	 */
	@TableField(exist = false)
	private String formName;

	/**
	 * 流程发起人
	 */
	@TableField(exist = false)
	private String userName;

	/**
	 * 状态
	 */
	@TableField(exist = false)
	private String status;

	/**
	 * 优先级
	 */
	@TableField(value = "PRIORITY_")
	private int priority;

	/**
	 * 业务主键
	 */
	@TableField(exist = false)
	private String businessKey;
	/**
	 * 流程实例id
	 */
	@TableField(exist = false)
	private String processInstanceId;

	/**
	 * 提交表单形式
	 */
	@TableField(exist = false)
	private String applyType;

	/**
	 * 表单前缀
	 */
	@TableField(exist = false)
	private String prefixUrl;

	/**
	 * 开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	@TableField(value = "CREATE_TIME_")
	private Date startTime ;

	/**
	 * 结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	@TableField(exist = false)
	private Date endTime;


	/**
	 * 耗时 毫秒
	 */
	@TableField(exist = false)
	private long duration;

	@TableField(exist = false)
	private String consuming;

	/**
	 * 系统标识
	 */
	@TableField(exist = false)
	private String tenantId;
}
