package com.fast.framework.sys.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.framework.sys.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 用户与角色对应关系
 */
public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity> {

	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);

	/**
	 * 根据用户ID数组，批量删除
	 */
	int deleteByUserIdBatch(Long[] userIds);


	int deleteRoleIdByToUserIdsBatch(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);


	/**
	 * 批量新增用户角色信息
	 *
	 * @param userRoleList 用户角色列表
	 * @return 结果
	 */
	int batchUserRole(List<SysUserRoleEntity> userRoleList);


}
