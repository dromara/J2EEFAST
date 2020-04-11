package com.j2eefast.framework.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2eefast.framework.log.entity.SysOperLogEntity;

/**
 * <p> 操作日志 Mapper 接口</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2019-03-20 16:43
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
public interface SysOperLogMapper extends BaseMapper<SysOperLogEntity> {
	/**
	 * 清空所有日志
	 */
	int cleanLog();
}
