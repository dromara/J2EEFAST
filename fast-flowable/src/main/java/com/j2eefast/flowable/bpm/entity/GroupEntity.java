package com.j2eefast.flowable.bpm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>用户组</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-23 15:26
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Data
@TableName("act_id_group")
public class GroupEntity {

	@TableId(value = "ID_",type = IdType.INPUT)
	private String id;

	@TableField(value = "REV_")
	private int rev;

	@TableField(value = "NAME_")
	private String name;

	@TableField(value = "TYPE_")
	private String type;
}
