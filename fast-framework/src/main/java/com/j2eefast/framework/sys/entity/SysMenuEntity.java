package com.j2eefast.framework.sys.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import com.j2eefast.common.core.base.entity.BaseEntity;
import lombok.Data;


/**
 * 菜单管理
 */
@Data
@TableName("sys_menu")
public class SysMenuEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

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

}
