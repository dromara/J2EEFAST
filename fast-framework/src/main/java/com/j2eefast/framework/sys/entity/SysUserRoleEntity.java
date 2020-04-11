package com.j2eefast.framework.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户与角色对应关系
 * @author zhouzhou
 */
@Data
@TableName("sys_user_role")
public class SysUserRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 角色ID
	 */
	private Long roleId;
}
