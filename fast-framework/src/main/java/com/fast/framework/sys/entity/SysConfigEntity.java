package com.fast.framework.sys.entity;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fast.common.core.base.entity.BaseEntity;


/**
 * 系统配置信息
 */
@TableName("sys_config")
public class SysConfigEntity  extends BaseEntity {

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

	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
}
