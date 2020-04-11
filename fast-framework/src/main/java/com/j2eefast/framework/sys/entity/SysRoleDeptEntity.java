package com.j2eefast.framework.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色与部门对应关系
 * @author zhouzhou
 */
@Data
@TableName("sys_role_dept")
public class SysRoleDeptEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 部门ID
	 */
	private Long deptId;
}
