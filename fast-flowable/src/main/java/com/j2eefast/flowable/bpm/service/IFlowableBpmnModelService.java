package com.j2eefast.flowable.bpm.service;

import org.flowable.bpmn.model.Activity;
import org.flowable.bpmn.model.BpmnModel;

public interface IFlowableBpmnModelService {

	/**
	 * 通过流程定义id获取BpmnModel
	 *
	 * @param processDefId 流程定义id
	 * @return
	 */
	BpmnModel getBpmnModelByProcessDefId(String processDefId);
	/**
	 * 通过名称获取节点
	 *
	 * @param processDefId 流程定义id
	 * @param name         节点名称
	 * @return
	 */
	Activity findActivityByName(String processDefId, String name);


//	String getBpmActivityId(String userId,
//							String processInstanceId);

}
