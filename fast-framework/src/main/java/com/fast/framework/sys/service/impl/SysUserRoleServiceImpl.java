package com.fast.framework.sys.service.impl;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.core.utils.MapUtil;
import com.fast.framework.sys.service.SysUserRoleService;
import com.fast.framework.sys.dao.SysUserRoleDao;
import com.fast.framework.sys.entity.SysUserRoleEntity;

/**
 * 用户与角色对应关系
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity>
		implements SysUserRoleService {
	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		// 先删除用户与角色关系
		this.removeByMap(new MapUtil().put("user_id", userId));

		if (roleIdList == null || roleIdList.size() == 0) {
			return;
		}

		// 保存用户与角色关系
		List<SysUserRoleEntity> list = new ArrayList<>(roleIdList.size());
		for (Long roleId : roleIdList) {
			SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);

			list.add(sysUserRoleEntity);
		}
		this.saveBatch(list);
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return baseMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(Long[] roleIds) {
		return baseMapper.deleteBatch(roleIds);
	}

	@Override
	public int deleteByUserIdBatch(Long[] userIds) {
		return baseMapper.deleteByUserIdBatch(userIds);
	}

	@Override
	public int deleteByUserIdToRoleIdsBatch(Long roleId, Long[] userIds) {
		return this.baseMapper.deleteRoleIdByToUserIdsBatch(roleId,userIds);
	}

	@Override
	public boolean insertAuthUsers(Long roleId,  Long[] userIds) {
		List<SysUserRoleEntity> list = new ArrayList<SysUserRoleEntity>();
		for(Long userId: userIds){
			SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);
			list.add(sysUserRoleEntity);
		}
		return this.saveBatch(list);
	}

	@Override
	public boolean insertUserAuths(Long userId, Long[] roleIds) {
		// 先删除用户与角色关系
		this.removeByMap(new MapUtil().put("user_id", userId));
		List<SysUserRoleEntity> list = new ArrayList<SysUserRoleEntity>();
		for(Long roleId: roleIds){
			SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);
			list.add(sysUserRoleEntity);
		}
		return this.saveBatch(list);
	}

//	@Override
//	public List<SysUserRoleEntity> selectRoleById(Long[] roleId) {
//		return this.selectList(new QueryWrapper<SysUserRoleEntity>().in("role_id",roleId));
//	}
}
