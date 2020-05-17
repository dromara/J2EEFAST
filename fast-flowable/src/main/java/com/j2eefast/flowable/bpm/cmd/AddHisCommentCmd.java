package com.j2eefast.flowable.bpm.cmd;

import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.flowable.bpm.enums.CommentTypeEnum;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.persistence.entity.CommentEntity;
import org.flowable.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.task.Comment;
import org.flowable.engine.task.Event;

/**
 * <p>添加历史审批记录</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-22 16:38
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public class AddHisCommentCmd implements Command<Comment> {

	protected String taskId;
	protected String userId;
	protected String processInstanceId;
	protected String type;
	protected String message;

	public AddHisCommentCmd(String taskId, String userId, String processInstanceId, String type, String message) {
		this.taskId = taskId;
		this.userId = userId;
		this.processInstanceId = processInstanceId;
		this.type = type;
		this.message = message;
	}

	@Override
	public Comment execute(CommandContext commandContext) {
		HistoricProcessInstanceEntity hisProIns = null;
		if (processInstanceId != null) {
			hisProIns = CommandContextUtil.getHistoricProcessInstanceEntityManager(commandContext).findById(processInstanceId);
			if (hisProIns == null) {
				throw new FlowableObjectNotFoundException("HistoricProcessInstance " + processInstanceId + " doesn't exist", HistoricProcessInstanceEntity.class);
			}
		}
		CommentEntity comment = CommandContextUtil.getCommentEntityManager(commandContext).create();
		comment.setUserId(userId);
		comment.setType((type == null) ? CommentTypeEnum.SP.toString() : type);
		comment.setTime(CommandContextUtil.getProcessEngineConfiguration(commandContext).getClock().getCurrentTime());
		comment.setTaskId(taskId);
		comment.setProcessInstanceId(processInstanceId);
		comment.setAction(Event.ACTION_ADD_COMMENT);

		String eventMessage = ToolUtil.isNotEmpty(message) ? message.replaceAll("\\s+", " ") : "";
		if (eventMessage.length() > 3900) {
			eventMessage = eventMessage.substring(0, 3900) + "...";
		}
		comment.setMessage(eventMessage);
		comment.setFullMessage(message);
		CommandContextUtil.getCommentEntityManager(commandContext).insert(comment);
		return comment;
	}

	protected String getSuspendedTaskException() {
		return "Cannot add a comment to a suspended task";
	}

	protected String getSuspendedExceptionMessage() {
		return "Cannot add a comment to a suspended execution";
	}
}
