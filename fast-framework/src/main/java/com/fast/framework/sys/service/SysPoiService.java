package com.fast.framework.sys.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.common.core.utils.PageUtil;
import com.fast.framework.sys.entity.SysPoiEntity;

public interface SysPoiService extends IService<SysPoiEntity> {
	
	PageUtil queryAllPage(Map<String, Object> params);
}
