package com.j2eefast.flowable.bpm.service.impl;

import cn.hutool.core.date.DateUtil;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.flowable.bpm.entity.CompleteTaskEntity;
import com.j2eefast.flowable.bpm.entity.BpmTaskEntity;
import com.j2eefast.flowable.bpm.enums.CommentTypeEnum;
import com.j2eefast.flowable.bpm.mapper.BpmTaskMapper;
import com.j2eefast.flowable.bpm.service.IFlowableTaskService;
import com.j2eefast.flowable.bpm.utils.BpmConstant;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.DelegationState;
import com.j2eefast.framework.utils.Constant;
import org.flowable.task.api.Task;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javax.annotation.Resource;
import java.util.Map;

/**
 * <p></p>
 *
 * @author: zhouzhou
 * @date: 2020-04-25 14:38
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Service
public class FlowableTaskServiceImpl extends BaseProcessService implements IFlowableTaskService {

	@Resource
	private BpmTaskMapper bpmTaskMapper;
	@Autowired
	protected RuntimeService runtimeService;

	@Override
	public PageUtil findApplyingTasksPage(Map<String, Object> params){
		Page<BpmTaskEntity> page = bpmTaskMapper.findApplyingTasksPage(new Query<BpmTaskEntity>(params).getPage(),
				(String)params.get("userId"),
				(String) params.get(Constant.SQL_FILTER));
		return new PageUtil(page);
	}

	@Override
	public PageUtil findApplyedTasksPage(Map<String, Object> params) {
		Page<BpmTaskEntity> page = bpmTaskMapper.findApplyedTasksPage(new Query<BpmTaskEntity>(params).getPage(),
				(String)params.get("userId"),
				(String) params.get(Constant.SQL_FILTER));
		page.getRecords().forEach(bpmTask->{
			bpmTask.setConsuming(DateUtil.formatBetween(bpmTask.getDuration()));
		});
		return new PageUtil(page);
	}

	/**
	 * 审批任务
	 * @param params 参数
	 * @return
	 */
	@Override
	public ResponseData complete(CompleteTaskEntity params) {
		//1. 先判断是否有实例ID
		if(ToolUtil.isNotEmpty(params.getProcessInstanceId())
				&& ToolUtil.isNotEmpty(params.getTaskId())){
			String taskId = params.getTaskId();
			//2.查看当前任务是存在 使用flowable 方法查询
			TaskEntity taskEntity =
					(TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
			if(ToolUtil.isNotEmpty(taskEntity)){
				//3.委派处理
				if (DelegationState.PENDING.equals(taskEntity.getDelegationState())) {
					//3.1生成历史记录
					TaskEntity task = this.createSubTask(taskEntity, params.getUserId());
					taskService.complete(task.getId());
					taskId = task.getId();
					//3.2执行委派
					taskService.resolveTask(params.getTaskId(), params.getVariables());
				} else {
					//3.1修改执行人 其实我这里就相当于签收了
					taskService.setAssignee(params.getTaskId(), params.getUserId());
					//3.2执行任务
					taskService.complete(params.getTaskId(), params.getVariables());
					//4.处理加签父任务
					String parentTaskId = taskEntity.getParentTaskId();
					if (ToolUtil.isNotEmpty(parentTaskId)) {
						String tableName = managementService.getTableName(TaskEntity.class);
						String sql = "select count(1) from " + tableName + " where PARENT_TASK_ID_=#{parentTaskId}";
						long subTaskCount = taskService.createNativeTaskQuery().sql(sql).parameter("parentTaskId", parentTaskId).count();
						if (subTaskCount == 0) {
							Task task = taskService.createTaskQuery().taskId(parentTaskId).singleResult();
							//处理前后加签的任务
							taskService.resolveTask(parentTaskId);
							if (BpmConstant.AFTER_ADDSIGN.equals(task.getScopeType())) {
								taskService.complete(parentTaskId);
							}
						}
					}
				}
				String type = params.getType() == null ? CommentTypeEnum.SP.toString() : params.getType();
				//5.生成审批意见
				this.addComment(taskId, params.getUserId(), params.getProcessInstanceId(), type, params.getMessage());
				return ResponseData.success("审批成功");
			}else{
				return ResponseData.error("20001","没有次任务,请确认!");
			}
		}else{
			return ResponseData.error("20001","传入参数错误,请确认参数是否正确!");
		}
	}

	@Override
	public boolean isSuspended(String processInstanceId) {
		boolean flag = true;
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if (processInstance != null){
			flag = !processInstance.isSuspended();
		}
		return flag;
	}

}
