package com.fast.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fast.framework.sys.entity.SysUserEntity;

/**
 * 用户 Mapper 接口
 * @author zhouzhou
 * @date 2020-03-08 21:20
 */
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

    /**
     * 修改用户
     * @param user
     * @return
     */
    int updateUser(SysUserEntity user);

    /**
     * 修改用户密码
     * @param userId 用户ID
     * @param newPassword 新密码
     * @param salt 盐值
     * @param pwdSecurityLevel 密码安全等级
     * @return
     */
    int updatePassword(Long userId, String newPassword,String salt,String pwdSecurityLevel);

    /**
     * 修改头像
     * @param userId 用户ID
     * @param avatar 头像路径
     * @return
     */
    int updateAvatar(Long userId, String avatar);

}
