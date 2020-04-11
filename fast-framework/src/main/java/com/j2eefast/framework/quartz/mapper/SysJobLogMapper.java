package com.j2eefast.framework.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2eefast.framework.quartz.entity.SysJobLogEntity;

/**
 * 定时任务日志 Mapper 接口
 * @author zhouzhou
 * @date 2020-03-08 15:36
 */
public interface SysJobLogMapper extends BaseMapper<SysJobLogEntity>{
	
	/**
	 * 清空定时任务日志表
	 */
	int cleanJobLog();
}
