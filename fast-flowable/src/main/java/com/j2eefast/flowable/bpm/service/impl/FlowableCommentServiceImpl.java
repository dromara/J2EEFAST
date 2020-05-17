package com.j2eefast.flowable.bpm.service.impl;

import cn.hutool.core.date.DateUtil;
import com.j2eefast.flowable.bpm.cmd.AddHisCommentCmd;
import com.j2eefast.flowable.bpm.entity.CommentEntity;
import com.j2eefast.flowable.bpm.enums.CommentTypeEnum;
import com.j2eefast.flowable.bpm.mapper.IFlowableCommentMapper;
import com.j2eefast.flowable.bpm.service.FlowableCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p></p>
 *
 * @author: zhouzhou
 * @date: 2020-04-22 16:31
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Service
public class FlowableCommentServiceImpl extends BaseProcessService implements FlowableCommentService {

	@Resource
	private IFlowableCommentMapper flowableCommentMapper;

	@Override
	public void addComment(CommentEntity comment) {
		managementService.executeCommand(new AddHisCommentCmd(comment.getTaskId(), comment.getUserId(), comment.getProcessInstanceId(),
				comment.getType(), comment.getMessage()));
	}

	@Override
	public List<CommentEntity> getFlowCommentVosByProcessInstanceId(String processInstanceId) {
		List<CommentEntity> datas = flowableCommentMapper.getFlowCommentVosByProcessInstanceId(processInstanceId);
		datas.forEach(commentVo -> {
			commentVo.setTypeName(CommentTypeEnum.getEnumMsgByType(commentVo.getType()));
			if(commentVo.getDuration() != 0 || (commentVo.getStartTime().equals(commentVo.getEndTime()))){
				commentVo.setConsuming(DateUtil.formatBetween(commentVo.getDuration()));
			}
		});
		return datas;
	}

	@Override
	public String findTaskInstId(String ProcessInstanceId) {
		return this.flowableCommentMapper.findTaskInstId(ProcessInstanceId);
	}
}
