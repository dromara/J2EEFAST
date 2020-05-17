package com.j2eefast.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j2eefast.framework.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>角色Mapper接口</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-02 13:47
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {


    Page<SysRoleEntity> findPage(IPage<?> params,
                                 @Param("roleName") String roleName,
                                 @Param("roleKey") String roleKey,
                                 @Param("sql_filter") String sql_filter);

    /**
     * 通过用户ID获取角色
     * @param userId
     * @return
     */
    List<SysRoleEntity> getRolesByUserId(Long userId);


    /**
     * 检测角色Key 唯一性
     * @param roleKey
     * @return
     */
    SysRoleEntity checkRoleKeyUnique(String roleKey);

    /**
     * 检测角色名称 唯一性
     * @param roleName
     * @return
     */
    SysRoleEntity checkRoleNameUnique(String roleName);



    List<SysRoleEntity> findRoleByIds(Long[] roleId);

}
