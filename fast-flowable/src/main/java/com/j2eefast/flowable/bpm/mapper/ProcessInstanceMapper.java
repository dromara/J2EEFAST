package com.j2eefast.flowable.bpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j2eefast.flowable.bpm.entity.ProcessInstanceEntity;
import org.apache.ibatis.annotations.Param;


public interface ProcessInstanceMapper extends BaseMapper<ProcessInstanceEntity> {

	/**
	 * 页面查询流程实例分页
	 * @return
	 */
	Page<ProcessInstanceEntity> findPage(IPage<?> params,
										 @Param("start_userId") String startUserId,
										 @Param("start_name") String startName,
										 @Param("name") String name,
										 @Param("sql_filter") String sql_filter);


}
