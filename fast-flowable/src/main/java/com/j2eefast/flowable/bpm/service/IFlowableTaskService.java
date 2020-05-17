package com.j2eefast.flowable.bpm.service;

import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.flowable.bpm.entity.CompleteTaskEntity;

import java.util.Map;

/**
 * 运行时任务
 */
public interface IFlowableTaskService {

	/**
	 * 查询待办任务
	 * @param params
	 * @return
	 */
	PageUtil findApplyingTasksPage(Map<String, Object> params);


	/**
	 * 查询已办任务
	 * @param params
	 * @return
	 */
	PageUtil findApplyedTasksPage(Map<String, Object> params);

	/**
	 * 执行任务
	 *
	 * @param params 参数
	 */
	ResponseData complete(CompleteTaskEntity params);


	/**
	 * 判断任务是否挂起
	 * @param processInstanceId
	 * @return
	 */
	boolean isSuspended(String processInstanceId);
}
