package com.fast.framework.sys.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fast.common.core.page.Query;
import com.fast.common.core.utils.*;
import com.fast.framework.sys.entity.*;
import com.fast.framework.sys.service.*;
import com.fast.framework.utils.Global;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.framework.annotation.DataFilter;
import com.fast.framework.sys.dao.SysUserDao;
import com.fast.framework.utils.Constant;

import com.fast.framework.utils.ShiroUtils;
import cn.hutool.core.util.StrUtil;

/**
 * 系统用户
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	@Autowired
	private SysRoleService sysRoleService;
	// 公司
	@Autowired
	private SysCompService sysCompService;
	@Autowired
	private SysUserPostService sysUserPostService;

	@Autowired
	private SysUserDeptService sysUserDeptService;
	
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return baseMapper.queryAllMenuId(userId);
	}

	@Override
	@DataFilter(subComp = true, tableAlias = "c")
	public PageUtil queryPage(Map<String, Object> params) {
		String username = (String) params.get("username");
		String status = (String) params.get("status");
		String mobile = (String) params.get("mobile");
		String email = (String) params.get("email");
		String compId = (String) params.get("compId");

		Page<SysUserEntity> tempPage = new Query<SysUserEntity>(params).getPage();
		Page<SysUserEntity> page = tempPage.setRecords(this.baseMapper.
				selectAll(tempPage, username,status,StrUtil.nullToDefault(mobile,""),StrUtil.nullToDefault(email,""),
						StrUtil.nullToDefault(compId,""),StrUtil.nullToDefault((String)params.get(Constant.SQL_FILTER),"")));
		for (SysUserEntity sysUserEntity : page.getRecords()) {
			String roleName = "";
			List<Object> listUserRole = sysUserRoleService.listObjs(new QueryWrapper<SysUserRoleEntity>().eq("user_id", sysUserEntity.getUserId()).select("role_id"));
			for(Object obj : listUserRole) {
				SysRoleEntity sysRoleEntity = sysRoleService.getById((Long)obj);
				roleName += sysRoleEntity.getRoleName()+","; 
			}
			sysUserEntity.setRoleName(roleName);
		}
		return new PageUtil(page);
	}

	@Override
	@DataFilter(subData = true, tableAlias = "u")
	public PageUtil query2UserPage(Map<String, Object> params) {
		String username = (String) params.get("username");
		String status = (String) params.get("status");
		String mobile = (String) params.get("mobile");
		String email = (String) params.get("email");
		String postCode = (String) params.get("postCode");
		Page<SysUserEntity> tempPage = new Query<SysUserEntity>(params).getPage();
		Page<SysUserEntity> page = tempPage.setRecords(this.baseMapper.select2All(tempPage,StrUtil.nullToDefault(username,""),
				StrUtil.nullToDefault(mobile,""),StrUtil.nullToDefault(email,""),StrUtil.nullToDefault(status,""),
				StrUtil.nullToDefault(postCode,""),
				StrUtil.nullToDefault((String)params.get(Constant.SQL_FILTER),"")));
		return new PageUtil(page);
	}


	@Override
	@DataFilter(subComp = true, tableAlias = "c")
	public PageUtil queryRoleToUserPage(Map<String, Object> params) {
		String roleId = (String) params.get("roleId");
		String username = (String) params.get("username");
		String status = (String) params.get("status");
		String mobile = (String) params.get("mobile");
		String email = (String) params.get("email");
		String compId = (String) params.get("compId");
		Page<SysUserEntity> tempPage = new Query<SysUserEntity>(params).getPage();
		Page<SysUserEntity> page = tempPage.setRecords(this.baseMapper.selectToRoleToUser(tempPage, roleId,StrUtil.nullToDefault(username,""),
				StrUtil.nullToDefault(status,""),StrUtil.nullToDefault(mobile,""),StrUtil.nullToDefault(email,""),
				StrUtil.nullToDefault(compId,""),
				StrUtil.nullToDefault((String)params.get(Constant.SQL_FILTER),"")));
		return new PageUtil(page);
	}

	@Override
	@DataFilter(subComp = true, tableAlias = "c")
	public PageUtil queryUnallocatedPage(Map<String, Object> params) {
		String roleId = (String) params.get("roleId");
		String username = (String) params.get("username");
		String mobile = (String) params.get("mobile");
		String email = (String) params.get("email");
		String compId = (String) params.get("compId");
		Page<SysUserEntity> tempPage = new Query<SysUserEntity>(params).getPage();
		Page<SysUserEntity> page = tempPage.setRecords(this.baseMapper.selectUnallocatedList(tempPage, roleId,StrUtil.nullToDefault(username,""),
				StrUtil.nullToDefault(mobile,""),StrUtil.nullToDefault(email,""),
				StrUtil.nullToDefault(compId,""),
				StrUtil.nullToDefault((String)params.get(Constant.SQL_FILTER),"")));
		return new PageUtil(page);
	}


	@Override
	public SysUserEntity selectByUserId(Long userId) {
		return this.baseMapper.selectByUserId(userId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insert(SysUserEntity user) {
		user.setCreateTime(new Date());

		//检查密码安全级别
		if(user.getPassword().equals(Global.getDbKey("sys.user.initPassword"))){
			user.setPwdSecurityLevel("0");
		}else{
			user.setPwdSecurityLevel(CheckPassWord.getPwdSecurityLevel(user.getPassword()).getValue());
		}
		// sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));

		this.save(user);

		// 保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());

		// 保存用户与公司地区关系
		sysUserDeptService.saveOrUpdate(user.getUserId(), user.getDeptIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysUserEntity user) {
		if (ToolUtil.isEmpty(user.getPassword())) {
			String pass = this.getById(user.getUserId()).getPassword();
			user.setPassword(pass);			
		} else {
			user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		}
		
		this.updateById(user);
		
//		sysUserRoleService.delete(new QueryWrapper<SysUserRoleEntity>().eq("user_id", user.getUserId())); //先删除，在插入
//
//		sysUserDeptService.delete(new QueryWrapper<SysUserDeptEntity>().eq("user_id", user.getUserId()));

		// 保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());

		// 保存用户与公司地区关系
		sysUserDeptService.saveOrUpdate(user.getUserId(), user.getDeptIdList());

	}

	@Override
	public int updateUser(SysUserEntity user) {
		Long userId = user.getUserId();

		//sysUserRoleService.delete(new QueryWrapper<SysUserRoleEntity>().eq("user_id", userId)); //先删除，在插入

		// 保存用户与角色关系 内部先删除 再插入
		sysUserRoleService.saveOrUpdate(userId, user.getRoleIdList());

		//sysUserDeptService.delete(new QueryWrapper<SysUserDeptEntity>().eq("user_id", userId));

		// 保存用户与公司地区关系 内部先删除 再插入
		sysUserDeptService.saveOrUpdate(userId, user.getDeptIdList());


		//岗位关联
		sysUserPostService.saveOrUpdate(userId, user.getPostCodes());

		return this.baseMapper.updateUser(user);
	}

	@Override
	public boolean updatePassword(Long userId,String newPassword,String salt, String pwdSecurityLevel) {
		return  this.baseMapper.updatePassWord(userId,newPassword,salt,pwdSecurityLevel) > 0;
	}

	@Override
	public boolean updateUserName(Long userId, String name, String phone, String email) {
		SysUserEntity userEntity = new SysUserEntity();
		userEntity.setName(name);
		userEntity.setMobile(phone);
		userEntity.setEmail(email);
		userEntity.setUserId(userId);

		return this.baseMapper.updateUser(userEntity) > 0;
	}

	@Override
	public boolean updateAvatar(Long userId, String avatar) {
		return this.baseMapper.updateAvatar(userId,avatar) > 0;
	}

	@Override
	public boolean checkUserNameUnique(String username) {
		int count = this.baseMapper.checkUserNameUnique(username);
		if(count > 0){
			return false;
		}
		return  true;
	}

	@Override
	public boolean checkMobileUnique(SysUserEntity user) {
		Long userId = ToolUtil.isEmpty(user.getUserId())?-1L:user.getUserId();
		SysUserEntity info = this.baseMapper.checkMobileUnique(user.getMobile());
		if(ToolUtil.isNotEmpty(info) && !info.getUserId().equals(userId)){ //1241000425446158338 //1241000425446158338
			return  false;
		}
		return true;
	}

	@Override
	public int changeStatus(SysUserEntity user) {
		return this.baseMapper.updateUser(user);
	}

	@Override
	public List<String> queryAllPerms(Long userId) {
		return this.baseMapper.queryAllPerms(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return this.baseMapper.queryByUserName(username);
	}

	@Override
	public SysUserEntity queryByMobile(String mobile) {
		return this.baseMapper.queryByMobile(mobile);
	}

	@Override
	public SysUserEntity queryByEmail(String email) {
		return this.baseMapper.queryByEmail(email);
	}

	@Override
	public List<SysUserEntity> queryAllClear(Long compId) {
		return baseMapper.queryAllClear(compId);
	}

	@Override
	public List<SysUserEntity> queryAllDriver(Long compId) {
		return baseMapper.queryAllDriver(compId);
	}

	@Override
	public List<SysUserEntity> queryAllMaga(Long compId) {
		return baseMapper.queryAllMaga(compId);
	}

}
