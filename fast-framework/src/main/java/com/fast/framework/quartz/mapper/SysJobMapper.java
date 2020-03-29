package com.fast.framework.quartz.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.framework.quartz.entity.SysJobEntity;

/**
 * 定时任务 Mapper 接口
 * @author zhouzhou
 * @date 2020-03-08 15:36
 */
public interface SysJobMapper extends BaseMapper<SysJobEntity>{
	
	/**
	 * 批量更新状态
	 */
	int updateBatchStatus(@Param("status") String status,
					@Param("jobIds") Long[] jobIds);
	
	/**
	 * 更新
	 * @author zhouzhou
	 * @date 2020-03-08 16:41
	 */
//	int updateSysJob(SysJobEntity sysJob);
}
