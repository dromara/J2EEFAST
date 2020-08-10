package com.j2eefast.framework.sys.entity;

import java.util.List;
import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.*;
import com.j2eefast.common.core.base.entity.BaseEntity;
import lombok.Data;

/**
 * 公司表
 * @author zhouzhou 18774995071@163.com
 * @time 2018-12-04 22:16
 */
@Data
@TableName("sys_comp")
public class SysCompEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 公司ID
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 上级公司ID，一级公司为0
	 */
	private Long parentId;

	/**
	 * 上级部门名称
	 */
	@TableField(exist = false)
	private String parentName;

	/**
	 * 所有父级节点ID集合
	 */
	private String parentIds;

	/**
	 * 公司所属地区ID集合
	 */
	private String  areaIds;

	/**
	 * 租户号
	 */
	private String tenantId;

	/**
	 * 租户名称
	 */
	private String tenantName;

	/**
	 *排序
	 */
	private Integer orderNum;

	/**
	 * 公司名称
	 */
	@NotBlank(message = "公司名称不能为空")
	private String name;

	/**
	 * 公司全称
	 */
	private String fullName;

	/**
	 * 0：公司 ，  1：部门，
	 */
	@NotBlank(message = "类型不能为空")
	private String type;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 是否假删除-改变标志
	 */
	@TableLogic
	private String delFlag;

	/**
	 * ztree属性
	 */
	@TableField(exist = false)
	private Boolean open;
	@TableField(exist = false)
	private List<?> list;

	/**
	 * 地区名称
	 */
	@TableField(exist = false)
	private String deptName;

	/**
	 * 关联地区List
	 */
	@TableField(exist = false)
	private List<Long> deptIdList;

}
