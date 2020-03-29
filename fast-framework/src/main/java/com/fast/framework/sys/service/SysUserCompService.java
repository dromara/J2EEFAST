package com.fast.framework.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.framework.sys.entity.SysUserCompEntity;

public interface SysUserCompService extends IService<SysUserCompEntity>{
	/**
	 * 根据用户ID，获取sys_comp ID列表
	 */
	List<Long> queryCompIdList(Long userId);

	int deleteByUserIdBatch(Long[] userIds);
}
