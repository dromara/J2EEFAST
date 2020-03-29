package com.fast.framework.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.framework.sys.service.SysRoleMenuService;
import com.fast.framework.sys.dao.SysRoleMenuDao;
import com.fast.framework.sys.entity.SysRoleMenuEntity;

/**
 * 角色与菜单对应关系
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity>
		implements SysRoleMenuService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		// 先删除角色与菜单关系
		deleteBatch(new Long[] { roleId });

		if (menuIdList.size() == 0) {
			return;
		}

		// 保存角色与菜单关系
		List<SysRoleMenuEntity> list = new ArrayList<>(menuIdList.size());
		for (Long menuId : menuIdList) {
			SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
			sysRoleMenuEntity.setMenuId(menuId);
			sysRoleMenuEntity.setRoleId(roleId);

			list.add(sysRoleMenuEntity);
		}
		this.saveBatch(list);
	}

	@Override
	public List<Long> queryMenuIdList(Long roleId) {
		return baseMapper.queryMenuIdList(roleId);
	}

	@Override
	public int deleteBatch(Long[] roleIds) {
		return baseMapper.deleteBatch(roleIds);
	}

}
