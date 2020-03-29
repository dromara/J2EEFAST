package com.fast.framework.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.framework.sys.entity.SysRoleEntity;

import java.util.List;

/**
 * 角色管理
 */
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {
    /**
     * 校验角色名称是否唯一
     *
     * @param roleName 角色名称
     * @return 角色信息
     */
    SysRoleEntity checkRoleNameUnique(String roleName);

    /**
     * 校验角色权限是否唯一
     *
     * @param roleKey 角色权限
     * @return 角色信息
     */
    SysRoleEntity checkRoleKeyUnique(String roleKey);


    int updateRole(SysRoleEntity role);


    List<SysRoleEntity> selectRolesByUserId(Long userId);

    List<SysRoleEntity> selectRoleById(Long[] roleId);

}
