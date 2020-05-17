package com.j2eefast.flowable.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>流程定义</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-27 14:44
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Data
@TableName("act_re_procdef")
public class BpmProcessDefinitionEntity {

	/**
	 * 流程实例id
	 */
	@TableId(value = "ID_")
	private String id;
	/**
	 * 名称
	 */
	@TableField(value = "NAME_")
	private String name;
	/**
	 * 标识
	 */
	@TableField(value = "KEY_")
	private String modelKey;

	@TableField(value = "CATEGORY_")
	private String category;
	@TableField(value = "VERSION_")
	private int version;
	@TableField(value = "DEPLOYMENT_ID_")
	private String deploymentId;
	@TableField(value = "RESOURCE_NAME_")
	private String resourceName;

	@TableField(value = "DGRM_RESOURCE_NAME_")
	private String diagramResourceName;
	@TableField(value = "SUSPENSION_STATE_")
	private int suspensionState;		// 暂停状态
	/**
	 * 租户号
	 */
	@TableField(value = "TENANT_ID_")
	private String tenantId;
}
