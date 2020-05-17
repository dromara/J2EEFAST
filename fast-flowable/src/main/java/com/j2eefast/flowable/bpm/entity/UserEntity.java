package com.j2eefast.flowable.bpm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>用户信息</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-23 22:18
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Data
@TableName("act_id_user")
public class UserEntity {
	@TableId(value = "ID_",type = IdType.INPUT)
	protected String id;
	@TableField(value = "REV_")
	private int rev;
	@TableField(value = "FIRST_")
	protected String firstName;
	@TableField(value = "LAST_")
	protected String lastName;
	@TableField(value = "DISPLAY_NAME_")
	protected String dispalyName;
	@TableField(value = "EMAIL_")
	protected String email;
}
