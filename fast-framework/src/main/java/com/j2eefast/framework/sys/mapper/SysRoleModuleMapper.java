package com.j2eefast.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2eefast.framework.sys.entity.SysRoleModuleEntity;

import java.util.List;

/**
 * <p>角色模块关系 Mapper接口</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-02 13:47
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
public interface SysRoleModuleMapper extends BaseMapper<SysRoleModuleEntity> {

	List<String> findRoleModuleList(Long roleId);
}
