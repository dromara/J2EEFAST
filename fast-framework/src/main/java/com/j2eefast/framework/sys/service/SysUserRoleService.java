package com.j2eefast.framework.sys.service;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.utils.MapUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.sys.entity.SysUserRoleEntity;
import com.j2eefast.framework.sys.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户与角色对应关系
 * @author zhouzhou
 */
@Service
public class SysUserRoleService  extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> {

	@Resource
	private SysUserRoleMapper sysUserRoleMapper;

	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		// 先删除用户与角色关系
		this.removeByMap(new MapUtil().put("user_id", userId));

		if (ToolUtil.isEmpty(roleIdList)) {
			return;
		}

		// 保存用户与角色关系
//		List<SysUserRoleEntity> list = new ArrayList<>(roleIdList.size());
		for (Long roleId : roleIdList) {
			SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);
//			list.add(sysUserRoleEntity);
			this.save(sysUserRoleEntity);
		}
	}

	public List<Long> findRoleIdList(Long userId) {
		return sysUserRoleMapper.findRoleIdList(userId);
	}

	/**
	 * 根居权限id删除
	 * @param roleIds
	 * @return
	 */
	public boolean deleteBatchByRoleIds(Long[] roleIds) {
		return this.remove(new QueryWrapper<SysUserRoleEntity>().in("role_id",roleIds));
	}

	/**
	 * 根居用户id删除
	 * @param userIds
	 * @return
	 */
	public boolean deleteBatchByUserIds(Long[] userIds) {
		return this.remove(new QueryWrapper<SysUserRoleEntity>().in("user_id",userIds));
	}

	/**
	 * 根居权限id 删除指定用户
	 * @param roleId
	 * @param userIds
	 * @return
	 */
	public boolean deleteByUserIdToRoleIdsBatch(Long roleId, Long[] userIds) {
		return sysUserRoleMapper.deleteRoleIdByToUserIdsBatch(roleId,userIds) > 0;
	}


	/**
	 * 角色批量授权用户插入
	 * @param roleId
	 * @param userIds
	 * @return
	 */
	public boolean addAuthUsers(Long roleId,  Long[] userIds) {
		for(Long userId: userIds){
			SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);
			this.save(sysUserRoleEntity);
		}
		return true;
	}

	/**
	 * 用户批量授权角色插入
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean addUserAuths(Long userId, Long[] roleIds) {
		// 先删除用户与角色关系
		this.removeByMap(new MapUtil().put("user_id", userId));
		List<SysUserRoleEntity> list = new ArrayList<SysUserRoleEntity>();
		for (Long roleId : roleIds) {
			SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);
			this.save(sysUserRoleEntity);
		}
		return true;
	}
}
