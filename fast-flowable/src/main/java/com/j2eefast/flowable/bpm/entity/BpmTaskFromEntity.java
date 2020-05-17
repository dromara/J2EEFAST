package com.j2eefast.flowable.bpm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.j2eefast.common.core.base.entity.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>系统流程表单关联表</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-27 17:35
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Data
@TableName("bpm_task_from")
public class BpmTaskFromEntity extends BaseEntity {

	@TableId(value = "id",type = IdType.ASSIGN_ID)
	private Long id;

	@NotBlank(message = "表单名称不能为空")
	private String fromName;

	@NotBlank(message = "表单前缀URL不能为空")
	private String prefixUrl;


	private String applyType;

	private String approvalType;


	@NotBlank(message = "流程定义ID不能为空")
	private String processDefinitionKey;

	@NotBlank(message = "流程Key不能为空")
	private String modelKey;

	private String version;

	@TableField(exist = false)
	private int suspensionState; //状态

}
