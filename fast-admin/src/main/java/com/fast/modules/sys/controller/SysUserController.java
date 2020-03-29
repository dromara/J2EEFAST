package com.fast.modules.sys.controller;

import java.util.*;
import com.fast.common.core.base.entity.Ztree;
import com.fast.common.core.utils.*;
import com.fast.common.core.business.annotaion.BussinessLog;
import com.fast.common.core.enums.BusinessType;
import com.fast.framework.annotation.RepeatSubmit;
import com.fast.framework.log.entity.SysLoginInfoEntity;
import com.fast.framework.log.service.SysLoginInfoSerice;
import com.fast.framework.sys.entity.*;
import com.fast.framework.sys.service.*;
import com.fast.framework.utils.Global;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.fast.common.core.utils.ValidatorUtil;
import com.fast.framework.utils.AbstractController;
import com.fast.framework.utils.Constant;
import com.fast.framework.utils.ShiroUtils;
import cn.hutool.json.JSONUtil;

/**
 *  系统用户控制器
 * @author zhouzhou
 * @date 2020-03-07 13:28
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {

	private String urlPrefix = "modules/sys/user";

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysUserDeptService sysUserDeptService;
	@Autowired
	private SysLoginInfoSerice  sysLoginInfoService;
	@Autowired
	private SysCompService sysCompService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private RedisUtil RedisUtil;

	
	@RequiresPermissions("sys:user:view")
	@GetMapping()
	public String user(ModelMap mmap){
		List<Ztree> ztrees = sysCompService.selectCompTree(getUser().getCompId(),getUserId());
		mmap.put("comps",  ztrees);
		return urlPrefix + "/user";
	}
	
	/**
	 * 用户列表
	 * @author zhouzhou
	 * @date 2020-03-07 13:31
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	@ResponseBody
	public R list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysUserService.queryPage(params);
		return R.ok().put("page", page);
	}

	/**
	 * 新增用户
	 * @author zhouzhou
	 * @date 2020-03-07 13:33
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap){
		mmap.put("roles", sysRoleService.list());
		return urlPrefix + "/add";
	}


	/**
	 * 修改用户
	 */
	@GetMapping("/edit/{userId}")
	public String edit(@PathVariable("userId") Long userId,ModelMap mmap){
		
		SysUserEntity user = sysUserService.selectByUserId(userId);
		// 获取用户所属的角色列表resetPwd
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);

		List<SysUserDeptEntity> deptIdList = sysUserDeptService.queryListByUserId(userId);

		List<SysRoleEntity> list = sysRoleService.list();
		for(SysRoleEntity L:list){
			for(Long k:roleIdList){
				if(L.getRoleId().equals(k)){
					L.setFlag(true);
				}
			}
		}
		mmap.put("roles", list);
		mmap.put("user", user);
		mmap.put("deptList",deptIdList);
		return urlPrefix + "/edit";
	}

	/**
	 * 授权角色
	 */
	@GetMapping("/authRole/{userId}")
	public String authRole(@PathVariable("userId") Long userId,ModelMap mmap){
		SysUserEntity user = sysUserService.selectByUserId(userId);
		// 获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		mmap.put("user", user);
		mmap.put("roleIdList", JSONUtil.parseArray(roleIdList).toString());
		return urlPrefix + "/authRole";
	}



	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	@ResponseBody
	public R info() {
		return R.ok().put("user", getUser());
	}
	
	@RequestMapping("/info/login/msg/{username}")
	@ResponseBody
	public R msg(@PathVariable("username") String username) {
		String src = RedisUtil.get("sys:session:"  + username);
		if(!ToolUtil.isEmpty(src) && "00000".equals(src)) {
			//查询数据库
			SysLoginInfoEntity infoEntity =  sysLoginInfoService.queryLoginByName(username);
			RedisUtil.delete("sys:session:"  + username);
			return R.ok(ToolUtil.message("sys.login.ex.info",infoEntity.getLoginLocation(),infoEntity.getIpaddr()));
		}else if(!ToolUtil.isEmpty(src) && "00001".equals(src)) { //自动断开
			RedisUtil.delete("sys:session:"  + username);
			return R.error("00001",ToolUtil.message("sys.login.out.info"));
		}else if(!ToolUtil.isEmpty(src) && "00002".equals(src)) { //被踢下线
			RedisUtil.delete("sys:session:"  + username);
			return R.error("00002",ToolUtil.message("sys.login.userout"));
		}
		else {
			return R.error();
		}
	}

	/**
	 * 修改登录用户密码
	 */
	@BussinessLog(title = "用户管理", businessType = BusinessType.UPDATE)
	@PostMapping("/updatePass")
	@RepeatSubmit
	@ResponseBody
	public R password(String oldPassword, String newPassword) {
		AssertUtil.isBlank(newPassword, ToolUtil.message("sys.user.newpassw.tips"));

		// 原密码
		oldPassword = ShiroUtils.sha256(oldPassword, getUser().getSalt());

		if(!oldPassword.equals(getUser().getPassword())){
			return R.error(ToolUtil.message("sys.user.oldPasswordError"));
		}

		String salt = ShiroUtils.randomSalt();

		String pwdSecurityLevel = CheckPassWord.getPwdSecurityLevel(newPassword).getValue();
		// 新密码
		newPassword = ShiroUtils.sha256(newPassword, salt);

		// 更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), newPassword,salt,pwdSecurityLevel);

		if (!flag) {
			return R.error(ToolUtil.message("sys.user.oldPasswordError"));
		}

		SysUserEntity user =  getUser();
		user.setPwdSecurityLevel(pwdSecurityLevel);
		user.setPassword(newPassword);
		user.setSalt(salt);
		ShiroUtils.setSysUser(user);
		return R.ok();
	}

	@RequiresPermissions("sys:user:resetPwd")
	@GetMapping("/resetPwd/{userId}")
	public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap){
		mmap.put("user", sysUserService.selectByUserId(userId));
		return urlPrefix + "/resetPwd";
	}

	@RequiresPermissions("sys:user:resetPwd")
	@BussinessLog(title = "重置密码", businessType = BusinessType.UPDATE)
	@RepeatSubmit
	@PostMapping("/resetPwd")
	@ResponseBody
	public R resetPwdSave(SysUserEntity user){
		
		if(!ShiroUtils.isPermissions(Constant.SU_ADMIN)){
			return R.error(ToolUtil.message("sys.msg.permissions"));
		}
		//
		if(user.getPassword().equals(Global.getDbKey("sys.user.initPassword"))){
			user.setPwdSecurityLevel("0");
		}else{
			user.setPwdSecurityLevel(CheckPassWord.getPwdSecurityLevel(user.getPassword()).getValue());
		}

		String salt = ShiroUtils.randomSalt();

		// 新密码
		String newPassword = ShiroUtils.sha256(user.getPassword(), salt);

		boolean flag = sysUserService.updatePassword(user.getUserId(), newPassword,salt,user.getPwdSecurityLevel());
		if (!flag) {
			return R.error(ToolUtil.message("sys.user.oldPasswordError"));
		}
		if (ShiroUtils.getUserId().equals(user.getUserId()))
		{
			SysUserEntity user0 =  getUser();
			user0.setPwdSecurityLevel(user.getPwdSecurityLevel());
			user0.setPassword(newPassword);
			user0.setSalt(salt);
			ShiroUtils.setSysUser(user0);
		}
		return R.ok();
	}





	/**
	 * 校验用户名
	 */
	@RequestMapping(value = "/checkUserNameUnique", method = RequestMethod.POST)
	@ResponseBody
	public R checkLoginNameUnique(String username)
	{
		if(sysUserService.checkUserNameUnique(username)){
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 校验手机号码
	 */
	@RequestMapping(value = "/checkMobileUnique", method = RequestMethod.POST)
	@ResponseBody
	public R checkMobileUnique(SysUserEntity user){
		if(sysUserService.checkMobileUnique(user)){
			return R.ok();
		}
		return R.error();
	}


	/**
	 * 保存用户
	 */
	@BussinessLog(title = "用户管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@RequiresPermissions("sys:user:add")
	@ResponseBody
	public R add(@Validated SysUserEntity user) {
		if (StringUtils.isBlank(user.getPassword())) {
			return R.error("密码不能为空");
		}
		ValidatorUtil.validateEntity(user);
		user.setCreateBy(getLoginName());
		user.setCreateTime(new Date());
		sysUserService.insert(user);
		return R.ok();
	}



	
	/**
	 * 更新用户
	 */
	@BussinessLog(title = "用户管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@RequiresPermissions("sys:user:edit")
	@ResponseBody
	public R updata(@Validated SysUserEntity user) {

		ValidatorUtil.validateEntity(user);
		user.setUpdateBy(getLoginName());
		return sysUserService.updateUser(user) > 0 ? R.ok() : R.error();
	}


	/**
	 * 用户状态修改
	 */
	@BussinessLog(title = "用户管理", businessType = BusinessType.UPDATE)
	@RepeatSubmit
	@RequiresPermissions("sys:user:edit")
	@PostMapping("/changeStatus")
	@ResponseBody
	public R changeStatus(SysUserEntity user)
	{
		if(!ShiroUtils.isPermissions(Constant.SU_ADMIN)){
			return R.error(ToolUtil.message("sys.msg.permissions"));
		}
		user.setCreateBy(getLoginName());
		return sysUserService.changeStatus(user) > 0 ? R.ok() : R.error();
	}

	/**
	 * 角色列表
	 */
	@RequestMapping("/authRole/list")
	@RequiresPermissions("sys:user:authRoleList")
	@ResponseBody
	public R authRoleList(@RequestParam Map<String, Object> params) {
		PageUtil page = sysRoleService.queryPage(params);
		return R.ok().put("page", page);
	}


	/**
	 * 用户授权角色
	 */
	@RepeatSubmit
	@RequiresPermissions("sys:user:authRoleList")
	@BussinessLog(title = "用户管理", businessType = BusinessType.GRANT)
	@PostMapping("/authRole/insertAuthRoles")
	@ResponseBody
	public R selectAuthUserAll(Long userId, Long[] roleIds)
	{
		return  sysUserRoleService.insertUserAuths(userId, roleIds)?R.ok() : R.error();
	}

	/**
	 * 删除用户
	 */
	@BussinessLog(title = "角色管理", businessType = BusinessType.DELETE)
	@RequestMapping( value = "/del", method = RequestMethod.POST)
	@RequiresPermissions("sys:user:del")
	@ResponseBody
	public R delete(Long[] ids) {
		if(getUserId().equals(Constant.SUPER_ADMIN) ||
				ShiroUtils.isPermissions(Constant.SU_ADMIN)){
			if (ArrayUtils.contains(ids, getUserId())) {
				return R.error("当前用户不能删除");
			}

			// 删除 用户与角色 关联表
			sysUserRoleService.deleteByUserIdBatch(ids);
			// 删除 用户与地区 关联表
			sysUserDeptService.deleteByUserIdBatch(ids);
			//删除 用户
			sysUserService.removeByIds(Arrays.asList(ids));

			return R.ok();
		}else{
			return R.error(ToolUtil.message("sys.msg.permissions"));
		}
	}
}
