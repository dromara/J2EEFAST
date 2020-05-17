package com.j2eefast.flowable.bpm.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>启动流程</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-22 15:53
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Data
public class StartProcessInstanceEntity implements Serializable {

	/**
	 * 流程定义key 必填
	 */
	private String processDefinitionKey;
	/**
	 * 业务系统id 必填
	 */
	private String businessKey;
	/**
	 * 启动流程变量 选填
	 */
	private Map<String, Object> variables;
	/**
	 * 申请人工号 必填
	 */
	private String currentUserCode;
	/**
	 * 系统标识 必填
	 */
	private String systemSn;
	/**
	 * 表单显示名称 必填
	 */
	private String formName;
	/**
	 * 流程提交人工号 必填
	 */
	private String creator;
}
