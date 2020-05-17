package com.j2eefast.flowable.bpm.service.impl;

import cn.hutool.core.util.IdUtil;
import com.j2eefast.flowable.bpm.entity.CommentEntity;
import com.j2eefast.flowable.bpm.mapper.IHisFlowableActinstMapper;
import com.j2eefast.flowable.bpm.mapper.IRunFlowableActinstMapper;
import com.j2eefast.flowable.bpm.mapper.ProcessInstanceMapper;
import com.j2eefast.flowable.bpm.service.FlowableActinstService;
import com.j2eefast.flowable.bpm.service.FlowableCommentService;
import org.flowable.engine.*;
import org.flowable.engine.runtime.ActivityInstance;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.flowable.engine.impl.persistence.entity.ActivityInstanceEntity;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class BaseProcessService {

	@Autowired
	@Lazy(value = true)
	protected RepositoryService repositoryService;
	@Autowired
	@Lazy(value = true)
	protected ManagementService managementService;
	@Autowired
	@Lazy(value = true)
	protected IdentityService identityService;
	@Autowired
	@Lazy(value = true)
	protected RuntimeService runtimeService;
	@Autowired
	@Lazy(value = true)
	protected HistoryService historyService;
	@Autowired
	@Lazy(value = true)
	protected FlowableCommentService flowableCommentService;
	@Autowired
	protected TaskService taskService;

	@Resource
	protected ProcessInstanceMapper processInstanceMapper;
	@Resource
	protected FlowableActinstService flowableActinstService;

//	@Autowired
//	protected IHisFlowableActinstMapper hisFlowableActinstMapper;
//	@Autowired
//	protected IRunFlowableActinstMapper runFlowableActinstMapper;

	/**
	 * 添加审批意见
	 *
	 * @param taskId            任务id
	 * @param userCode          处理人工号
	 * @param processInstanceId 流程实例id
	 * @param type              审批类型
	 * @param message           审批意见
	 */
	protected void addComment(String taskId, String userCode, String processInstanceId, String type, String message) {
		//1.添加备注
		CommentEntity commentVo = new CommentEntity(taskId, userCode, processInstanceId, type, message);
		flowableCommentService.addComment(commentVo);
		//TODO 2.修改扩展的流程实例表的状态以备查询待办的时候能带出来状态
		//TODO 3.发送mongodb的数据到消息队列里面
	}


	/**
	 * 添加审批意见
	 *
	 * @param userCode          处理人工号
	 * @param processInstanceId 流程实例id
	 * @param type              审批类型
	 * @param message           审批意见
	 */
	protected void addComment(String userCode, String processInstanceId, String type, String message) {
		this.addComment(null, userCode, processInstanceId, type, message);
	}

	protected TaskEntity createSubTask(TaskEntity ptask, String assignee) {
		return this.createSubTask(ptask, ptask.getId(), assignee);
	}



	/**
	 * 删除跳转的历史节点信息
	 *
	 * @param disActivityId     跳转的节点id
	 * @param processInstanceId 流程实例id
	 */
	protected void deleteActivity(String disActivityId, String processInstanceId) {
		String tableName = managementService.getTableName(ActivityInstanceEntity.class);
		String sql = "select t.* from " + tableName + " t where t.PROC_INST_ID_=#{processInstanceId} and t.ACT_ID_ = #{disActivityId} " +
				" order by t.END_TIME_ ASC";
		List<ActivityInstance> disActivities = runtimeService.createNativeActivityInstanceQuery().sql(sql)
				.parameter("processInstanceId", processInstanceId)
				.parameter("disActivityId", disActivityId).list();
		//删除运行时和历史节点信息
		if (CollectionUtils.isNotEmpty(disActivities)) {
			ActivityInstance activityInstance = disActivities.get(0);
			sql = "select t.* from " + tableName + " t where t.PROC_INST_ID_=#{processInstanceId} and (t.END_TIME_ >= #{endTime} or t.END_TIME_ is null)";
			List<ActivityInstance> datas = runtimeService.createNativeActivityInstanceQuery().sql(sql).parameter("processInstanceId", processInstanceId)
					.parameter("endTime", activityInstance.getEndTime()).list();
			List<String> runActivityIds = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(datas)) {
				datas.forEach(ai -> runActivityIds.add(ai.getId()));
				flowableActinstService.deleteRunActinstsByIds(runActivityIds);
				flowableActinstService.deleteHisActinstsByIds(runActivityIds);
			}
		}
	}

	/**
	 * 执行跳转
	 */
	protected void moveExecutionsToSingleActivityId(List<String> executionIds, String activityId) {
		runtimeService.createChangeActivityStateBuilder()
				.moveExecutionsToSingleActivityId(executionIds, activityId)
				.changeState();
	}

	/**
	 * 创建子任务
	 *
	 * @param ptask    创建子任务
	 * @param assignee 子任务的执行人
	 * @return
	 */
	protected TaskEntity createSubTask(TaskEntity ptask, String ptaskId, String assignee) {
		TaskEntity task = null;
		if (ptask != null) {
			//1.生成子任务
			task = (TaskEntity) taskService.newTask(IdUtil.simpleUUID());
			task.setCategory(ptask.getCategory());
			task.setDescription(ptask.getDescription());
			task.setTenantId(ptask.getTenantId());
			task.setAssignee(assignee);
			task.setName(ptask.getName());
			task.setParentTaskId(ptaskId);
			task.setProcessDefinitionId(ptask.getProcessDefinitionId());
			task.setProcessInstanceId(ptask.getProcessInstanceId());
			task.setTaskDefinitionKey(ptask.getTaskDefinitionKey());
			task.setTaskDefinitionId(ptask.getTaskDefinitionId());
			task.setPriority(ptask.getPriority());
			task.setCreateTime(new Date());
			taskService.saveTask(task);
		}
		return task;
	}

}
