package com.j2eefast.framework.sys.entity;

import java.util.List;
import com.baomidou.mybatisplus.annotation.*;
import com.j2eefast.common.core.base.entity.BaseEntity;
import lombok.Data;


/**
 * 部门管理对应
 * @author zhouzhou
 */
@Data
@TableName("sys_dept")
public class SysDeptEntity  extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 部门ID
 	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 上级部门ID，一级部门为0
	 */
	private Long parentId;

	/**
	 * 部门名称
	 */
	private String name;

	/**
	 * 排序
	 */
	private Integer orderNum;

	@TableLogic
	private String delFlag;

	/**
	 * 类型 0：地区 1:汽车线路
	 */
	private Integer type;

	private String status;

	/**
	 * 上级部门名称
	 */
	@TableField(exist = false)
	private String parentName;
	/**
	 * ztree属性
	 */
	@TableField(exist = false)
	private Boolean open;
	@TableField(exist = false)
	private List<?> list;
}
