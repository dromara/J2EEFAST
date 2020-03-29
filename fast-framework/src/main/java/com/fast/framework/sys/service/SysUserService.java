package com.fast.framework.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.common.core.utils.PageUtil;
import com.fast.framework.sys.entity.SysUserEntity;

/**
 * 系统用户
 */
public interface SysUserService extends IService<SysUserEntity> {

	PageUtil queryPage(Map<String, Object> params);


	PageUtil query2UserPage(Map<String, Object> params);

	PageUtil queryRoleToUserPage(Map<String, Object> params);


	PageUtil queryUnallocatedPage(Map<String, Object> params);

	SysUserEntity selectByUserId(Long userId);

	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	List<SysUserEntity> queryAllClear(Long compId);

	List<SysUserEntity> queryAllDriver(Long compId);

	/**
	 * 根据公司ID查询公司管理人员
	 */
	List<SysUserEntity> queryAllMaga(Long compId);
	/**
	 * 保存用户
	 * @return 
	 */
	void insert(SysUserEntity user);

	/**
	 * 修改用户
	 */
	void update(SysUserEntity user);


	int updateUser(SysUserEntity user);

	/**
	 * 修改密码
	 * 
	 * @param userId      用户ID
	 * @param newPassword 新密码
	 * @param pwdSecurityLevel 密码安全级别
	 */
	boolean updatePassword(Long userId, String newPassword,String salt,String pwdSecurityLevel);

	boolean updateUserName(Long userId, String name, String phone,String email);

	boolean updateAvatar(Long userId, String avatar);

	/**
	 * 校验用户名称是否唯一
	 *
	 * @param username 登录名称
	 * @return 结果
	 */
	boolean checkUserNameUnique(String username);

	boolean checkMobileUnique(SysUserEntity user);

	/**
	 * 用户状态修改
	 *
	 * @param user 用户信息
	 * @return 结果
	 */
	int changeStatus(SysUserEntity user);


	List<String> queryAllPerms(Long userId);


	/**
	 * 通过用户名查询用户
	 */
	SysUserEntity queryByUserName(String username);


	/**
	 * 通过用手机号码查询用户
	 */
	SysUserEntity queryByMobile(String mobile);


	/**
	 * 通过用邮箱查询用户
	 */
	SysUserEntity queryByEmail(String email);
	/**
	 * 
	 * 用户登陆查询
	 * 
	 */
//	SysUserEntity login(String username, String password);
	
}
