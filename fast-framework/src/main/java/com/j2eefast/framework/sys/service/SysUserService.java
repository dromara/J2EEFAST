package com.j2eefast.framework.sys.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.CheckPassWord;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.annotation.DataFilter;
import com.j2eefast.framework.sys.entity.SysUserEntity;
import com.j2eefast.framework.sys.mapper.SysUserMapper;
import com.j2eefast.framework.utils.Constant;
import com.j2eefast.framework.utils.Global;
import com.j2eefast.framework.utils.UserUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统用户
 * @author zhouzhou
 */
@Service
public class SysUserService  extends ServiceImpl<SysUserMapper,SysUserEntity> {

	@Resource
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Resource
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserDeptService sysUserDeptService;
	@Autowired
	private SysUserPostService sysUserPostService;

	/**
	 * 用户页面查询分页
	 * @param params
	 * @return
	 */
	@DataFilter(subComp = true, tableAlias = "c")
	public PageUtil findPage(Map<String, Object> params) {
		String username = (String) params.get("username");
		String status = (String) params.get("status");
		String mobile = (String) params.get("mobile");
		String email = (String) params.get("email");
		String compId = (String) params.get("compId");
		Page<SysUserEntity> page = sysUserMapper.findPage(	new Query<SysUserEntity>(params).getPage(),
															StrUtil.nullToDefault(username,""),
															StrUtil.nullToDefault(status,""),
															StrUtil.nullToDefault(mobile,""),
															StrUtil.nullToDefault(email,""),
															StrUtil.nullToDefault(compId,""),
															(String) params.get(Constant.SQL_FILTER));
		return new PageUtil(page);
	}


	/**
	 * 根居用户权限查询用户
	 * @param params
	 * @return
	 */
	@DataFilter(subComp = true, tableAlias = "c")
	public PageUtil findUserByRolePage(Map<String, Object> params) {
		String roleId = (String) params.get("roleId");
		String username = (String) params.get("username");
		String status = (String) params.get("status");
		String mobile = (String) params.get("mobile");
		String email = (String) params.get("email");
		String compId = (String) params.get("compId");
		Page<SysUserEntity> page = sysUserMapper.findUserByRolePage(new Query<SysUserEntity>(params).getPage(),
																	StrUtil.nullToDefault(roleId,""),
																	StrUtil.nullToDefault(username,""),
																	StrUtil.nullToDefault(status,""),
																	StrUtil.nullToDefault(mobile,""),
																	StrUtil.nullToDefault(email,""),
																	StrUtil.nullToDefault(compId,""),
																	(String) params.get(Constant.SQL_FILTER));
		return new PageUtil(page);
	}


	@DataFilter(subComp = true, tableAlias = "c")
	public PageUtil findUnallocatedList(Map<String, Object> params) {
		String roleId = (String) params.get("roleId");
		String username = (String) params.get("username");
		String mobile = (String) params.get("mobile");
		String email = (String) params.get("email");
		String compId = (String) params.get("compId");
		Page<SysUserEntity> page = sysUserMapper.findUnallocatedList(new Query<SysUserEntity>(params).getPage(),
																	StrUtil.nullToDefault(roleId,""),
																	StrUtil.nullToDefault(username,""),
																	StrUtil.nullToDefault(mobile,""),
																	StrUtil.nullToDefault(email,""),
																	StrUtil.nullToDefault(compId,""),
																	(String) params.get(Constant.SQL_FILTER));
		return new PageUtil(page);
	}

//	public List<Long> findAllMenuId(Long userId) {
//		return sysUserMapper.findAllMenuId(userId);
//	}

	/**
	 * 保存
	 * @param user
	 * @return
	 */
	public boolean add(SysUserEntity user) {

		//检查密码安全级别
		if(user.getPassword().equals(Global.getDbKey("sys.user.initPassword"))){
			user.setPwdSecurityLevel("0");
		}else{
			user.setPwdSecurityLevel(CheckPassWord.getPwdSecurityLevel(user.getPassword()).getValue());
		}
		// sha256加密
		String salt = UserUtils.randomSalt();
		user.setSalt(salt);
		user.setPassword(UserUtils.sha256(user.getPassword(), user.getSalt()));

		if(this.save(user)){
			// 保存用户与角色关系
			sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());

			// 保存用户与公司地区关系
			sysUserDeptService.saveOrUpdate(user.getUserId(), user.getDeptIdList());

			return true;
		}

		return false;
	}

	/**
	 * 更新
	 * @param user
	 * @return
	 */
	public boolean update(SysUserEntity user) {

		if (ToolUtil.isNotEmpty(user.getPassword())) {
			String salt = UserUtils.randomSalt();
			user.setPwdSecurityLevel(CheckPassWord.getPwdSecurityLevel(user.getPassword()).getValue());
			user.setSalt(salt);
			user.setPassword(UserUtils.sha256(user.getPassword(), user.getSalt()));
		}
		if(this.updateById(user)){
			// 保存用户与角色关系
			sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());

			// 保存用户与公司地区关系
			sysUserDeptService.saveOrUpdate(user.getUserId(), user.getDeptIdList());

			//岗位关联
			sysUserPostService.saveOrUpdate(user.getUserId(), user.getPostCodes());

			return true;
		}

		return false;
	}

	/**
	 * 修改头像
	 * @param userId
	 * @param avatar
	 * @return
	 */
	public boolean updateAvatar(Long userId, String avatar) {
		return sysUserMapper.updateAvatar(userId,avatar) > 0;
	}

	public boolean updateUserName(Long userId, String name, String phone, String email) {
		SysUserEntity userEntity = new SysUserEntity();
		userEntity.setName(name);
		userEntity.setMobile(phone);
		userEntity.setEmail(email);
		userEntity.setUserId(userId);
		return this.updateById(userEntity);
	}

	public boolean  updatePassWord(Long userId, String password, String salt, String pwdSecurityLevel){
		return sysUserMapper.updatePassWord(userId,password,salt,pwdSecurityLevel) > 0;
	}


	public boolean checkUserNameUnique(String username) {
		int count = this.count(new QueryWrapper<SysUserEntity>().eq("username",username));
		if(count > 0){
			return false;
		}
		return  true;
	}

	public boolean checkMobileUnique(SysUserEntity user) {
		Long userId = ToolUtil.isEmpty(user.getUserId())?-1L:user.getUserId();
		SysUserEntity info = this.getOne(new QueryWrapper<SysUserEntity>().eq("mobile",user.getMobile()));
		if(ToolUtil.isNotEmpty(info) && !info.getUserId().equals(userId)){
			return  false;
		}
		return true;
	}

	public boolean changeStatus(SysUserEntity user) {
		return sysUserMapper.setStatus(user.getUserId(),user.getStatus()) > 0;
	}

	public SysUserEntity findUserByUserId(Long userId){
		return sysUserMapper.findUserByUserId(userId);
	}
}
