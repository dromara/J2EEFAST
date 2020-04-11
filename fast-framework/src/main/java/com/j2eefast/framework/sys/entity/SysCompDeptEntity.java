package com.j2eefast.framework.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 
 * @Description:公司与地区对应关系
 * @author zhouzhou 18774995071@163.com
 * @time 2018-12-05 15:46
 *
 */
@Data
@TableName("sys_comp_dept")
public class SysCompDeptEntity implements Serializable {

	@TableId(type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 公司ID
	 */
	private Long compId;
	/**
	 * 地区ID
	 */
	private Long deptId;
}
