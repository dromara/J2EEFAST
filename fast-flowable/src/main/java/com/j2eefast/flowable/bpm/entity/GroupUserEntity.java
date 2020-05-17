package com.j2eefast.flowable.bpm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>用户用户组中间表</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-23 22:26
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@TableName("act_id_membership")
@Data
public class GroupUserEntity {

	@TableId(value = "USER_ID_",type = IdType.INPUT)
	private String userId;

	@TableField(value = "GROUP_ID_")
	private String groupId;
}
