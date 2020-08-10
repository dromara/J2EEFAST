package com.j2eefast.framework.sys.entity;

import javax.validation.constraints.NotBlank;
import com.baomidou.mybatisplus.annotation.*;
import com.j2eefast.common.core.base.entity.BaseEntity;
import lombok.Data;

/**
 * 角色
 */
@Data
@TableName("sys_role")
public class SysRoleEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	@TableId(value = "id",type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 角色名称
	 */
	@NotBlank(message = "角色名称不能为空")
	private String roleName;


	/**
	 * 删除标志（0代表存在 1代表删除）
	 * */
	@TableLogic
	private String delFlag;

	/**
	 * 角色权限Key
	 * */
	@NotBlank(message = "角色权限不能为空")
	private String roleKey;

	/**
	 * 角色排序
	 **/
	private int roleSort;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 角色权限范围  1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限
	 */
	private String dataScope;

	/**
	 * 用户是否存在此角色标识 默认不存在
	 */
	@TableField(exist = false)
	private boolean flag = false;

	/**
	 * 菜单组
	 */
	@TableField(exist = false)
	private Long[] menuIds;

	/**
	 * 部门组（数据权限）
	 */
	@TableField(exist = false)
	private Long[] deptIds;

	/**
	 * 管理模块
	 */
	@TableField(exist = false)
	private String moduleCodes;
}
