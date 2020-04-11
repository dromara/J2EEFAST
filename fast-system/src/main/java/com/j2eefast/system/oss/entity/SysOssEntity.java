package com.j2eefast.system.oss.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 文件上传
 */
@TableName("sys_oss")
public class SysOssEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;
	/**
	 * URL地址
	 */
	private String url;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：URL地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取：URL地址
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
}
