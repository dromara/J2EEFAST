package com.j2eefast.framework.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * 用户与地区对应关系
 * @author zhouzhou 18774995071@163.com
 * @time 2018-12-05 22:43
 */
@Data
@TableName("sys_user_dept")
public class SysUserDeptEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 地区ID
	 */
	private Long deptId;

	@TableField(exist = false)
	private String name;

}
