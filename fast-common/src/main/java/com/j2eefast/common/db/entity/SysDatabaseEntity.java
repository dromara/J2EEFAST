package com.j2eefast.common.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.j2eefast.common.core.base.entity.BaseEntity;
import com.j2eefast.common.db.utils.DbUtil;

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
	 * 数据库标志名称(系统中的dataSource key name)
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
	

	/** oralce 使用 */
	@TableField(exist=false)
	private String schema;

	/**
	* 获取数据库的schema
	*/
	public String getSchema() {
		if ("mysql".equals(DbUtil.getDbType(this.jdbcDriver))) {
			return "";   //取当前连接的数据库名
		}
		if ("oracle".equals(DbUtil.getDbType(this.jdbcDriver))) {
			return this.getUserName();
		}
		return "";
	}
	
	/** 数据库类型    oacle|mysql|sqlserver */
	@TableField(exist=false)
	private String dbType;
	
	
	public String getDbType() {
		return DbUtil.getDbType(this.jdbcDriver);
	}
	
}
