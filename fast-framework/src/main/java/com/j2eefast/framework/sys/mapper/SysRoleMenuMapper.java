package com.j2eefast.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2eefast.framework.sys.entity.SysRoleMenuEntity;

import java.util.List;

/**
 * <p>角色菜单关系 Mapper接口</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-02 13:47
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuEntity> {

	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> findMenuIdList(Long roleId);

}
