package com.fast.modules.sys.controller;

import java.util.List;
import java.util.Map;
import com.fast.common.core.base.entity.Ztree;
import com.fast.common.core.utils.ToolUtil;
import com.fast.common.core.business.annotaion.BussinessLog;
import com.fast.common.core.enums.BusinessType;
import com.fast.framework.annotation.RepeatSubmit;
import com.fast.framework.sys.entity.SysModuleEntity;
import com.fast.framework.sys.service.*;
import com.fast.framework.utils.Constant;
import com.fast.framework.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.R;
import com.fast.framework.utils.AbstractController;
import com.fast.common.core.utils.ValidatorUtil;
import com.fast.framework.sys.entity.SysRoleEntity;

/**
 * 角色管理控制器
 * @author zhouzhou
 * @date 2020-03-07 13:40
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	private String urlPrefix = "modules/sys/role";

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysModuleService sysModuleService;
	@Autowired
	private SysRoleModuleService sysRoleModuleService;
	@Autowired
	private SysCompService sysCompService;
	@Autowired
	private SysUserService sysUserService;
	@RequiresPermissions("sys:role:view")
	
	
	@GetMapping()
	public String role(){
		return urlPrefix + "/role";
	}

	/**
	 * 新增角色
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap){
		List<SysModuleEntity>  modules = sysModuleService.list();
		mmap.put("modules", modules);
		return urlPrefix + "/add";
	}

	/**
	 * 修改角色
	 */
	@GetMapping("/edit/{roleId}")
	public String edit(@PathVariable("roleId") Long roleId, ModelMap mmap){
		mmap.put("role", sysRoleService.getById(roleId));
		List<SysModuleEntity>  modules = sysModuleService.list();
		mmap.put("modules", modules);
		List<String> lisModule = sysRoleModuleService.selectRoleModuleList(roleId);
		mmap.put("lisModule", lisModule);
		return urlPrefix + "/edit";
	}

	/**
	 * 角色授权
	 */
	@GetMapping("/authorization/{roleId}")
	public String authorization(@PathVariable("roleId") Long roleId, ModelMap mmap){
		mmap.put("role", sysRoleService.getById(roleId));
		List<SysModuleEntity>  modules = sysModuleService.list();
		mmap.put("modules", modules);
		List<String> lisModule = sysRoleModuleService.selectRoleModuleList(roleId);
		mmap.put("lisModule", lisModule);
		return urlPrefix + "/authorization";
	}

	/**
	 * 角色授权用户
	 */
	@GetMapping("/authUser/{roleId}")
	public String authorUser(@PathVariable("roleId") Long roleId, ModelMap mmap){
		mmap.put("roleId", roleId);
		List<Ztree> ztrees = sysCompService.selectCompTree(getUser().getCompId(),getUserId());
		mmap.put("comps",  ztrees);
		return urlPrefix + "/authUser";
	}

	/**
	 * 选择用户
	 */
	@GetMapping("/authUser/selectUser/{roleId}")
	public String selectUser(@PathVariable("roleId") Long roleId, ModelMap mmap){
		mmap.put("roleId",roleId);
		List<Ztree> ztrees = sysCompService.selectCompTree(getUser().getCompId(),getUserId());
		mmap.put("comps",  ztrees);
		return urlPrefix + "/selectUser";
	}

	/**
	 * 授权用户列表
	 */
	@RequestMapping("/authUser/list")
	@RequiresPermissions("sys:role:authUserList")
	@ResponseBody
	public R authUserList(@RequestParam Map<String, Object> params) {
		PageUtil page = sysUserService.queryRoleToUserPage(params);
		return R.ok().put("page", page);
	}



	/**
	 * 选择用户列表
	 */
	@RequestMapping("/authUser/unallocatedList")
	@RequiresPermissions("sys:role:authUserList")
	@ResponseBody
	public R unallocatedList(@RequestParam Map<String, Object> params) {
		PageUtil page = sysUserService.queryUnallocatedPage(params);
		return R.ok().put("page", page);
	}

	/**
	 * 取消授权
	 */
	@RepeatSubmit
	@BussinessLog(title = "角色管理", businessType = BusinessType.GRANT)
	@PostMapping("/authUser/cancel")
	@ResponseBody
	public R cancelAuthUser(Long roleId,Long[] userIds){
		if(getUserId().equals(Constant.SUPER_ADMIN) ||
				ShiroUtils.isPermissions(Constant.SU_ADMIN)){
			return sysUserRoleService.deleteByUserIdToRoleIdsBatch(roleId,userIds) > 0? R.ok() : R.error();
		}else{
			return R.error(ToolUtil.message("sys.msg.permissions"));
		}
	}


	/**
	 * 批量选择用户授权
	 */
	@RepeatSubmit
	@RequiresPermissions("sys:role:authUserList")
	@BussinessLog(title = "角色管理", businessType = BusinessType.GRANT)
	@PostMapping("/authUser/insertAuthUsers")
	@ResponseBody
	public R selectAuthUserAll(Long roleId, Long[] userIds){
		return  sysUserRoleService.insertAuthUsers(roleId, userIds)?R.ok() : R.error();
	}

	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:role:list")
	@ResponseBody
	public R list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysRoleService.queryPage(params);
		return R.ok().put("page", page);
	}

	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:role:select")
	@ResponseBody
	public R select() {
		List<SysRoleEntity> list = sysRoleService.list();
		return R.ok().put("list", list);
	}

	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	@ResponseBody
	public R info(@PathVariable("roleId") Long roleId) {
		SysRoleEntity role = sysRoleService.getById(roleId);

		// 查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);

		// 查询角色对应的部门
		List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(new Long[] { roleId });
		role.setDeptIdList(deptIdList);

		return R.ok().put("role", role);
	}



	/**
	 * 保存角色
	 */
	@RepeatSubmit
	@BussinessLog(title = "角色管理", businessType = BusinessType.INSERT)
	@RequestMapping(value ="/add" , method = RequestMethod.POST)
	@RequiresPermissions("sys:role:add")
	@ResponseBody
	public R add(@Validated SysRoleEntity role) {
		ValidatorUtil.validateEntity(role);
		if(getUserId().equals(Constant.SUPER_ADMIN) ||
				ShiroUtils.isPermissions(Constant.SU_ADMIN)){
			role.setCreateBy(getLoginName());
			sysRoleService.save(role);
			return R.ok();
		}else{
			return R.error(ToolUtil.message("sys.msg.permissions"));
		}
	}

	/**
	 * 修改角色
	 */
	@RepeatSubmit
	@BussinessLog(title = "角色管理", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@RequiresPermissions("sys:role:edit")
	@ResponseBody
	public R update(@Validated SysRoleEntity role) {
		ValidatorUtil.validateEntity(role);

		if(getUserId().equals(Constant.SUPER_ADMIN) ||
				ShiroUtils.isPermissions(Constant.SU_ADMIN)){
			role.setUpdateBy(getUser().getUsername());
			sysRoleService.update(role);
			return R.ok();
		}else{
			return R.error(ToolUtil.message("sys.msg.permissions"));
		}
	}

	/**
	 * 修改角色
	 */
	@RepeatSubmit
	@BussinessLog(title = "角色授权", businessType = BusinessType.GRANT)
	@RequestMapping(value = "/authorization", method = RequestMethod.POST)
	@RequiresPermissions("sys:role:authorization")
	@ResponseBody
	public R authorization(@Validated SysRoleEntity role) {
		ValidatorUtil.validateEntity(role);
		if(getUserId().equals(Constant.SUPER_ADMIN) ||
				ShiroUtils.isPermissions(Constant.SU_ADMIN)){
			role.setUpdateBy(getUser().getUsername());
			sysRoleService.update(role);
			return R.ok();
		}else{
			return R.error(ToolUtil.message("sys.msg.permissions"));
		}
	}


	/**
	 * 删除角色
	 */
	@BussinessLog(title = "角色管理", businessType = BusinessType.DELETE)
	@RequestMapping( value = "/del", method = RequestMethod.POST)
	@RequiresPermissions("sys:role:del")
	@ResponseBody
	public R delete(Long[] ids) {
		if(getUserId().equals(Constant.SUPER_ADMIN) ||
				ShiroUtils.isPermissions(Constant.SU_ADMIN)){
			sysRoleService.deleteBatch(ids);
			return R.ok();
		}else{
			return R.error(ToolUtil.message("sys.msg.permissions"));
		}
	}


	/**
	 * 校验角色名称
	 */
	@RequestMapping(value = "/checkRoleNameUnique", method = RequestMethod.POST)
	@ResponseBody
	public R checkRoleNameUnique(SysRoleEntity role){
		if(sysRoleService.checkRoleNameUnique(role)){
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 校验角色权限
	 */
	@RequestMapping(value = "/checkRoleKeyUnique", method = RequestMethod.POST)
	@ResponseBody
	public R checkRoleKeyUnique(SysRoleEntity role){
		if(sysRoleService.checkRoleKeyUnique(role)){
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 角色状态修改
	 */
	@BussinessLog(title = "角色管理", businessType = BusinessType.UPDATE)
	@RepeatSubmit
	@RequiresPermissions("sys:role:edit")
	@PostMapping("/changeStatus")
	@ResponseBody
	public R changeStatus(SysRoleEntity role){
		if(!ShiroUtils.isPermissions(Constant.SU_ADMIN)){
			return R.error(ToolUtil.message("sys.msg.permissions"));
		}
		role.setCreateBy(getLoginName());
		return sysRoleService.changeStatus(role) > 0 ? R.ok() : R.error();
	}

}
