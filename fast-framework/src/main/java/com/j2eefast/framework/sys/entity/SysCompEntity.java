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
	private Long compId;

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
	 *排序
	 */
	private Integer orderNum;

	/**
	 * 公司名称
	 */
	@NotBlank(message = "公司名称不能为空")
	private String name;


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
