package com.j2eefast.flowable.bpm.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.flowable.bpm.cmd.processinstance.DeleteFlowableProcessInstanceCmd;
import com.j2eefast.flowable.bpm.entity.ProcessInstanceEntity;
import com.j2eefast.flowable.bpm.entity.RevokeProcessEntity;
import com.j2eefast.flowable.bpm.entity.StartProcessInstanceEntity;
import com.j2eefast.flowable.bpm.enums.CommentTypeEnum;
import com.j2eefast.flowable.bpm.mapper.ProcessInstanceMapper;
import com.j2eefast.flowable.bpm.service.FlowableProcessInstanceService;
import com.j2eefast.flowable.bpm.service.IFlowableBpmnModelService;
import com.j2eefast.framework.utils.Constant;
import org.flowable.bpmn.model.Activity;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.flowable.engine.runtime.Execution;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @author: zhouzhou
 * @date: 2020-04-22 16:10
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Service
public class FlowableProcessInstanceServiceImpl extends  BaseProcessService implements FlowableProcessInstanceService {

	@Autowired
	private IFlowableBpmnModelService flowableBpmnModelService;

	@Override
	public ResponseData startProcessInstanceByKey(StartProcessInstanceEntity params) {
		if (ToolUtil.isNotEmpty(params.getProcessDefinitionKey())
				&& ToolUtil.isNotEmpty(params.getBusinessKey())
				&& ToolUtil.isNotEmpty(params.getSystemSn())) {
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(params.getProcessDefinitionKey())
					.latestVersion().singleResult();
			if (processDefinition != null && processDefinition.isSuspended()) {
				return ResponseData.error("70001","此流程已经挂起,请联系系统管理员!");
			}
			/**
			 * 1、设置变量
			 * 1.1、设置提交人字段为空字符串让其自动跳过
			 * 1.2、设置可以自动跳过
			 * 1.3、汇报线的参数设置
			 */
			//1.1、设置提交人字段为空字符串让其自动跳过
			params.getVariables().put("initiator", "");
			//1.2、设置可以自动跳过
			params.getVariables().put("_FLOWABLE_SKIP_EXPRESSION_ENABLED", true);
			// TODO 1.3、汇报线的参数设置
			//2、当我们流程创建人和发起人
			String creator = params.getCreator();
			if (ToolUtil.isEmpty(creator)) {
				creator = params.getCurrentUserCode();
				params.setCreator(creator);
			}
			//3.启动流程
			identityService.setAuthenticatedUserId(creator);
			ProcessInstance processInstance = runtimeService.createProcessInstanceBuilder()
					.processDefinitionKey(params.getProcessDefinitionKey().trim())
					.name(params.getFormName().trim())
					.businessKey(params.getBusinessKey().trim())
					.variables(params.getVariables())
					.tenantId(params.getSystemSn().trim())
					.start();
			//4.添加审批记录


			String taskId = flowableCommentService.findTaskInstId(processInstance.getProcessInstanceId());

			this.addComment(taskId,params.getCurrentUserCode(), processInstance.getProcessInstanceId(),
					CommentTypeEnum.TJ.toString(), params.getFormName() + "提交");
			//5.TODO 推送消息数据
			return ResponseData.success(processInstance);
		} else {
			return  ResponseData.error("70001","请填写 这三个字段 ProcessDefinitionKey,BusinessKey,SystemSn");
		}
	}


	@Override
	public PageUtil findPage(Map<String, Object> params) {
		String formName = (String) params.get("formName");
		String starterId = (String) params.get("starterId");
		String starter = (String) params.get("starter");
		Page<ProcessInstanceEntity> page = processInstanceMapper.findPage(	new Query<ProcessInstanceEntity>(params).getPage(),
				StrUtil.nullToDefault(starterId,""),
				StrUtil.nullToDefault(formName,""),
				StrUtil.nullToDefault(starter,""),
				(String) params.get(Constant.SQL_FILTER));
		return new PageUtil(page);
	}

	@Override
	public ResponseData deleteProcessInstanceById(String processInstanceId) {
		long count = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).count();
		if (count > 0) {
			DeleteFlowableProcessInstanceCmd cmd = new DeleteFlowableProcessInstanceCmd(processInstanceId, "删除流程实例", true);
			managementService.executeCommand(cmd);
			return ResponseData.success("删除成功");
		} else {
			historyService.deleteHistoricProcessInstance(processInstanceId);
			return ResponseData.success("删除成功");
		}
	}

	@Override
	public ResponseData suspendOrActivateProcessInstanceById(String processInstanceId, Integer suspensionState) {
		if (suspensionState == 1) {
			runtimeService.suspendProcessInstanceById(processInstanceId);
			return ResponseData.success("挂起成功");
		} else {
			runtimeService.activateProcessInstanceById(processInstanceId);
			return ResponseData.success("激活成功");
		}
	}

	/**
	 * 发起人任务撤回
	 * @param revoke
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResponseData revokeProcess(RevokeProcessEntity revoke) {
		if (ToolUtil.isNotEmpty(revoke.getProcessInstanceId())) {
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
					.processInstanceId(revoke.getProcessInstanceId()).singleResult();
			if (processInstance != null) {
				//1.添加撤回意见
				this.addComment(revoke.getUserId(), revoke.getProcessInstanceId(), CommentTypeEnum.CH.toString(), revoke.getMessage());
				//2.设置提交人
				runtimeService.setVariable(revoke.getProcessInstanceId(), "initiator", processInstance.getStartUserId());
				//3.执行撤回
//				Activity disActivity = flowableBpmnModelService.findActivityByName(processInstance.getProcessDefinitionId(), "提交人");
				String activityId = flowableActinstService.getBpmActivityId(processInstance.getStartUserId(),revoke.getProcessInstanceId());
				//4.删除运行和历史的节点信息
				this.deleteActivity(activityId, revoke.getProcessInstanceId());
				//5.执行跳转
				List<Execution> executions = runtimeService.createExecutionQuery().parentId(revoke.getProcessInstanceId()).list();
				List<String> executionIds = new ArrayList<>();
				executions.forEach(execution -> executionIds.add(execution.getId()));
				this.moveExecutionsToSingleActivityId(executionIds,activityId);
				return ResponseData.success("撤回成功!");
			}else{
				return ResponseData.error("撤回失败!");
			}
		} else {
			return ResponseData.error("流程实例id不能为空!");
		}
	}

}
