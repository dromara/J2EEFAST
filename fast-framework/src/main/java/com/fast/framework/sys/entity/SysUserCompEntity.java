package com.fast.framework.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 
 * @Description:用户与sys_comp 中间表
 * @author ZhouHuan 18774995071@163.com
 * @time 2019-04-09 22:22
 * @version V1.0 
 的
 *
 */
@TableName("sys_user_comp")
public class SysUserCompEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;
	
	/**
	 * 用户ID
	 */
	private Long userId;
	
	private Long compId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}
	
}
