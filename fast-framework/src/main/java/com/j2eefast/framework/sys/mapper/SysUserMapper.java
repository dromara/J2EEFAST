package com.j2eefast.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j2eefast.framework.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户 Mapper 接口
 * @author zhouzhou
 * @date 2020-03-08 21:20
 */
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

    /**
     * 用户页面查询分页
     * @return
     */
    Page<SysUserEntity> findPage(IPage<?> params,
                                 @Param("username") String username,
                                 @Param("status") String status,
                                 @Param("mobile") String mobile,
                                 @Param("email") String email,
                                 @Param("compId") String compId,
                                 @Param("sql_filter") String sql_filter);


    List<SysUserEntity> findList(@Param("username") String username,
                                 @Param("status") String status,
                                 @Param("mobile") String mobile,
                                 @Param("email") String email,
                                 @Param("compId") String compId,
                                 @Param("sql_filter") String sql_filter);

    /**
     *通过角色ID查询所有用户
     * @return
     */
    Page<SysUserEntity> findUserByRolePage(IPage<?> params,
                                           @Param("roleId") String roleId,
                                           @Param("username") String username,
                                           @Param("status") String status,
                                           @Param("mobile") String mobile,
                                           @Param("email") String email,
                                           @Param("compId") String compId,
                                           @Param("sql_filter") String sql_filter);

    /**
     * 根据条件分页查询未分配用户角色列表
     * @return
     */
    Page<SysUserEntity> findUnallocatedList(IPage<?> params,
                                            @Param("roleId") String roleId,
                                            @Param("username") String username ,
                                            @Param("mobile") String mobile ,
                                            @Param("email") String email ,
                                            @Param("compId") String compId,
                                            @Param("sql_filter") String sql_filter);

    /**
     * 修改用户
     * @param user
     * @return
     */
    int updateUser(SysUserEntity user);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> findAllMenuId(Long userId);

    /**
     * 修改用户密码
     * @param userId 用户ID
     * @param password 新密码
     * @param salt 盐值
     * @param pwdSecurityLevel 密码安全等级
     * @return
     */
    int updatePassWord(@Param("userId") Long userId,
                       @Param("password") String password,
                       @Param("salt") String salt,
                       @Param("pwdSecurityLevel") String pwdSecurityLevel);

    /**
     * 修改头像
     * @param userId 用户ID
     * @param avatar 头像路径
     * @return
     */
    int updateAvatar(@Param("userId") Long userId,
                     @Param("avatar") String avatar);


    /**
     * 修改状态
     * @param userId
     * @param status
     * @return
     */
    int setStatus(@Param("userId") Long userId,
                  @Param("status") String status);


    /**
     * 通过用户账号获取用户信息
     * @param userName
     * @return
     */
    SysUserEntity findUserByUserName(@Param("userName") String userName);


    /**
     * 手机号码获取用户信息
     * @param mobile
     * @return
     */
    SysUserEntity findUserByMobile(@Param("mobile") String mobile);


    /**
     * 邮箱获取用户信息
     * @param email
     * @return
     */
    SysUserEntity findUserByEmail(@Param("email") String email);


    /**
     * 通过用户ID获取所属公司
     * @param userId
     * @return
     */
    String findCompNameByUserId(@Param("userId") Long userId);


    /**
     * 通过用户ID获取完整用户信息
    * @param userId
     * @return
     */
    SysUserEntity findUserByUserId(@Param("userId") Long userId);
}
