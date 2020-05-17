package com.j2eefast.flowable.bpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j2eefast.flowable.bpm.entity.BpmTaskEntity;
import org.apache.ibatis.annotations.Param;

public interface BpmTaskMapper extends BaseMapper<BpmTaskEntity> {

	/**
	 * 查询待办任务
	 * @param params
	 * @param userId
	 * @param sql_filter
	 * @return
	 */
	Page<BpmTaskEntity> findApplyingTasksPage(IPage<?> params,
											  @Param("userId") String userId,
											  @Param("sql_filter") String sql_filter);


	/**
	 * 查询已办任务
	 * @param params
	 * @param userId
	 * @param sql_filter
	 * @return
	 */
	Page<BpmTaskEntity> findApplyedTasksPage(IPage<?> params,
											  @Param("userId") String userId,
											  @Param("sql_filter") String sql_filter);
}
