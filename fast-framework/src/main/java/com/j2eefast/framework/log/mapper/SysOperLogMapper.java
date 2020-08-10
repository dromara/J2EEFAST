package com.j2eefast.framework.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j2eefast.framework.log.entity.SysOperLogEntity;
import org.apache.ibatis.annotations.Param;

/**
 * <p> 操作日志 Mapper 接口</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2019-03-20 16:43
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public interface SysOperLogMapper extends BaseMapper<SysOperLogEntity> {
	/**
	 * 清空所有日志
	 */
	int cleanLog();

	Page<SysOperLogEntity> findPage(IPage<?> params,
									  @Param("title") String title,
									  @Param("operName") String operName,
									  @Param("status") String status,
									  @Param("beginTime") String beginTime,
									  @Param("endTime") String endTime,
									  @Param("businessTypes") String businessTypes,
									  @Param("deptId") String deptId,
									  @Param("sql_filter") String sql_filter);
}
