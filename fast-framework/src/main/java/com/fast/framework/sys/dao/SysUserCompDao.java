package com.fast.framework.sys.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.framework.sys.entity.SysUserCompEntity;

public interface SysUserCompDao extends BaseMapper<SysUserCompEntity> {
	
	/**
	 * 根据用户ID，获取sys_comp ID列表
	 */
	List<Long> queryCompIdList(Long userId);

	/**
	 * 根据用户ID数组，批量删除
	 */
	int deleteByUserIdBatch(Long[] userIds);
}
