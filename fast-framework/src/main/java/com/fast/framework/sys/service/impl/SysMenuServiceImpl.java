package com.fast.framework.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fast.framework.sys.entity.SysRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.framework.sys.service.SysMenuService;
import com.fast.framework.sys.service.SysRoleMenuService;
import com.fast.framework.sys.service.SysUserService;
import com.fast.framework.utils.Constant;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import com.fast.common.core.base.entity.Ztree;
import com.fast.common.core.utils.MapUtil;
import com.fast.common.core.utils.ToolUtil;
import com.fast.framework.sys.dao.SysMenuDao;
import com.fast.framework.sys.entity.SysMenuEntity;

@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenuEntity> menuList = queryListParentId(parentId);
		if (menuIdList == null) {
			return menuList;
		}

		List<SysMenuEntity> userMenuList = new ArrayList<>();
		for (SysMenuEntity menu : menuList) {
			if (menuIdList.contains(menu.getMenuId())) {
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList, String models) {
		List<SysMenuEntity> menuList = queryListParentId(parentId,models);
		if (menuIdList == null) {
			for (SysMenuEntity menu : menuList) {
				menu.setmId(Base64.encode(models+menu.getMenuId()));
				/*
				 *判断是否市新的
				 */
				if(ToolUtil.isNotEmpty(menu.getCreateTime())){
					menu.setmNew(DateUtil.betweenDay(menu.getCreateTime(),new Date(),true) <= 7);
				}else{
					menu.setmNew(false);
				}
			}
			return menuList;
		}

		List<SysMenuEntity> userMenuList = new ArrayList<>();
		for (SysMenuEntity menu : menuList) {
			if (menuIdList.contains(menu.getMenuId())) {
				menu.setmId(Base64.encode(models+menu.getMenuId()));
				/*
				 *判断是否市新的
				 */
				if(ToolUtil.isNotEmpty(menu.getCreateTime())){
					menu.setmNew(DateUtil.betweenDay(menu.getCreateTime(),new Date(),true) <= 7);
				}else{
					menu.setmNew(false);
				}
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}


	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId) {
		return baseMapper.queryListParentId(parentId);
	}

	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId, String models) {
		return baseMapper.queryListmoduleParentId(parentId,models);
	}


	@Override
	public List<String> selectMenuTree(Long roleId) {
		return this.baseMapper.selectMenuTree(roleId);
	}

	@Override
	public List<SysMenuEntity> queryNotButtonList() {
		return baseMapper.queryNotButtonList();
	}

	/**
	 * 查询菜单集合
	 *
	 * @return 所有菜单信息
	 */
	@Override
	public List<SysMenuEntity> getUserMenuList(Long userId) {
		// 系统管理员，拥有最高权限
		if (userId.equals(Constant.SUPER_ADMIN)) {
			return getAllMenuList(null);
		}

		// 用户菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}

	@Override
	public List<SysMenuEntity> getUserModuleMenuList(Long userId, String modules) {
		// 系统管理员，拥有最高权限
		if (userId.equals(Constant.SUPER_ADMIN)) {
			//return this.baseMapper.queryListmoduleParentId(null);
			return getAllModelMenuList(null,modules);
		}
		// 用户菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllModelMenuList(menuIdList,modules);
	}


	/**
	 * 查询系统所有菜单（含按钮）
	 *
	 * @return 所有菜单信息
	 */
	@Override
	public List<SysMenuEntity> selectMenuAll(Long userId)
	{
		List<SysMenuEntity> menuList = null;
		if (userId.equals(Constant.SUPER_ADMIN))
		{
			menuList = this.baseMapper.selectMenuAll();
		}
		else
		{
			menuList = selectMenuAllByUserId(userId);
		}
		return menuList;
	}

	@Override
	public List<SysMenuEntity> selectModuleMenuAll(Long userId, String moduleCodes) {
		List<SysMenuEntity> menuList = null;
		if (userId.equals(Constant.SUPER_ADMIN))
		{
			menuList = this.baseMapper.selectModuleMenuAll(moduleCodes);
		}
		else
		{
			menuList = this.baseMapper.selectMenuAllByUserIdModelId(userId,moduleCodes);
		}
		return menuList;
	}

	@Override
	public void delete(Long menuId) {
		// 删除菜单
		this.removeById(menuId);
		// 删除菜单与角色关联
		sysRoleMenuService.removeByMap(new MapUtil().put("menu_id", menuId));
	}

	@Override
	public List<Ztree> roleMenuTreeData(SysRoleEntity role, Long userId) {
		Long roleId = role.getRoleId();
		List<Ztree> ztrees = new ArrayList<Ztree>();
		List<SysMenuEntity> menuList = selectMenuAll(userId);
		if (!ToolUtil.isEmpty(roleId))
		{
			List<String> roleMenuList = selectMenuTree(roleId);
			ztrees = initZtree(menuList, roleMenuList, true);
		}
		else
		{
			ztrees = initZtree(menuList, null, true);
		}
		return ztrees;
	}

	@Override
	public List<Ztree> roleModuleMenuTreeData(SysRoleEntity role, Long userId) {
		Long roleId = role.getRoleId();
		List<Ztree> ztrees = new ArrayList<Ztree>();
		List<SysMenuEntity> menuList = selectModuleMenuAll(userId,role.getModuleCodes());
		if (!ToolUtil.isEmpty(roleId))
		{
			List<String> roleMenuList = selectMenuTree(roleId);
			ztrees = initZtree(menuList, roleMenuList, true);
		}
		else
		{
			ztrees = initZtree(menuList, null, true);
		}
		return ztrees;
	}

	@Override
	public List<Ztree> menuTreeData(Long userId) {
		List<SysMenuEntity> menuList = selectMenuAll(userId);
		List<Ztree> ztrees = initZtree(menuList);
		return ztrees;
	}

	/**
	 * 对象转菜单树
	 *
	 * @param menuList 菜单列表
	 * @return 树结构列表
	 */
	public List<Ztree> initZtree(List<SysMenuEntity> menuList)
	{
		return initZtree(menuList, null, false);
	}


	@Override
	public List<SysMenuEntity> selectMenuAllByUserId(Long userId) {
		return this.baseMapper.selectMenuAllByUserId(userId);
	}

	@Override
	public boolean checkMenuNameUnique(SysMenuEntity menu) {
		Long menuId =ToolUtil.isEmpty(menu.getMenuId()) ? -1L : menu.getMenuId();
		SysMenuEntity info = this.baseMapper.checkMenuNameUnique(menu.getName(), menu.getParentId());
		if (!ToolUtil.isEmpty(info) && info.getMenuId().longValue() != menuId.longValue())
		{
			return  false;
		}
		return true;
	}

	@Override
	public SysMenuEntity selectMenuById(Long menuId) {
		return this.baseMapper.selectMenuById(menuId);
	}

	@Override
	public int updateMenu(SysMenuEntity menu) {
		return this.baseMapper.updateMenu(menu);
	}

	@Override
	public List<SysMenuEntity> selectMenuList(SysMenuEntity menu, Long userId) {
		List<SysMenuEntity> menuList = null;
		if (userId.equals(Constant.SUPER_ADMIN)){
			menuList = this.baseMapper.selectMenuList(menu);
		}
		else{
			menuList = this.baseMapper.selectMenuListByUserId(userId,menu.getName(),menu.getHide());
		}
		return menuList;
	}

	/**
	 * 对象转菜单树
	 *
	 * @param menuList 菜单列表
	 * @param roleMenuList 角色已存在菜单列表
	 * @param permsFlag 是否需要显示权限标识
	 * @return 树结构列表
	 */
	public List<Ztree> initZtree(List<SysMenuEntity> menuList, List<String> roleMenuList, boolean permsFlag)
	{
		List<Ztree> ztrees = new ArrayList<Ztree>();
		boolean isCheck = !ToolUtil.isEmpty(roleMenuList);
		for (SysMenuEntity menu : menuList)
		{
			Ztree ztree = new Ztree();
			ztree.setId(menu.getMenuId());
			ztree.setpId(menu.getParentId());
			ztree.setName(transMenuName(menu, permsFlag));
			ztree.setTitle(menu.getName());
			if (isCheck)
			{
				ztree.setChecked(roleMenuList.contains(menu.getMenuId() + menu.getPerms()));
			}
			ztrees.add(ztree);
		}
		return ztrees;
	}

	public String transMenuName(SysMenuEntity menu, boolean permsFlag)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(menu.getName() + "&nbsp;&nbsp;["+menu.getModuleCodes()+"]");
		if (permsFlag)
		{
			sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + menu.getPerms() + "</font>");
		}
		return sb.toString();
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList) {
		// 查询根菜单列表
		List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
		// 递归获取子菜单
		getMenuTreeList(menuList, menuIdList);

		return menuList;
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenuEntity> getAllModelMenuList(List<Long> menuIdList,String models) {
		// 查询根菜单列表
		List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList,models);
		// 递归获取子菜单
		getModuleMenuTreeList(menuList, menuIdList,models);

		return menuList;
	}


	/**
	 * 递归
	 */
	private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList) {
		List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();

		for (SysMenuEntity entity : menuList) {
			// 目录
			if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {
				entity.setChildren(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}

		return subMenuList;
	}

	/**
	 * 递归
	 */
	private List<SysMenuEntity> getModuleMenuTreeList(List<SysMenuEntity> rootmenuList, List<Long> menuIdList,String models) {
		List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();

		for (SysMenuEntity entity : rootmenuList) {
			// 目录
			if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {
				entity.setChildren(getModuleMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList,models), menuIdList,models));
			}
			subMenuList.add(entity);
		}

		return subMenuList;
	}
}
