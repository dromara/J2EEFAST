package com.j2eefast.framework.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>用户岗位关联表</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-10 14:57
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Data
@TableName("sys_user_post")
public class SysUserPostEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.ASSIGN_ID)
	private Long  userId;

	private String postCode;
}
