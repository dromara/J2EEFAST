package com.fast.framework.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.framework.log.entity.SysLoginInfoEntity;

/**
 *  登陆日志 Mapper 接口
 * @author zhouzhou
 * @date 2018-03-13 15:00
 */
public interface SysLoginInfoMapper extends BaseMapper<SysLoginInfoEntity>{
	
	/**
	 * 清空所有日志
	 */
	int cleanLog();
}
