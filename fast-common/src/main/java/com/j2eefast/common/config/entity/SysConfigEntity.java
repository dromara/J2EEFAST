package com.j2eefast.common.config.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.j2eefast.common.core.base.entity.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>系统配置参数</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-01 15:54
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
@Data
@TableName("sys_config")
public class SysConfigEntity extends BaseEntity {

    @TableId
    private Long id;

    @NotBlank(message = "参数名不能为空")
    private String paramKey;

    @NotBlank(message = "参数值不能为空")
    private String paramValue;

    @NotBlank(message = "参数名称不能为空")
    private String paramName;

    @NotBlank(message = "系统默认值不能为空")
    private String configType;

    /**
     * 逻辑删除 是否删除
     */
    @TableLogic
    private String delFlag;

}
