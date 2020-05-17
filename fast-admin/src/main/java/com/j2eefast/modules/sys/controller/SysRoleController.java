package com.j2eefast.modules.sys.controller;

import java.util.List;
import java.util.Map;
import com.j2eefast.common.core.base.entity.Ztree;
import com.j2eefast.common.core.utils.*;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.framework.annotation.RepeatSubmit;
import com.j2eefast.framework.sys.entity.SysModuleEntity;
import com.j2eefast.framework.sys.service.*;
import com.j2eefast.framework.utils.Constant;
import com.j2eefast.framework.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.framework.sys.entity.SysRoleEntity;

/**
 * 角色管理控制器
 * @author zhouzhou
 * @date 2020-03-07 13:40
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {
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
		List<String> lisModule = sysRoleModuleService.findRoleModuleList(roleId);
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
		List<String> lisModule = sysRoleModuleService.findRoleModuleList(roleId);
		mmap.put("lisModule", lisModule);
		return urlPrefix + "/authorization";
	}

	/**
	 * 角色授权用户
	 */
	@GetMapping("/authUser/{roleId}")
	public String authorUser(@PathVariable("roleId") Long roleId, ModelMap mmap){
		mmap.put("roleId", roleId);
		List<Ztree> ztrees = sysCompService.findCompTree(UserUtils.getUserInfo().getCompId(),UserUtils.getUserId());
		mmap.put("comps",  ztrees);
		return urlPrefix + "/authUser";
	}

	/**
	 * 选择用户
	 */
	@GetMapping("/authUser/selectUser/{roleId}")
	public String selectUser(@PathVariable("roleId") Long roleId, ModelMap mmap){
		mmap.put("roleId",roleId);
		List<Ztree> ztrees = sysCompService.findCompTree(UserUtils.getUserInfo().getCompId(),UserUtils.getUserId());
		mmap.put("comps",  ztrees);
		return urlPrefix + "/selectUser";
	}

	/**
	 * 授权用户列表
	 */
	@RequestMapping("/authUser/list")
	@RequiresPermissions("sys:role:authUserList")
	@ResponseBody
	public ResponseData authUserList(@RequestParam Map<String, Object> params) {
		PageUtil page = sysUserService.findUserByRolePage(params);
		return success(page);
	}



	/**
	 * 选择用户列表
	 */
	@RequestMapping("/authUser/unallocatedList")
	@RequiresPermissions("sys:role:authUserList")
	@ResponseBody
	public ResponseData unallocatedList(@RequestParam Map<String, Object> params) {
		PageUtil page = sysUserService.findUnallocatedList(params);
		return success(page);
	}

	/**
	 * 取消授权
	 */
	@RepeatSubmit
	@BussinessLog(title = "角色管理", businessType = BusinessType.GRANT)
	@PostMapping("/authUser/cancel")
	@RequiresRoles(Constant.SU_ADMIN)
	@ResponseBody
	public ResponseData cancelAuthUser(Long roleId,Long[] userIds){
		return sysUserRoleService.deleteByUserIdToRoleIdsBatch(roleId,userIds) ? success() : success();
	}


	/**
	 * 批量选择用户授权
	 */
	@RepeatSubmit
	@RequiresPermissions("sys:role:authUserList")
	@BussinessLog(title = "角色管理", businessType = BusinessType.GRANT)
	@PostMapping("/authUser/insertAuthUsers")
	@ResponseBody
	public ResponseData selectAuthUserAll(Long roleId, Long[] userIds){
		return  sysUserRoleService.addAuthUsers(roleId, userIds)?success() : success();
	}

	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:role:list")
	@ResponseBody
	public ResponseData list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysRoleService.findPage(params);
		return success(page);
	}

	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:role:select")
	@ResponseBody
	public ResponseData select() {
		List<SysRoleEntity> list = sysRoleService.list();
		return success().put("list", list);
	}

	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	@ResponseBody
	public ResponseData info(@PathVariable("roleId") Long roleId) {
		SysRoleEntity role = sysRoleService.getById(roleId);

		// 查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.findMenuIdList(roleId);
		//role.setMenuIdList(menuIdList);

		// 查询角色对应的部门
		List<Long> deptIdList = sysRoleDeptService.findDeptIdList(new Long[] { roleId });
		//role.setDeptIdList(deptIdList);

		return  success().put("role", role);
	}



	/**
	 * 保存角色
	 */
	@RepeatSubmit
	@BussinessLog(title = "角色管理", businessType = BusinessType.INSERT)
	@RequestMapping(value ="/add" , method = RequestMethod.POST)
	@RequiresPermissions("sys:role:add")
	@RequiresRoles(Constant.SU_ADMIN)
	@ResponseBody
	public ResponseData add(@Validated SysRoleEntity role) {
		ValidatorUtil.validateEntity(role);
		return sysRoleService.add(role)?success():error("新增失败!");
	}

	/**
	 * 修改角色
	 */
	@RepeatSubmit
	@BussinessLog(title = "角色管理", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@RequiresPermissions("sys:role:edit")
	@RequiresRoles(Constant.SU_ADMIN)
	@ResponseBody
	public ResponseData update(@Validated SysRoleEntity role) {
		ValidatorUtil.validateEntity(role);
		return sysRoleService.update(role)?success():error("修改失败!");
	}

	/**
	 * 修改角色
	 */
	@RepeatSubmit
	@BussinessLog(title = "角色授权", businessType = BusinessType.GRANT)
	@RequestMapping(value = "/authorization", method = RequestMethod.POST)
	@RequiresPermissions("sys:role:authorization")
	@RequiresRoles(Constant.SU_ADMIN)
	@ResponseBody
	public ResponseData authorization(@Validated SysRoleEntity role) {
		ValidatorUtil.validateEntity(role);
		return sysRoleService.update(role)?success():error("修改失败!");
	}


	/**
	 * 删除角色
	 */
	@BussinessLog(title = "角色管理", businessType = BusinessType.DELETE)
	@RequestMapping( value = "/del", method = RequestMethod.POST)
	@RequiresPermissions("sys:role:del")
	@RequiresRoles(Constant.SU_ADMIN)
	@ResponseBody
	public ResponseData delete(Long[] ids) {
		return sysRoleService.deleteBatchByIds(ids)?success(): error("删除失败!");
	}


	/**
	 * 校验角色名称
	 */
	@RequestMapping(value = "/checkRoleNameUnique", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData checkRoleNameUnique(SysRoleEntity role){
		if(sysRoleService.checkRoleNameUnique(role)){
			return success();
		}
		return error("已经存在!");
	}

	/**
	 * 校验角色权限
	 */
	@RequestMapping(value = "/checkRoleKeyUnique", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData checkRoleKeyUnique(SysRoleEntity role){
		if(sysRoleService.checkRoleKeyUnique(role)){
			return success();
		}
		return error("已经存在!");
	}

	/**
	 * 角色状态修改
	 */
	@BussinessLog(title = "角色管理", businessType = BusinessType.UPDATE)
	@RepeatSubmit
	@RequiresPermissions("sys:role:edit")
	@PostMapping("/changeStatus")
	@RequiresRoles(Constant.SU_ADMIN)
	@ResponseBody
	public ResponseData changeStatus(SysRoleEntity role){
		return sysRoleService.changeStatus(role) ? success() : error("角色状态修改失败!");
	}

}
