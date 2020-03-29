package com.fast.framework.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 
 * @Description:公司与地区对应关系
 * @author ZhouHuan 18774995071@163.com
 * @time 2018-12-05 15:46
的
 *
 */
@TableName("sys_comp_dept")
public class SysCompDeptEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;

	/**
	 * 公司ID
	 */
	private Long compId;

	/**
	 * 地区ID
	 */
	private Long deptId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
}
