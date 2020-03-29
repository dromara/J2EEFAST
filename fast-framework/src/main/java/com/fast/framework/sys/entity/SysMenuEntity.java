package com.fast.framework.sys.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fast.common.core.base.entity.BaseEntity;


/**
 * 菜单管理
 */
@TableName("sys_menu")
public class SysMenuEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@TableId
	private Long menuId;

	/**
	 * 菜单转换页面ID
	 */
	@TableField(exist = false)
	private String mId;

	/**
	 *菜单是否为新的
	 */
	@TableField(exist = false)
	private boolean mNew;

	/**
	 * 父菜单ID，一级菜单为0
	 */
	private Long parentId;

	/**
	 * 父菜单名称
	 */
	@TableField(exist = false)
	private String parentName;

	/**
	 * 菜单名称
	 */
	private String name;

	/**
	 * 菜单URL
	 */
	private String url;

	/**
	 * 菜单打开方式
	 */
	private String target;

	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	@TableField(insertStrategy = FieldStrategy.IGNORED)
	private String perms;

	/**
	 *归属模块（多个用逗号隔开）
	 */
	@TableField(insertStrategy = FieldStrategy.IGNORED)
	private String moduleCodes;

	/**
	 * 类型 0：目录 1：菜单 2：按钮
	 */
	private Integer type;

	/**
	 * 菜单图标
	 */
	private String icon;

	/**
	 * 排序
	 */
	private Integer orderNum;


//	@TableLogic
//	private Integer delFlag;
	/**
	 * 是否隐藏
	 */
	private Integer hide;

	/** 子菜单 */
	@TableField(exist = false)
	private List<SysMenuEntity> children = new ArrayList<SysMenuEntity>();

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getmId() {
		return mId;
	}

	public boolean ismNew() {
		return mNew;
	}

	public void setmNew(boolean mNew) {
		this.mNew = mNew;
	}

	public void setmId(String mId) {
		this.mId = mId;
	}

	public Long getMenuId() {
		return menuId;
	}

	/**
	 * 设置：父菜单ID，一级菜单为0
	 * 
	 * @param parentId 父菜单ID，一级菜单为0
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取：父菜单ID，一级菜单为0
	 * 
	 * @return Long
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * 设置：菜单名称
	 * 
	 * @param name 菜单名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取：菜单名称
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置：菜单URL
	 * 
	 * @param url 菜单URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取：菜单URL
	 * 
	 * @return String
	 */
	public String getUrl() {
		return url;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public Integer getType() {
		return type;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 设置：菜单图标
	 * 
	 * @param icon 菜单图标
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 获取：菜单图标
	 * 
	 * @return String
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * 设置：排序
	 * 
	 * @param orderNum 排序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * 获取：排序
	 * 
	 * @return Integer
	 */
	public Integer getOrderNum() {
		return orderNum;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getModuleCodes() {
		return moduleCodes;
	}

	public void setModuleCodes(String moduleCodes) {
		this.moduleCodes = moduleCodes;
	}

	//	public Integer getDelFlag() {
//		return delFlag;
//	}
//
//	public void setDelFlag(Integer delFlag) {
//		this.delFlag = delFlag;
//	}

	public Integer getHide() {
		return hide;
	}

	public void setHide(Integer hide) {
		this.hide = hide;
	}

	public List<SysMenuEntity> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenuEntity> children) {
		this.children = children;
	}
}
