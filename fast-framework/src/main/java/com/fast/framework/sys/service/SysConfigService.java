package com.fast.framework.sys.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.common.core.utils.PageUtil;
import com.fast.framework.sys.entity.SysConfigEntity;

/**
 * 系统配置信息
 */
public interface SysConfigService extends IService<SysConfigEntity> {

	PageUtil queryPage(Map<String, Object> params);

	/**
	 * 保存配置信息
	 */
	void insert(SysConfigEntity config);

	/**
	 * 更新配置信息
	 */
	void update(SysConfigEntity config);

	/**
	 * 根据key，更新value
	 */
	void updateValueByKey(String key, String value);

	/**
	 * 删除配置信息
	 */
	void deleteBatch(Long[] ids);

	/**
	 * 根据key，获取配置的value值
	 * 
	 * @param key key
	 */
	String getValue(String key);

	/**
	 * 根据key，获取value的Object对象
	 * 
	 * @param key   key
	 * @param clazz Object对象
	 */
	<T> T getConfigObject(String key, Class<T> clazz);


	boolean checkConfigKeyUnique(SysConfigEntity config);

}
