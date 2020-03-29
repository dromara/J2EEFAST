package com.fast.system.oss.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.common.core.utils.PageUtil;
import com.fast.system.oss.entity.SysOssEntity;

/**
 * 文件上传
 */
public interface SysOssService extends IService<SysOssEntity> {

	PageUtil queryPage(Map<String, Object> params);
}
