package com.j2eefast.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2eefast.framework.sys.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户与角色对应关系 Mapper 接口
 * @author zhouzhou
 * @date 2020-03-08 21:20
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRoleEntity> {

	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> findRoleIdList(@Param("userId") Long userId);


	/**
	 * 根居权限批量删除用户
	 * @param roleId 权限id
	 * @param userIds 用户ids
	 * @return
	 */
	int deleteRoleIdByToUserIdsBatch(@Param("roleId") Long roleId,
									 @Param("userIds") Long[] userIds);

}
