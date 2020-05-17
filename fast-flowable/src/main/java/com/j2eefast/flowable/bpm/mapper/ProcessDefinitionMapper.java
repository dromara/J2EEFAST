package com.j2eefast.flowable.bpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j2eefast.flowable.bpm.entity.BpmProcessDefinitionEntity;
import org.apache.ibatis.annotations.Param;

public interface ProcessDefinitionMapper extends BaseMapper<BpmProcessDefinitionEntity> {
	/**
	 * 通过流程定义id获取流程定义的信息
	 * @param processDefinitionId 流程定义id
	 * @return
	 */
	BpmProcessDefinitionEntity getById(String processDefinitionId);


	/**
	 * 页面获取流程定义分页数据
	 * @param params
	 * @param sql_filter
	 * @return
	 */
	Page<BpmProcessDefinitionEntity> findPage( IPage<?> params,
											   @Param("name") String name,
											   @Param("modelKey") String modelKey,
											   @Param("category") String category,
											   @Param("suspensionState") String suspensionState,
											   @Param("sql_filter") String sql_filter);
}
