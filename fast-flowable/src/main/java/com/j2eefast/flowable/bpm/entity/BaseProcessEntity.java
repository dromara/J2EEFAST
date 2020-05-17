package com.j2eefast.flowable.bpm.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>任务审批</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-26 10:20
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Data
public class BaseProcessEntity implements Serializable {

	/**********************任务相关的参数**********************/
	/**
	 * 任务id 必填
	 */
	private String taskId;
	/**********************审批意见的参数**********************/
	/**
	 * 操作人code 必填
	 */
	private String userId;

	/**
	 * 审批意见 必填
	 */
	private String message;

	/**
	 * 流程实例的id 必填
	 */
	private String processInstanceId;

	/**
	 * 审批类型 必填
	 */
	private String type;
}
