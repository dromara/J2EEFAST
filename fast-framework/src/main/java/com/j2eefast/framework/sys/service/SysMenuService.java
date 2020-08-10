package com.j2eefast.framework.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.base.entity.LoginUserEntity;
import com.j2eefast.common.core.base.entity.Ztree;
import com.j2eefast.common.core.constants.Cache;
import com.j2eefast.common.core.utils.RedisUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.sys.entity.SysMenuEntity;
import com.j2eefast.framework.sys.entity.SysModuleEntity;
import com.j2eefast.framework.sys.entity.SysRoleEntity;
import com.j2eefast.framework.sys.mapper.SysMenuMapper;
import com.j2eefast.framework.sys.mapper.SysModuleMapper;
import com.j2eefast.framework.sys.mapper.SysUserMapper;
import com.j2eefast.framework.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 菜单管理
 * @author zhouzhou
 */
@Service
public class SysMenuService  extends ServiceImpl<SysMenuMapper, SysMenuEntity> {

	@Resource
	private SysUserMapper sysUserMapper;

	@Resource
	private SysMenuMapper sysMenuMapper;

	@Resource
	private RedisUtil redisUtil;
	/**
	 * 获取所有菜单
	 * @param menu
	 * @param user
	 * @return
	 */
	public List<SysMenuEntity> findMenuList(SysMenuEntity menu, LoginUserEntity user) {
		List<SysMenuEntity> menuList = null;
		if (user.getId().equals(Constant.SUPER_ADMIN) || user.getRoleKey().contains(Constant.SU_ADMIN)){
			menuList = sysMenuMapper.findMenuList(menu);
		}
		else{
			menuList = sysMenuMapper.findMenuListByUserId(user.getId(),menu.getName(),menu.getHide());
		}
		return menuList;
	}


	/**
	 * 通过菜单ID查询菜单
	 * @param menuId
	 * @return
	 */
	public SysMenuEntity selectMenuByMenId(Long menuId){
		return sysMenuMapper.selectMenuByMenId(menuId);
	}

	public List<SysMenuEntity> findUserMenuList(Long userId) {
		// 系统管理员，拥有最高权限
		if (userId.equals(Constant.SUPER_ADMIN)) {
			return getAllMenuList(null);
		}

		// 用户菜单列表
		List<Long> menuIdList = sysUserMapper.findAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}

	public List<SysMenuEntity> findUserModuleMenuList(Long userId, String modules,boolean flag) {
		// 系统管理员，拥有最高权限
		if (userId.equals(Constant.SUPER_ADMIN) || flag) {
			return findAllModelMenuList(null,modules);
		}
		// 用户菜单列表
		List<Long> menuIdList = sysUserMapper.findAllMenuId(userId);
		return findAllModelMenuList(menuIdList,modules);
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenuEntity> findAllModelMenuList(List<Long> menuIdList,String models) {
		// 查询根菜单列表
		List<SysMenuEntity> menuList = findListParentId(0L, menuIdList,models);
		// 递归获取子菜单
		getModuleMenuTreeList(menuList, menuIdList,models);

		return menuList;
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList) {
		// 查询根菜单列表
		List<SysMenuEntity> menuList = findListParentId(0L, menuIdList);
		// 递归获取子菜单
		getMenuTreeList(menuList, menuIdList);

		return menuList;
	}

	public List<SysMenuEntity> findListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenuEntity> menuList = sysMenuMapper.findListParentId(parentId);
		if (menuIdList == null) {
			return menuList;
		}

		List<SysMenuEntity> userMenuList = new ArrayList<>();
		for (SysMenuEntity menu : menuList) {
			if (menuIdList.contains(menu.getId())) {
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	public List<SysMenuEntity> findListParentId(Long parentId, List<Long> menuIdList, String models) {
		List<SysMenuEntity> menuList = sysMenuMapper.findListmoduleParentId(parentId,models);
		if (menuIdList == null) {
			for (SysMenuEntity menu : menuList) {
				menu.setMId(Base64.encode(models+menu.getId()));
				/*
				 *判断是否市新的
				 */
				if(ToolUtil.isNotEmpty(menu.getCreateTime())){
					menu.setMNew(DateUtil.betweenDay(menu.getCreateTime(),new Date(),true) <= 7);
				}else{
					menu.setMNew(false);
				}
			}
			return menuList;
		}

		List<SysMenuEntity> userMenuList = new ArrayList<>();
		for (SysMenuEntity menu : menuList) {
			if (menuIdList.contains(menu.getId())) {
				menu.setMId(Base64.encode(models+menu.getId()));
				/*
				 *判断是否市新的
				 */
				if(ToolUtil.isNotEmpty(menu.getCreateTime())){
					menu.setMNew(DateUtil.betweenDay(menu.getCreateTime(),new Date(),true) <= 7);
				}else{
					menu.setMNew(false);
				}
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}



	/**
	 * 校验菜单
	 * @param menu
	 * @return
	 */
	public boolean checkMenuNameUnique(SysMenuEntity menu) {
		Long menuId = ToolUtil.isEmpty(menu.getId()) ? -1L : menu.getId();
		SysMenuEntity info = this.sysMenuMapper.checkMenuNameUnique(menu.getName(), menu.getParentId());
		if (!ToolUtil.isEmpty(info) && info.getId().longValue() != menuId.longValue()) {
			return  false;
		}
		return true;
	}

	public List<Ztree> roleModuleMenuTreeData(SysRoleEntity role, LoginUserEntity user) {
		Long roleId = role.getId();
		List<Ztree> ztrees = new ArrayList<Ztree>();
		String modules = StrUtil.EMPTY;
		if(ToolUtil.isEmpty(role.getModuleCodes())){
			for(Map<String, Object> s: user.getModules()){
				modules = modules+s.get("moduleCode")+StrUtil.COMMA;
			}
			modules = modules.substring(0,modules.length()-1);
		}else{
			modules = role.getModuleCodes();
		}
		List<SysMenuEntity> menuList = selectModuleMenuAll(user,modules);
		if (!ToolUtil.isEmpty(roleId)){
			List<String> roleMenuList = selectMenuTree(roleId);
			ztrees = initZtree(menuList, roleMenuList, true);
		}
		else {
			ztrees = initZtree(menuList, null, true);
		}
		return ztrees;
	}

	public List<SysMenuEntity> selectModuleMenuAll(LoginUserEntity user, String moduleCodes) {
		List<SysMenuEntity> menuList = null;
		if (user.getId().equals(Constant.SUPER_ADMIN) || user.getRoleKey().contains(Constant.SU_ADMIN)) {
			menuList = this.sysMenuMapper.findModuleMenuAll(moduleCodes);
		}
		else {
			menuList = this.sysMenuMapper.findMenuAllByUserIdModelId(user.getId(),moduleCodes);
		}
		return menuList;
	}

	public List<Ztree> menuTreeData(LoginUserEntity user) {
		List<SysMenuEntity> menuList = selectMenuAll(user);
		List<Ztree> ztrees = initZtree(menuList);
		return ztrees;
	}

	/**
	 * 对象转菜单树
	 *
	 * @param menuList 菜单列表
	 * @return 树结构列表
	 */
	public List<Ztree> initZtree(List<SysMenuEntity> menuList){
		return initZtree(menuList, null, false);
	}


	public List<SysMenuEntity> findNotButtonList() {
		return this.sysMenuMapper.findNotButtonList();
	}

	/**
	 * 根居父ID查询
	 * @param parentId
	 * @return
	 */
	public List<SysMenuEntity> findListParentId(Long parentId) {
		return sysMenuMapper.findListParentId(parentId);
	}

	public List<SysMenuEntity> findListmoduleParentId(Long parentId, String models) {
		return sysMenuMapper.findListmoduleParentId(parentId,models);
	}

	/**
	 * 清空系统参数redis 缓存
	 * @return
	 */
	public boolean clearMenuRedis(){
		return  redisUtil.deletes(Cache.MENU_CONSTANT+"*");
	}

	/**
	 * 根居权限查询菜单树
	 * @param role
	 * @param user
	 * @return
	 */
	public List<Ztree> roleMenuTreeData(SysRoleEntity role, LoginUserEntity user) {
		Long roleId = role.getId();
		List<Ztree> ztrees = new ArrayList<Ztree>();
		List<SysMenuEntity> menuList = selectMenuAll(user);
		if (!ToolUtil.isEmpty(roleId)) {
			List<String> roleMenuList = selectMenuTree(roleId);
			ztrees = initZtree(menuList, roleMenuList, true);
		}
		else{
			ztrees = initZtree(menuList, null, true);
		}
		return ztrees;
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
			ztree.setId(menu.getId());
			ztree.setpId(menu.getParentId());
			ztree.setName(menu.getName());
			ztree.setTitle(transMenuName(menu, permsFlag));
			if (isCheck)
			{
				ztree.setChecked(roleMenuList.contains(menu.getId() + menu.getPerms()));
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
			sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + StrUtil.nullToDefault(menu.getPerms(),"") + "</font>");
		}
		return sb.toString();
	}

	public List<String> selectMenuTree(Long roleId) {
		return this.sysMenuMapper.findMenuTree(roleId);
	}

	public List<SysMenuEntity> selectMenuAll(LoginUserEntity user){
		List<SysMenuEntity> menuList = null;
		if (user.getId().equals(Constant.SUPER_ADMIN) || user.getRoleKey().contains(Constant.SU_ADMIN)){
			menuList = this.list(new QueryWrapper<SysMenuEntity>().
					orderByAsc("parent_id","order_num"));
		}
		else{
			menuList = this.sysMenuMapper.findMenuAllByUserId(user.getId());
		}
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
				entity.setChildren(getMenuTreeList(findListParentId(entity.getId(), menuIdList), menuIdList));
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
				entity.setChildren(getModuleMenuTreeList(findListParentId(entity.getId(), menuIdList,models), menuIdList,models));
			}
			subMenuList.add(entity);
		}

		return subMenuList;
	}

}
