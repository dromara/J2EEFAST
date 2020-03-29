package com.fast.framework.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.common.core.base.entity.Ztree;
import com.fast.framework.sys.entity.SysMenuEntity;
import com.fast.framework.sys.entity.SysRoleEntity;

/**
 * 菜单管理
 */
public interface SysMenuService extends IService<SysMenuEntity> {


	/**
	 * 根据父菜单，查询子菜单
	 * 
	 * @param parentId   父菜单ID
	 * @param menuIdList 用户菜单ID
	 */
	List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);


	List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList,String models);

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
	List<SysMenuEntity> queryListParentId(Long parentId,String models);

	/**
	 * 根据角色ID查询菜单
	 *
	 * @param roleId 角色ID
	 * @return 菜单列表
	 */
	List<String> selectMenuTree(Long roleId);

	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenuEntity> queryNotButtonList();

	/**
	 * 获取用户菜单列表
	 */
	List<SysMenuEntity> getUserMenuList(Long userId);

	/**
	 * 获取用户菜单列表
	 */
	List<SysMenuEntity> getUserModuleMenuList(Long userId, String modules);

	/**
	 * 删除
	 */
	void delete(Long menuId);


	/**
	 * 根据角色ID查询菜单
	 *
	 * @param role 角色对象
	 * @param userId 用户ID
	 * @return 菜单列表
	 */
	List<Ztree> roleMenuTreeData(SysRoleEntity role, Long userId);


	/**
	 * 根据角色ID 模块查询菜单
	 *
	 * @param role 角色对象
	 * @param userId 用户ID
	 * @return 菜单列表
	 */
	List<Ztree> roleModuleMenuTreeData(SysRoleEntity role, Long userId);


	/**
	 * 查询所有菜单信息
	 *
	 * @param userId 用户ID
	 * @return 菜单列表
	 */
	public List<Ztree> menuTreeData(Long userId);

	/**
	 * 查询菜单集合
	 *
	 * @param userId 用户ID
	 * @return 所有菜单信息
	 */
	List<SysMenuEntity> selectMenuAll(Long userId);


	/**
	 * 查询菜单集合
	 *
	 * @param userId 用户ID
	 * @return 所有菜单信息
	 */
	List<SysMenuEntity> selectModuleMenuAll(Long userId ,String moduleCodes);

	/**
	 * 根据用户ID查询菜单
	 *
	 * @param userId 用户ID
	 * @return 菜单列表
	 */
	List<SysMenuEntity> selectMenuAllByUserId(Long userId);

	/**
	 * 校验菜单名称是否唯一
	 *
	 * @param menu 菜单信息
	 * @return 结果
	 */
	boolean checkMenuNameUnique(SysMenuEntity menu);


	SysMenuEntity selectMenuById(Long menuId);


	int updateMenu(SysMenuEntity menu);


	List<SysMenuEntity> selectMenuList(SysMenuEntity menu, Long userId);

}
