package com.j2eefast.modules.sys.controller;

import java.io.File;
import java.util.*;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import com.alibaba.excel.EasyExcel;
import com.j2eefast.common.core.base.entity.LoginUserEntity;
import com.j2eefast.common.core.utils.*;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.framework.annotation.RepeatSubmit;
import com.j2eefast.framework.log.entity.SysLoginInfoEntity;
import com.j2eefast.framework.log.service.SysLoginInfoSerice;
import com.j2eefast.framework.sys.entity.*;
import com.j2eefast.framework.sys.service.*;
import com.j2eefast.framework.utils.Global;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.j2eefast.common.core.utils.ValidatorUtil;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.framework.utils.Constant;
import com.j2eefast.framework.utils.UserUtils;
import cn.hutool.json.JSONUtil;

/**
 *  系统用户控制器
 * @author zhouzhou
 * @date 2020-03-07 13:28
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

	private String urlPrefix = "modules/sys/user";

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysPostService sysPostService;
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
		return urlPrefix + "/user";
	}
	
	/**
	 * 页面用户表格分页查询
	 * @author zhouzhou
	 * @date 2020-03-07 13:31
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	@ResponseBody
	public ResponseData list(@RequestParam Map<String, Object> params) {
		PageUtil page = sysUserService.findPage(params);
		return success(page);
	}

	/**
	 * 新增用户
	 * @author zhouzhou
	 * @date 2020-03-07 13:33
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap){
		mmap.put("roles", sysRoleService.getRolesAll());
		mmap.put("posts",sysPostService.getPostAll());
		return urlPrefix + "/add";
	}

	@BussinessLog(title = "用户管理", businessType = BusinessType.EXPORT)
	@RequiresPermissions("sys:user:export")
	@PostMapping("/export")
	@ResponseBody
	public ResponseData export(@RequestParam Map<String, Object> params) throws Exception {
		String fileName =  "测试";
		fileName = ToolUtil.encodingExcelFilename(fileName);
		String folder = Global.getConifgFile() + File.separator + "pio" + File.separator;
		FileUtil.touch(folder + fileName);
		List<SysUserEntity> listData = sysUserService.findList(params);
		EasyExcel.write(folder + fileName, SysUserEntity.class).sheet("模板").doWrite(listData);
		return success(fileName);
	}




	/**
	 * 修改用户
	 */
	@GetMapping("/edit/{userId}")
	public String edit(@PathVariable("userId") Long userId,ModelMap mmap){
		
		SysUserEntity user = sysUserService.findUserByUserId(userId);
		mmap.put("roles", sysRoleService.getRolesAll());
		mmap.put("posts",sysPostService.getPostAll());
		mmap.put("selectRoles", sysRoleService.getRolesByUserIdToStr(userId));
		mmap.put("selectPosts",sysPostService.getPostByUserIdToStr(userId));
		mmap.put("user", user);
		return urlPrefix + "/edit";
	}

	/**
	 * 授权角色
	 */
	@GetMapping("/authRole/{userId}")
	public String authRole(@PathVariable("userId") Long userId,ModelMap mmap){
		SysUserEntity user = sysUserService.findUserByUserId(userId);
		// 获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.findRoleIdList(userId);
		mmap.put("user", user);
		mmap.put("roleIdList", JSONUtil.parseArray(roleIdList).toString());
		return urlPrefix + "/authRole";
	}



	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	@ResponseBody
	public ResponseData info() {
		LoginUserEntity loginUser = UserUtils.getUserInfo();
		return success().put("user", loginUser);
	}
	
	@RequestMapping("/info/login/msg/{username}")
	@ResponseBody
	public ResponseData msg(@PathVariable("username") String username) {
		String src = RedisUtil.get("sys:session:"  + username);
		if(!ToolUtil.isEmpty(src) && "00000".equals(src)) {
			//查询数据库
			SysLoginInfoEntity infoEntity =  sysLoginInfoService.queryLoginByName(username);
			RedisUtil.delete("sys:session:"  + username);
			return success(ToolUtil.message("sys.login.ex.info",infoEntity.getLoginLocation(),infoEntity.getIpaddr()));
		}else if(!ToolUtil.isEmpty(src) && "00001".equals(src)) { //自动断开
			RedisUtil.delete("sys:session:"  + username);
			return error("00001",ToolUtil.message("sys.login.out.info"));
		}else if(!ToolUtil.isEmpty(src) && "00002".equals(src)) { //被踢下线
			RedisUtil.delete("sys:session:"  + username);
			return error("00002",ToolUtil.message("sys.login.userout"));
		}
		else {
			return success();
		}
	}

	/**
	 * 修改登录用户密码
	 */
	@BussinessLog(title = "用户管理", businessType = BusinessType.UPDATE)
	@PostMapping("/updatePass")
	@RepeatSubmit
	@ResponseBody
	public ResponseData password(String oldPassword, String newPassword) {
		AssertUtil.isBlank(newPassword, ToolUtil.message("sys.user.newpassw.tips"));
		LoginUserEntity loginUser = UserUtils.getUserInfo();
		// 原密码
		oldPassword = UserUtils.sha256(oldPassword, loginUser.getSalt());

		if(!oldPassword.equals(loginUser.getPassword())){
			return error(ToolUtil.message("sys.user.oldPasswordError"));
		}

		String salt = UserUtils.randomSalt();

		String pwdSecurityLevel = CheckPassWord.getPwdSecurityLevel(newPassword).getValue();
		// 新密码
		newPassword = UserUtils.sha256(newPassword, salt);

		// 更新密码
		boolean flag = sysUserService.updatePassWord(loginUser.getId(), newPassword,salt,pwdSecurityLevel);

		if (!flag) {
			return error(ToolUtil.message("sys.user.oldPasswordError"));
		}

		loginUser.setPwdSecurityLevel(pwdSecurityLevel);
		loginUser.setPassword(newPassword);
		loginUser.setSalt(salt);
		UserUtils.reloadUser(loginUser);
		return success();
	}

	@RequiresPermissions("sys:user:resetPwd")
	@GetMapping("/resetPwd/{id}")
	public String resetPwd(@PathVariable("id") Long userId, ModelMap mmap){
		mmap.put("user", sysUserService.getById(userId));
		return urlPrefix + "/resetPwd";
	}

	@RequiresRoles(Constant.SU_ADMIN)
	@RequiresPermissions("sys:user:resetPwd")
	@BussinessLog(title = "重置密码", businessType = BusinessType.UPDATE)
	@RepeatSubmit
	@PostMapping("/resetPwd")
	@ResponseBody
	public ResponseData resetPwdSave(SysUserEntity user){

		LoginUserEntity loginUser = UserUtils.getUserInfo();
		if(user.getPassword().equals(Global.getDbKey("sys.user.initPassword"))){
			user.setPwdSecurityLevel("0");
		}else{
			user.setPwdSecurityLevel(CheckPassWord.getPwdSecurityLevel(user.getPassword()).getValue());
		}

		String salt = UserUtils.randomSalt();

		// 新密码
		String newPassword = UserUtils.sha256(user.getPassword(), salt);

		boolean flag = sysUserService.updatePassWord(user.getId(), newPassword,salt,user.getPwdSecurityLevel());
		if (!flag) {
			return error(ToolUtil.message("sys.user.oldPasswordError"));
		}
		if (loginUser.getId().equals(user.getId())){
			loginUser.setPwdSecurityLevel(user.getPwdSecurityLevel());
			loginUser.setPassword(newPassword);
			loginUser.setSalt(salt);
			UserUtils.reloadUser(loginUser);
		}
		return success();
	}





	/**
	 * 校验用户名
	 */
	@RequestMapping(value = "/checkUserNameUnique", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData checkLoginNameUnique(String username) {
		if(sysUserService.checkUserNameUnique(username)){
			return success();
		}
		return error("已经存在!");
	}

	/**
	 * 校验手机号码
	 */
	@RequestMapping(value = "/checkMobileUnique", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData checkMobileUnique(SysUserEntity user){
		if(sysUserService.checkMobileUnique(user)){
			return success();
		}
		return error("已经存在!");
	}


	/**
	 * 保存用户
	 */
	@BussinessLog(title = "用户管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@RequiresPermissions("sys:user:add")
	@ResponseBody
	public ResponseData add(@Validated SysUserEntity user) {
		if (StringUtils.isBlank(user.getPassword())) {
			return error("密码不能为空");
		}
		ValidatorUtil.validateEntity(user);
		return sysUserService.add(user)?success(): error("新增失败!");
	}

	/**
	 * 更新用户
	 */
	@BussinessLog(title = "用户管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@RequiresPermissions("sys:user:edit")
	@ResponseBody
	public ResponseData edit(@Validated SysUserEntity user) {
		ValidatorUtil.validateEntity(user);
		return sysUserService.update(user) ? success() : error("修改失败!");
	}


	/**
	 * 用户状态修改
	 */
	@BussinessLog(title = "用户管理", businessType = BusinessType.UPDATE)
	@RequiresRoles(Constant.SU_ADMIN)
	@RepeatSubmit
	@RequiresPermissions("sys:user:edit")
	@PostMapping("/changeStatus")
	@ResponseBody
	public ResponseData changeStatus(SysUserEntity user)
	{
		return sysUserService.changeStatus(user) ? success() : error("修改失败!");
	}

	/**
	 * 角色列表
	 */
	@RequestMapping("/authRole/list")
	@RequiresPermissions("sys:user:authRoleList")
	@ResponseBody
	public ResponseData authRoleList(@RequestParam Map<String, Object> params) {
		PageUtil page = sysRoleService.findPage(params);
		return success(page);
	}


	/**
	 * 用户授权角色
	 */
	@RepeatSubmit
	@RequiresPermissions("sys:user:authRoleList")
	@BussinessLog(title = "用户管理", businessType = BusinessType.GRANT)
	@PostMapping("/authRole/insertAuthRoles")
	@ResponseBody
	public ResponseData selectAuthUserAll(Long userId, Long[] roleIds){
		return  sysUserRoleService.addUserAuths(userId, roleIds)?success() : error("授权失败!");
	}

	/**
	 * 删除用户
	 */
	@BussinessLog(title = "角色管理", businessType = BusinessType.DELETE)
	@RequestMapping( value = "/del", method = RequestMethod.POST)
	@RequiresPermissions("sys:user:del")
	@RequiresRoles(Constant.SU_ADMIN)
	@ResponseBody
	public ResponseData delete(Long[] ids) {
		if(ArrayUtil.contains(ids,UserUtils.getUserId())){
			return error("不能删除自己!");
		}
		return sysUserService.delUser(ids)?success(): error("删除失败!");
	}
}
