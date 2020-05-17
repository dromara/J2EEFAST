package com.j2eefast.flowable.bpm.service.impl;

import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.flowable.bpm.service.IFlowableBpmnModelService;
import org.flowable.bpmn.model.Activity;
import org.flowable.bpmn.model.BpmnModel;
import  org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * <p></p>
 *
 * @author: zhouzhou
 * @date: 2020-04-28 11:32
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Service
public class FlowableBpmnModelServiceImpl extends BaseProcessService implements IFlowableBpmnModelService {

	@Override
	public BpmnModel getBpmnModelByProcessDefId(String processDefId) {
		return repositoryService.getBpmnModel(processDefId);
	}

	@Override
	public Activity findActivityByName(String processDefId, String name) {
		Activity activity = null;
		BpmnModel bpmnModel = this.getBpmnModelByProcessDefId(processDefId);
		Process process = bpmnModel.getMainProcess();
		Collection<FlowElement> list = process.getFlowElements();
		for (FlowElement f : list) {
			if (ToolUtil.isNotEmpty(name)) {
				if (name.equals(f.getName())) {
					activity = (Activity) f;
					break;
				}
			}
		}
		return activity;
	}

//	public String getBpmActivityId(String userId,
//									  String processInstanceId){
//		return hisFlowableActinstMapper.getActivityId(userId,processInstanceId);
//	}
}
