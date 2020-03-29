package com.fast.framework.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fast.framework.sys.entity.SysUserEntity;

/**
 * 系统用户
 */
public interface SysUserDao extends BaseMapper<SysUserEntity> {

	/**
	 * 查询用户的所有权限
	 * 
	 * @param userId 用户ID
	 */
	List<String> queryAllPerms(Long userId);

	SysUserEntity selectByUserId(@Param("userId") Long userId);

	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * 根据公司ID查询公司清机人员
	 */
	List<SysUserEntity> queryAllClear(Long compId);

	/**
	 * 根据公司ID查询公司司机人员
	 */
	List<SysUserEntity> queryAllDriver(Long compId);


	/**
	 * 根据条件分页查询未分配用户角色列表
	 *
	 * @param user 用户信息
	 * @return 用户信息集合信息
	 */
	List<SysUserEntity> selectUnallocatedList(IPage<?> params,@Param("roleId") String roleId,
											  @Param("username") String username ,@Param("mobile") String mobile ,@Param("email") String email ,
											  @Param("compId") String compId,
											  @Param("sql_file") String sql_file);


	List<SysUserEntity> select2All(IPage<?> params,
											  @Param("username") String username ,@Param("mobile") String mobile ,@Param("email") String email ,
								              @Param("status") String status ,
								              @Param("postCode") String postCode ,
											  @Param("sql_file") String sql_file);

	/**
	 * 根据公司ID查询公司管理人员
	 */
	List<SysUserEntity> queryAllMaga(Long compId);
	
	/**
	 * 通过用户名查询用户
	 */
	SysUserEntity queryByUserName(String username);

	/**
	 * 修改用户信息
	 *
	 * @param user 用户信息
	 * @return 结果
	 */
	int updateUser(SysUserEntity user);

	/**
	 * 修改用户密码
	 * @param userId
	 * @param password
	 * @param salt
	 * @return
	 */
	int updatePassWord(@Param("userId") Long userId, @Param("password")
			String password,@Param("salt") String salt,@Param("pwdSecurityLevel") String pwdSecurityLevel);
	
	/**
	 * 通过用邮箱查询用户
	 */
	SysUserEntity queryByEmail(String email);
	
	/**
	 * 通过用手机号码查询用户
	 */
	SysUserEntity queryByMobile(String mobile);

	/**
	 * 校验用户名称是否唯一
	 *
	 * @param username 登录名称
	 * @return 结果
	 */
	int checkUserNameUnique(String username);


	SysUserEntity checkMobileUnique(String mobile);

	/**
	 * 查询用户信息
	 */
	List<SysUserEntity> selectAll(IPage params, @Param("username") String username,
								  @Param("status") String status,@Param("mobile") String mobile ,@Param("email") String email ,
								  @Param("compId") String compId,
								  @Param("sql_file") String sql_file);

	List<SysUserEntity> selectToRoleToUser(IPage params,@Param("roleId") String roleId,
										   @Param("username") String username ,@Param("status") String status ,@Param("mobile") String mobile ,@Param("email") String email ,
										   @Param("compId") String compId,
										   @Param("sql_file") String sql_file);

	/**
	 * 修改头像
	 * @param userId
	 * @param avatar
	 * @return
	 */
	int updateAvatar(@Param("userId") Long userId, @Param("avatar")
			String avatar);
	
	
}
