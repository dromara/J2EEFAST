package com.j2eefast.common.core.base.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 树对象
 * @author zhouzhou
 * @date 2020-03-12 21:34
 */
public class Ztree implements Serializable{

	private static final long 					serialVersionUID 					= 1L;

	/** 节点ID */
    private Long id;

    /** 节点父ID */
    private Long pId;

    /** 获取节点值 */
    private String name;

    /** 节点页面显示 有时候我们需要显示的值与获取的值不一致此处就可以用上,你也可以name 与 title 一样 */
    private String title;

    /** 类型*/
    private String type;

    /** 是否勾选 */
    private boolean checked = false;

    /** 是否展开 */
    private boolean open = false;

    private boolean isParent = false;

    /** 是否能勾选 */
    private boolean nocheck = false;

    /** 设置节点的 checkbox / radio 是否禁用*/
    private boolean chkDisabled = false;


    public boolean isChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public boolean getIsParent() {
        return isParent;
    }


    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getpId()
    {
        return pId;
    }

    public void setpId(Long pId)
    {
        this.pId = pId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

    public boolean isOpen()
    {
        return open;
    }

    public void setOpen(boolean open)
    {
        this.open = open;
    }

    public boolean isNocheck()
    {
        return nocheck;
    }

    public void setNocheck(boolean nocheck)
    {
        this.nocheck = nocheck;
    }
	
	

}
