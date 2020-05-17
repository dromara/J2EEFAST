package com.j2eefast.flowable.bpm.cmd.processinstance;

import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.compatibility.Flowable5CompatibilityHandler;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.impl.util.Flowable5Util;
import org.flowable.engine.runtime.ProcessInstance;

import java.io.Serializable;

/**
 * <p>删除流程定义命令</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-14 20:24
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public class DeleteFlowableProcessInstanceCmd implements Command<Void>, Serializable {

	private static final long serialVersionUID = 1L;
	protected String processInstanceId;
	protected String deleteReason;
	protected boolean cascade = false;

	public DeleteFlowableProcessInstanceCmd(String processInstanceId, String deleteReason) {
		this.processInstanceId = processInstanceId;
		this.deleteReason = deleteReason;
	}

	public DeleteFlowableProcessInstanceCmd(String processInstanceId, String deleteReason,boolean cascade) {
		this.processInstanceId = processInstanceId;
		this.deleteReason = deleteReason;
		this.cascade = cascade;
	}

	@Override
	public Void execute(CommandContext commandContext) {
		if (processInstanceId == null) {
			throw new FlowableIllegalArgumentException("processInstanceId is null");
		}

		ExecutionEntity processInstanceEntity = CommandContextUtil.getExecutionEntityManager(commandContext).findById(processInstanceId);
		if (processInstanceEntity == null) {
			throw new FlowableObjectNotFoundException("No process instance found for id '" + processInstanceId + "'", ProcessInstance.class);
		}
		if (processInstanceEntity.isDeleted()) {
			return null;
		}

		if (Flowable5Util.isFlowable5ProcessDefinitionId(commandContext, processInstanceEntity.getProcessDefinitionId())) {
			Flowable5CompatibilityHandler compatibilityHandler = Flowable5Util.getFlowable5CompatibilityHandler();
			compatibilityHandler.deleteProcessInstance(processInstanceId, deleteReason);
		} else {
			CommandContextUtil.getExecutionEntityManager(commandContext).deleteProcessInstance(processInstanceEntity.getProcessInstanceId(), deleteReason, cascade);
		}

		return null;
	}

}
