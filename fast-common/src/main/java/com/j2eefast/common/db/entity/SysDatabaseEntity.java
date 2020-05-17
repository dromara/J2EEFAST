package com.j2eefast.common.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.j2eefast.common.core.base.entity.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>多源数据配置表</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-15 10:15
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Data
@TableName("sys_database")
public class SysDatabaseEntity extends BaseEntity {

	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 数据库标志名称
	 */
	@NotBlank(message = "参数值不能为空")
	private String dbName;

	/**
	 * jdbc的驱动类型
	 */
	@NotBlank(message = "参数值不能为空")
	private String jdbcDriver;

	/**
	 * 数据库账号
	 */
	@NotBlank(message = "参数值不能为空")
	private String userName;

	/**
	 * 数据库密码
	 */
	@NotBlank(message = "参数值不能为空")
	private String password;

	/**
	 * 数据库连接URL
	 */
	@NotBlank(message = "参数值不能为空")
	private String jdbcUrl;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 逻辑删除 是否删除
	 */
	@TableLogic
	private String delFlag;

	/**
	 * 加密类型
	 */
	@NotBlank(message = "参数值不能为空")
	private String encType;
}
