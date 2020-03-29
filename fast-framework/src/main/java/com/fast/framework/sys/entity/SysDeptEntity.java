package com.fast.framework.sys.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;

//import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
//import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fast.common.core.base.entity.BaseEntity;


/**
 * 部门管理对应
 */
@TableName("sys_dept")
public class SysDeptEntity  extends BaseEntity {
	private static final long serialVersionUID = 1L;

	// 部门ID
	@TableId
	private Long deptId;
	// 上级部门ID，一级部门为0
	private Long parentId;
	// 部门名称
	private String name;
	// 上级部门名称
	@TableField(exist = false)
	private String parentName;
	// 排序
	private Integer orderNum;

//	@TableLogic
//	private Integer delFlag;

	/**
	 * 类型 0：地区 1:汽车线路
	 */
	private Integer type;

	private String status;

	/**
	 * ztree属性
	 */
	@TableField(exist = false)
	private Boolean open;
	@TableField(exist = false)
	private List<?> list;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getDeptId() {
		return deptId;
	}

	/**
	 * 设置：上级部门ID，一级部门为0
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取：上级部门ID，一级部门为0
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * 设置：部门名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取：部门名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置：排序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * 获取：排序
	 */
	public Integer getOrderNum() {
		return orderNum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

//	public Integer getDelFlag() {
//		return delFlag;
//	}
//
//	public void setDelFlag(Integer delFlag) {
//		this.delFlag = delFlag;
//	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
}
