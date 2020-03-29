package com.fast.framework.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.framework.sys.entity.SysUserRoleEntity;

/**
 * 用户与角色对应关系
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {

	void saveOrUpdate(Long userId, List<Long> roleIdList);

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

	int deleteByUserIdToRoleIdsBatch(Long roleId,Long[] userIds);

	/**
	 * 批量新增用户角色信息
	 *
	 */
	boolean insertAuthUsers(Long roleId,  Long[] userIds);

	/**
	 * 批量新增用户角色信息
	 *
	 */
	boolean insertUserAuths(Long userId,  Long[] roleIds);

//	List<SysUserRoleEntity> selectRoleById(Long[] roleId);
}
