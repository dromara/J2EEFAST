package com.j2eefast.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2eefast.framework.sys.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 菜单 Mapper 接口</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-07 13:21
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {


	/**
	 * 通过权限获取用户菜单权限
	 * @param roleId
	 * @return
	 */
	List<String> findPermsByRoleId(@Param("roleId") Long roleId);

	/**
	 * 根据角色ID查询菜单
	 *
	 * @param roleId 角色ID
	 * @return 菜单列表
	 */
	List<String> findMenuTree(@Param("roleId") Long roleId);


	/**
	 * 通过模块查询菜单
	 *
	 * @return 菜单列表
	 */
	List<SysMenuEntity> findModuleMenuAll(@Param("moduleCodes") String moduleCodes);

	/**
	 * 根据用户ID 及用户选择模块 查询菜单
	 *
	 * @param userdId 用户ID
	 * @return 菜单列表
	 */
	List<SysMenuEntity> findMenuAllByUserIdModelId(@Param("userdId") Long userdId,
													 @Param("moduleCodes") String moduleCodes);

	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenuEntity> findNotButtonList();


	/**
	 * 根据父菜单，查询子菜单
	 *
	 * @param parentId 父菜单ID
	 */
	List<SysMenuEntity> findListParentId(@Param("parentId") Long parentId);


	/**
	 * 根据父菜单，查询子菜单
	 *
	 * @param parentId 父菜单ID
	 */
	List<SysMenuEntity> findListmoduleParentId(@Param("parentId") Long parentId,
												@Param("modules") String modules);

	/**
	 * 查询所有菜单
	 * @param menu
	 * @return
	 */
	List<SysMenuEntity> findMenuList(SysMenuEntity menu);


	/**
	 * 查询系统菜单列表
	 *
	 * @return 菜单列表
	 */
	List<SysMenuEntity> findMenuListByUserId(@Param("userId") Long userId,
											   @Param("name") String name,
											   @Param("hide") Integer hide);


	/**
	 * 校验菜单名称是否唯一
	 *
	 * @param name 菜单名称
	 * @param parentId 父菜单ID
	 * @return 结果
	 */
	SysMenuEntity checkMenuNameUnique(@Param("name") String name,
									  @Param("parentId") Long parentId);

	/**
	 * 根据用户ID查询菜单
	 *
	 * @param userId 用户ID
	 * @return 菜单列表
	 */
	List<SysMenuEntity> findMenuAllByUserId(@Param("userdId") Long userId);
}
