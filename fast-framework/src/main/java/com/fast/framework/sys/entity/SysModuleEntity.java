package com.fast.framework.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fast.common.core.base.entity.BaseEntity;

import javax.validation.constraints.NotBlank;


/**
 * 
 * 模块表
 * @author zhouzhou
 * @date 2020-03-08 21:10
 */
@TableName("sys_module")
public class SysModuleEntity extends BaseEntity
{

    /** 模块编码 */
	@TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 模块编码 */
    @NotBlank(message = "参数值不能为空")
    private String moduleCode;

    /** 模块名称 */
    @NotBlank(message = "参数值不能为空")
    private String moduleName;

    /** 模块描述 */
    @NotBlank(message = "参数值不能为空")
    private String description;

    /** 主类全名 */
    @NotBlank(message = "参数值不能为空")
    private String mainClassName;

    /** 图标 */
    @NotBlank(message = "参数值不能为空")
    private String icon;

    /**
	 * 逻辑删除 是否删除
	 */
	@TableLogic
	private String delFlag;
	
	
    /** 版本 */
    @NotBlank(message = "参数值不能为空")
    private String currentVersion;

    /** 升级信息 */
    private String upgradeInfo;

    /** 状态（0正常 1停用） */
    private String status;



    public void setId(Long id)
    {
        this.id = id;
    }
    public Long getId()
    {
        return id;
    }

    public void setModuleCode(String moduleCode)
    {
        this.moduleCode = moduleCode;
    }
    public String getModuleCode()
    {
        return moduleCode;
    }

    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }
    public String getModuleName()
    {
        return moduleName;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    public String getDescription()
    {
        return description;
    }

    public void setMainClassName(String mainClassName)
    {
        this.mainClassName = mainClassName;
    }
    public String getMainClassName()
    {
        return mainClassName;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }
    public String getIcon()
    {
        return icon;
    }

    public void setCurrentVersion(String currentVersion)
    {
        this.currentVersion = currentVersion;
    }
    public String getCurrentVersion()
    {
        return currentVersion;
    }

    public void setUpgradeInfo(String upgradeInfo)
    {
        this.upgradeInfo = upgradeInfo;
    }
    public String getUpgradeInfo()
    {
        return upgradeInfo;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
    public String getStatus()
    {
        return status;
    }
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
    
}
