package com.j2eefast.flowable.bpm.mapper;

import com.j2eefast.flowable.bpm.entity.CommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IFlowableCommentMapper {
	/**
	 * 通过流程实例id获取审批意见列表
	 * @param ProcessInstanceId 流程实例id
	 * @return
	 */
	List<CommentEntity> getFlowCommentVosByProcessInstanceId(String ProcessInstanceId);


	String findTaskInstId(String ProcessInstanceId);
}
