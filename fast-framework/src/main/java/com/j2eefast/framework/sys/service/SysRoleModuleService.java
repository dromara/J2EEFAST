package com.j2eefast.framework.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.framework.sys.entity.SysRoleModuleEntity;
import com.j2eefast.framework.sys.mapper.SysRoleModuleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色与模块对应关系
 * @author zhouzhou
 */
@Service
public class SysRoleModuleService extends ServiceImpl<SysRoleModuleMapper, SysRoleModuleEntity> {

    @Resource
    private SysRoleModuleMapper sysRoleModuleMapper;

    public List<String> findRoleModuleList(Long roleId) {
        return sysRoleModuleMapper.findRoleModuleList(roleId);
    }

    public boolean deleteBatchByRoleIds(Long[] roleIds) {
        return this.remove(new QueryWrapper<SysRoleModuleEntity>().in("role_id",roleIds));
    }

    public boolean deleRoleModule(Long roleId) {
        return this.remove(new QueryWrapper<SysRoleModuleEntity>().eq("role_id",roleId));
    }

}
