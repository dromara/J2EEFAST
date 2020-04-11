package com.j2eefast.system.oss.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.j2eefast.system.validator.group.QiniuGroup;

/**
 * 
 * @Description:更新软件包
 * @author zhouzhou 18774995071@163.com
 * @time 2019-03-15 17:27
的
 *
 */
@TableName("tb_update_page")
public class SysTssEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(type = IdType.INPUT)
	private Long id;

	@NotBlank(message = "版本号不能为空")
	private String version;

	@NotBlank(message = "包名称不能为空")
	private String name;

	private Long size;

	/**
	 * 过期时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	private String remark;

	private String path;
	
	
	private String sizeinfo;
	

	public String getSizeinfo() {
		return sizeinfo;
	}

	public void setSizeinfo(String sizeinfo) {
		this.sizeinfo = sizeinfo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
