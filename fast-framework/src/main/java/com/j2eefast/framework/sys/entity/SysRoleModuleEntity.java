package com.j2eefast.framework.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色与模块中间表
 * @author: zhouzhou Emall:18774995071@163.com
 * @time 2020/2/15 11:29
 * @version V1.0
 */
@Data
@TableName("sys_role_module")
public class SysRoleModuleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long roleId;

    private String moduleCode;
}
