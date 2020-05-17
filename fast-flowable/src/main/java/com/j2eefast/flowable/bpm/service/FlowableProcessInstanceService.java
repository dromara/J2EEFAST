package com.j2eefast.flowable.bpm.service;

import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.flowable.bpm.entity.RevokeProcessEntity;
import com.j2eefast.flowable.bpm.entity.StartProcessInstanceEntity;
import org.flowable.engine.runtime.ProcessInstance;

import java.util.Map;

public interface FlowableProcessInstanceService {

	/**
	 * 启动流程
	 *
	 * @param startProcessInstance 参数
	 * @return
	 */
	ResponseData startProcessInstanceByKey(StartProcessInstanceEntity startProcessInstance);

	/**
	 * 查询流程实例列表
	 * @param params
	 * @return
	 */
	PageUtil findPage(Map<String, Object> params);
//
//	/**
//	 * 查询我发起的流程实例
//	 *
//	 * @param params 参数
//	 * @param query  分页参数
//	 * @return
//	 */
//	public PagerModel<ProcessInstanceVo> getMyProcessInstances(ProcessInstanceQueryVo params, Query query);
//
//	/**
//	 * 获取流程图图片
//	 *
//	 * @param processInstanceId 流程实例id
//	 * @return
//	 */
//	public byte[] createImage(String processInstanceId);
//
	/**
	 * 删除流程实例
	 *
	 * @param processInstanceId 流程实例id
	 * @return
	 */
	ResponseData deleteProcessInstanceById(String processInstanceId);
//
	/**
	 * 激活流程实例
	 *
	 * @param processInstanceId 流程实例id
	 * @param suspensionState   2激活 1挂起
	 */
	ResponseData suspendOrActivateProcessInstanceById(String processInstanceId, Integer suspensionState);
//
//	/**
//	 * 终止流程
//	 * @param endVo 参数
//	 * @return
//	 */
//	public ReturnVo<String> stopProcessInstanceById(EndProcessVo endVo) ;
//
	/**
	 * 撤回流程
	 * @return
	 */
	ResponseData revokeProcess(RevokeProcessEntity revoke);
}
