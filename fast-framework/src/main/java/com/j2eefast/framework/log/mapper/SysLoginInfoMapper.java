package com.j2eefast.framework.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j2eefast.framework.log.entity.SysLoginInfoEntity;
import org.apache.ibatis.annotations.Param;

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

	/**
	 * 获取上次登录时间
	 * @param username
	 * @return
	 */
	SysLoginInfoEntity findFirstLoginInfo(String username);

	/**
	 * 前端页面分页
	 * @param params
	 * @param username
	 * @param ipaddr
	 * @param status
	 * @param beginTime
	 * @param endTime
	 * @param deptId
	 * @param sql_filter
	 * @return
	 */
	Page<SysLoginInfoEntity> findPage(IPage<?> params,
									  @Param("username") String username,
									  @Param("ipaddr") String ipaddr,
									  @Param("status") String status,
									  @Param("beginTime") String beginTime,
									  @Param("endTime") String endTime,
									  @Param("deptId") String deptId,
									  @Param("sql_filter") String sql_filter);
}
