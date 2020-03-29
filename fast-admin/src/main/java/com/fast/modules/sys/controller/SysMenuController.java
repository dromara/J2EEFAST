package com.fast.modules.sys.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fast.common.core.base.entity.Ztree;
import com.fast.common.core.business.annotaion.BussinessLog;
import com.fast.common.core.enums.BusinessType;
import com.fast.framework.sys.entity.SysModuleEntity;
import com.fast.framework.sys.entity.SysRoleEntity;
import com.fast.framework.sys.service.SysModuleService;
import com.fast.framework.utils.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fast.common.core.exception.RxcException;
import com.fast.common.core.utils.R;
import com.fast.framework.utils.AbstractController;
import com.fast.framework.utils.Constant;
import com.fast.framework.sys.entity.SysMenuEntity;
import com.fast.framework.sys.service.SysMenuService;

/**
 * 系统菜单控制器
 * @author zhouzhou
 * @date 2020-03-07 13:44
 */
@Controller
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {

	private String urlPrefix = "modules/sys/menu";

	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysModuleService sysModuleService;

	@RequiresPermissions("sys:menu:view")
	@GetMapping()
	public String menu(){
		return urlPrefix + "/menu";
	}

	/**
	 * 新增
	 * @author zhouzhou
	 * @date 2020-03-07 14:22
	 */
	@GetMapping("/add/{parentId}")
	public String add(@PathVariable("parentId") Long parentId, ModelMap mmap){
		SysMenuEntity menu = null;
		if (0L != parentId){
			menu = sysMenuService.selectMenuById(parentId);
		}
		else
		{
			menu = new SysMenuEntity();
			menu.setMenuId(0L);
			menu.setName("主目录");
		}
		mmap.put("menu", menu);
		List<SysModuleEntity>  modules = sysModuleService.list();
		mmap.put("modules", modules);
		return urlPrefix + "/add";
	}


	/**
	 * 修改菜单
	 * @author zhouzhou
	 * @date 2020-03-07 14:22
	 */
	@GetMapping("/edit/{menuId}")
	public String edit(@PathVariable("menuId") Long menuId, ModelMap mmap)
	{
		mmap.put("menu", sysMenuService.selectMenuById(menuId));
		List<SysModuleEntity>  modules = sysModuleService.list();
		mmap.put("modules", modules);
		return urlPrefix + "/edit";
	}

	/**
	 * 图标选择
	 * @author zhouzhou
	 * @date 2020-03-07 13:58
	 */
	@GetMapping("/iconSelect")
	public String iconselect(@RequestParam(value="value", required=true)String value,ModelMap mmap)
	{
		mmap.put("iconValue", value);
		return urlPrefix + "/icon";
	}

	/**
	 * 修改
	 * @author zhouzhou
	 * @date 2020-03-07 14:23
	 */
	@BussinessLog(title = "菜单管理", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@RequiresPermissions("sys:menu:edit")
	@ResponseBody
	public R editSave(@Validated SysMenuEntity menu) {
		// 数据校验
		verifyForm(menu);

		if (!sysMenuService.checkMenuNameUnique(menu)){
			return R.error("新增菜单'" + menu.getName()+ "'失败，菜单名称已存在");
		}
		menu.setUpdateBy(getUser().getUsername());

		ShiroUtils.clearCachedAuthorizationInfo(); //清理权限缓存

		if(sysMenuService.updateMenu(menu) > 0){
			return R.ok();
		}else {
			return R.error();
		}
	}

	/**
	 * 导航菜单
	 */
	@RequestMapping("/nav")
	@ResponseBody
	public R nav() {
		List<SysModuleEntity> modules = sysModuleService.selectSysModules(getUser());
		Map<String, List<SysMenuEntity>> menuList = new HashMap<>();
		for(SysModuleEntity s: modules){
			List<SysMenuEntity> menu = sysMenuService.getUserModuleMenuList(getUserId(),s.getModuleCode());
			menuList.put(s.getModuleCode(),menu);
		}
		return R.ok().put("menuList", menuList);
	}

	/**
	 * 所有菜单列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@RequiresPermissions("sys:menu:list")
	@ResponseBody
	public R list(SysMenuEntity menu) {
		List<SysMenuEntity> menuList = sysMenuService.selectMenuList(menu, getUserId());
		return R.ok().put("list", menuList);
	}

	/**
	 * 加载角色菜单列表树
	 */
	@GetMapping("/roleMenuTreeData")
	@ResponseBody
	public List<Ztree> roleMenuTreeData(SysRoleEntity role){
		List<Ztree> ztrees = sysMenuService.roleMenuTreeData(role, getUserId());
		return ztrees;
	}

	/**
	 * 加载角色菜单列表树
	 * @author zhouzhou
	 * @date 2020-03-07 14:23
	 */
	@GetMapping("/roleModuleMenuTreeData")
	@ResponseBody
	public List<Ztree> roleModuleMenuTreeData(SysRoleEntity role){
		List<Ztree> ztrees = sysMenuService.roleModuleMenuTreeData(role, getUserId());
		return ztrees;
	}




	/**
	 * 校验菜单名称
	 */
	@RequestMapping(value = "/checkMenuNameUnique", method = RequestMethod.POST)
	@ResponseBody
	public R checkMenuNameUnique(SysMenuEntity menu){
		if(sysMenuService.checkMenuNameUnique(menu)){
			return R.ok();
		}else{
			return R.error();
		}
	}

	/**
	 * 加载所有菜单列表树
	 */
	@GetMapping("/menuTreeData")
	@ResponseBody
	public List<Ztree> menuTreeData(){
		Long userId = ShiroUtils.getUserId();
		List<Ztree> ztrees = sysMenuService.menuTreeData(userId);
		return ztrees;
	}

	/**
	 * 选择菜单树
	 */
	@GetMapping("/selectMenuTree/{menuId}")
	public String selectMenuTree(@PathVariable("menuId") Long menuId, ModelMap mmap){
		mmap.put("menu", sysMenuService.selectMenuById(menuId));
		return urlPrefix + "/tree";
	}


	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:menu:select")
	@ResponseBody
	public R select() {
		// 查询列表数据
		List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

		// 添加顶级菜单
		SysMenuEntity root = new SysMenuEntity();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		menuList.add(root);

		return R.ok().put("menuList", menuList);
	}

	/**
	 * 菜单信息
	 */
	@RequestMapping("/info/{menuId}")
	@RequiresPermissions("sys:menu:info")
	@ResponseBody
	public R info(@PathVariable("menuId") Long menuId) {
		SysMenuEntity menu = sysMenuService.getById(menuId);
		return R.ok().put("menu", menu);
	}

	/**
	 * 保存
	 */
	@BussinessLog(title = "菜单管理", businessType = BusinessType.INSERT)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@RequiresPermissions("sys:menu:add")
	@ResponseBody
	public R save(@Validated SysMenuEntity menu) {
		// 数据校验
		verifyForm(menu);

		if (!sysMenuService.checkMenuNameUnique(menu))
		{
			return R.error("新增菜单'" + menu.getName()+ "'失败，菜单名称已存在");
		}
		menu.setCreateBy(getUser().getUsername());
		menu.setCreateTime(new Date());
		if(sysMenuService.save(menu)){
			return R.ok();
		}else{
			return R.error();
		}

	}



	/**
	 * 删除
	 */
	@BussinessLog(title = "菜单管理", businessType = BusinessType.DELETE)
	@RequestMapping(value = "/del/{menuId}")
	@RequiresPermissions("sys:menu:del")
	@ResponseBody
	public R delete(@PathVariable("menuId") Long menuId) {
		List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
		if (menuList.size() > 0) {
			return R.error("请先删除子菜单或按钮");
		}
		sysMenuService.delete(menuId);
		return R.ok();
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenuEntity menu) {
		if (StringUtils.isBlank(menu.getName())) {
			throw new RxcException("菜单名称不能为空");
		}

		if (menu.getParentId() == null) {
			throw new RxcException("上级菜单不能为空");
		}

		// 菜单
		if (menu.getType() == Constant.MenuType.MENU.getValue()) {
			if (StringUtils.isBlank(menu.getUrl())) {
				throw new RxcException("菜单URL不能为空");
			}
		}

		// 上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if (menu.getParentId() != 0) {
			SysMenuEntity parentMenu = sysMenuService.getById(menu.getParentId());
			parentType = parentMenu.getType();
		}

		// 目录、菜单
		if (menu.getType() == Constant.MenuType.CATALOG.getValue()
				|| menu.getType() == Constant.MenuType.MENU.getValue()) {
			if (parentType != Constant.MenuType.CATALOG.getValue()) {
				throw new RxcException("上级菜单只能为目录类型");
			}
			return;
		}

		// 按钮
		if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
			if (parentType != Constant.MenuType.MENU.getValue()) {
				throw new RxcException("上级菜单只能为菜单类型");
			}
			return;
		}
	}
}
