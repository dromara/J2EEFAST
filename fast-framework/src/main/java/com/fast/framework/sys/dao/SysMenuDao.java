package com.fast.framework.sys.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.framework.sys.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 菜单管理
 */
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {

	/**
	 * 根据父菜单，查询子菜单
	 * 
	 * @param parentId 父菜单ID
	 */
	List<SysMenuEntity> queryListParentId(Long parentId);


	/**
	 * 根据父菜单，查询子菜单
	 *
	 * @param parentId 父菜单ID
	 */
	List<SysMenuEntity> queryListmoduleParentId(@Param("parentId") Long parentId, @Param("modules") String modules);

	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenuEntity> queryNotButtonList();

	/**
	 * 根据角色ID查询菜单
	 *
	 * @param roleId 角色ID
	 * @return 菜单列表
	 */
	 List<String> selectMenuTree(Long roleId);


	/**
	 * 查询系统所有菜单（含按钮）
	 *
	 * @return 菜单列表
	 */
	List<SysMenuEntity> selectMenuAll();


	/**
	 * 查询系统所有菜单（含按钮）
	 *
	 * @return 菜单列表
	 */
	List<SysMenuEntity> selectModuleMenuAll(@Param("moduleCodes") String moduleCodes);

	/**
	 * 根据用户ID查询菜单
	 *
	 * @param userId 用户ID
	 * @return 菜单列表
	 */
	List<SysMenuEntity> selectMenuAllByUserId(@Param("userdId") Long userI);


	/**
	 * 根据用户ID 及用户选择模块 查询菜单
	 *
	 * @param userId 用户ID
	 * @return 菜单列表
	 */
	List<SysMenuEntity> selectMenuAllByUserIdModelId(@Param("userdId") Long userdId, @Param("moduleCodes") String moduleCodes);

	/**
	 * 校验菜单名称是否唯一
	 *
	 * @param name 菜单名称
	 * @param parentId 父菜单ID
	 * @return 结果
	 */
	SysMenuEntity checkMenuNameUnique(@Param("name") String name, @Param("parentId") Long parentId);



	/**
	 * 根据菜单ID查询信息
	 *
	 * @param menuId 菜单ID
	 * @return 菜单信息
	 */
	SysMenuEntity selectMenuById(Long menuId);

	/**
	 * 修改菜单信息
	 *
	 * @param menu 菜单信息
	 * @return 结果
	 */
	int updateMenu(SysMenuEntity menu);


	List<SysMenuEntity> selectMenuList(SysMenuEntity menu);


	/**
	 * 查询系统菜单列表
	 *
	 * @return 菜单列表
	 */
	List<SysMenuEntity> selectMenuListByUserId(@Param("userId") Long userId,@Param("name") String name,@Param("hide") Integer hide);

}
