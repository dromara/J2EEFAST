package com.fast.framework.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.framework.sys.entity.SysRoleDeptEntity;

/**
 * 角色与部门对应关系
 */
public interface SysRoleDeptService extends IService<SysRoleDeptEntity> {

	void saveOrUpdate(Long roleId, List<Long> deptIdList);

	/**
	 * 根据角色ID，获取部门ID列表
	 */
	List<Long> queryDeptIdList(Long[] roleIds);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
