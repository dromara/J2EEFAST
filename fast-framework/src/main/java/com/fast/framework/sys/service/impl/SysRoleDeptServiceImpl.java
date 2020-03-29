package com.fast.framework.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.framework.sys.service.SysRoleDeptService;
import com.fast.framework.sys.dao.SysRoleDeptDao;
import com.fast.framework.sys.entity.SysRoleDeptEntity;

/**
 * 角色与部门对应关系
 */
@Service("sysRoleDeptService")
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptDao, SysRoleDeptEntity>
		implements SysRoleDeptService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
		// 先删除角色与部门关系
		deleteBatch(new Long[] { roleId });

		if (deptIdList.size() == 0) {
			return;
		}

		// 保存角色与菜单关系
		List<SysRoleDeptEntity> list = new ArrayList<>(deptIdList.size());
		for (Long deptId : deptIdList) {
			SysRoleDeptEntity sysRoleDeptEntity = new SysRoleDeptEntity();
			sysRoleDeptEntity.setDeptId(deptId);
			sysRoleDeptEntity.setRoleId(roleId);

			list.add(sysRoleDeptEntity);
		}
		this.saveBatch(list);
	}

	@Override
	public List<Long> queryDeptIdList(Long[] roleIds) {
		return baseMapper.queryDeptIdList(roleIds);
	}

	@Override
	public int deleteBatch(Long[] roleIds) {
		return baseMapper.deleteBatch(roleIds);
	}
}
