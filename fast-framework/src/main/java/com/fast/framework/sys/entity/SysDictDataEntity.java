package com.fast.framework.sys.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fast.common.core.base.entity.BaseEntity;


/**
 * 字典数据表
 */
@TableName("sys_dict_data")
public class SysDictDataEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long dictCode;

	/** 字典排序 */
	private Long dictSort;

	/** 字典标签 */
	private String dictLabel;


	/** 字典键值 */
	private String dictValue;

	/** 字典类型 */
	private String dictType;

	/** 是否系统内置*/
	private String isSys;

	/** 样式属性（其他样式扩展） */
	private String cssClass;

	/* css样式（如：color:red)*/
	private String cssStyle;

	/** 表格字典样式 */
	private String listClass;

	/** 是否默认（Y是 N否） */
	private String isDefault;

	/** 状态（0正常 1停用） */
	private String status;

	public String getIsSys() {
		return isSys;
	}

	public void setIsSys(String isSys) {
		this.isSys = isSys;
	}

	public Long getDictCode() {
		return dictCode;
	}

	public void setDictCode(Long dictCode) {
		this.dictCode = dictCode;
	}

	public Long getDictSort() {
		return dictSort;
	}

	public void setDictSort(Long dictSort) {
		this.dictSort = dictSort;
	}

	public String getDictLabel() {
		return dictLabel;
	}

	public void setDictLabel(String dictLabel) {
		this.dictLabel = dictLabel;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getListClass() {
		return listClass;
	}

	public void setListClass(String listClass) {
		this.listClass = listClass;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}
}
