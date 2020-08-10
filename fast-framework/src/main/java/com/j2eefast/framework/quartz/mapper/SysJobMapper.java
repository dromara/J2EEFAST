package com.j2eefast.framework.quartz.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2eefast.framework.quartz.entity.SysJobEntity;

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
					@Param("ids") Long[] ids);

	int updateStatus(@Param("status") String status,
					  @Param("id") Long id);
	/**
	 * 根据Id删除
	 * @param id
	 * @return
	 */
	int deleteJobLogById(Long id);

	/**
	 * 更新
	 * @author zhouzhou
	 * @date 2020-03-08 16:41
	 */
//	int updateSysJob(SysJobEntity sysJob);
}
