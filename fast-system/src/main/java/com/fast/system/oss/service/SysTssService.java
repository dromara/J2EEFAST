package com.fast.system.oss.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.common.core.utils.PageUtil;
import com.fast.system.oss.entity.SysTssEntity;

public interface SysTssService extends IService<SysTssEntity> {
	PageUtil queryPage(Map<String, Object> params);

	SysTssEntity selectByVs(String version);
}
