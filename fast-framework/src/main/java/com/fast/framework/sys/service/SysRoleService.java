package com.fast.framework.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.common.core.utils.PageUtil;
import com.fast.framework.sys.entity.SysRoleEntity;

/**
 * 角色
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageUtil queryPage(Map<String, Object> params);

	void insert(SysRoleEntity role);

	void update(SysRoleEntity role);

	void deleteBatch(Long[] roleIds);

	/**
	 * 校验角色名称是否唯一
	 *
	 * @param role 角色信息
	 * @return 结果
	 */
	 boolean checkRoleNameUnique(SysRoleEntity role);

	/**
	 * 校验角色权限是否唯一
	 *
	 * @param role 角色信息
	 * @return 结果
	 */
	boolean checkRoleKeyUnique(SysRoleEntity role);


	List<SysRoleEntity> selectRolesByUserId(Long userId);

	/**
	 * 角色状态修改
	 *
	 * @param role 用户信息
	 * @return 结果
	 */
	int changeStatus(SysRoleEntity role);


//	List<SysRoleEntity> selectRoleById(Long[] roleId);

}
