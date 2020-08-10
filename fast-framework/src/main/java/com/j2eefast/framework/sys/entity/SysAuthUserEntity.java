package com.j2eefast.framework.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 第三方授权表
 * sys_auth_user
 * @author: ZhouZhou
 * @date 2020-07-24 18:00
 */
@Data
@TableName("sys_auth_user")
public class SysAuthUserEntity {
    /** 主键 */

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    /** 第三方平台用户唯一ID */
    private String uuid;

    /** 系统ID */
    private Long userId;

    /** 用户名称 */
    private String username;

    /** 昵称 */
    private String nickname;

    /** 头像地址 */
    private String avatar;

    /** 邮箱 */
    private String email;

    /** 来源 */
    private String source;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date createTime;
}
