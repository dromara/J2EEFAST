package com.j2eefast.framework.sys.service;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.utils.MapUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.sys.entity.SysRoleMenuEntity;
import com.j2eefast.framework.sys.mapper.SysRoleMenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 角色与菜单对应关系
 * @author zhouzhou
 */
@Service
public class SysRoleMenuService extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuEntity> {

	@Resource
	private SysRoleMenuMapper sysRoleMenuMapper;

	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		// 先删除角色与菜单关系
		this.removeByMap(new MapUtil().put("role_id",roleId));

		if (ToolUtil.isEmpty(menuIdList)) {
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

	public List<Long> findMenuIdList(Long roleId) {
		return sysRoleMenuMapper.findMenuIdList(roleId);
	}

	public boolean deleteBatchByRoleIds(Long[] roleIds){
		return this.remove(new QueryWrapper<SysRoleMenuEntity>().in("role_id",roleIds));
	}
}
