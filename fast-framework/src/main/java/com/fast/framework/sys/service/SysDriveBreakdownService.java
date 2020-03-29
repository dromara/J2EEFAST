package com.fast.framework.sys.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.common.core.utils.PageUtil;
import com.fast.framework.sys.entity.SysDriveBreakdownEntity;

public interface SysDriveBreakdownService extends IService<SysDriveBreakdownEntity> {
	/**
	 * 最大用户查询
	 */
	PageUtil queryAllPage(Map<String, Object> params);

	void delete(String machNo);
}
