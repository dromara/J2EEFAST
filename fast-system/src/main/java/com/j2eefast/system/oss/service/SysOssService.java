package com.j2eefast.system.oss.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.system.oss.entity.SysOssEntity;

/**
 * 文件上传
 */
public interface SysOssService extends IService<SysOssEntity> {

	PageUtil queryPage(Map<String, Object> params);
}
