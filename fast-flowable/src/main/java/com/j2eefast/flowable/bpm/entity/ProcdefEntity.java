package com.j2eefast.flowable.bpm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;

import java.util.Date;

/**
 * <p>流程定义</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-18 20:37
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Data
public class ProcdefEntity {

	protected String id;
	protected String name;
	protected String key;
	protected String category;
	protected int version;
	protected String deploymentId;
	protected String resourceName;
	protected String diagramResourceName;
	protected int suspensionState;		// 暂停状态

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	protected Date deploymentTime;

	public ProcdefEntity(ProcessDefinitionEntityImpl procDef) {
		this.id = procDef.getId();
		this.name = procDef.getName();
		this.key = procDef.getKey();
		this.category = procDef.getCategory();
		this.version = procDef.getVersion();
		this.deploymentId = procDef.getDeploymentId();
		this.resourceName = procDef.getResourceName();
		this.diagramResourceName = procDef.getDiagramResourceName();
		this.suspensionState = procDef.getSuspensionState();
	}

}
