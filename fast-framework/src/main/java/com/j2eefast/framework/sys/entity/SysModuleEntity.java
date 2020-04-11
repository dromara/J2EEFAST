package com.j2eefast.framework.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.j2eefast.common.core.base.entity.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * 
 * 模块表
 * @author zhouzhou
 * @date 2020-03-08 21:10
 */
@TableName("sys_module")
@Data
public class SysModuleEntity extends BaseEntity {

    /**
     * 模块编码
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 模块编码
     */
    @NotBlank(message = "参数值不能为空")
    private String moduleCode;

    /**
     * 模块名称
     */
    @NotBlank(message = "参数值不能为空")
    private String moduleName;

    /**
     * 模块描述
     */
    @NotBlank(message = "参数值不能为空")
    private String description;

    /**
     * 主类全名
     */
    @NotBlank(message = "参数值不能为空")
    private String mainClassName;

    /**
     * 图标
     */
    @NotBlank(message = "参数值不能为空")
    private String icon;

    /**
     * 逻辑删除 是否删除
     */
    @TableLogic
    private String delFlag;


    /**
     * 版本
     */
    @NotBlank(message = "参数值不能为空")
    private String currentVersion;

    /**
     * 升级信息
     */
    private String upgradeInfo;

    /**
     * 状态（0正常 1停用）
     */
    private String status;
}
