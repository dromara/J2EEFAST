package com.j2eefast.framework.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>系统操作系统</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2019-03-20 16:40
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@TableName("sys_oper_log")
@Data
public class SysOperLogEntity implements Serializable {
	/**
	 * 主键ID
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long operId;

	/** 操作模块 */
	private String title;

	/** 业务类型（0其它 1新增 2修改 3删除） */
	private Integer businessType;

	/** 业务类型数组 */
	@TableField(exist = false)
	private Integer[] businessTypes;

	/** 请求方法 */
	private String method;

	/** 请求方式 */
	private String requestMethod;

	/** 操作类别（0其它 1后台用户 2手机端用户） */
	private Integer operatorType;

	/** 操作人员 */
	private String operName;

	/** 部门名称 */
	private String compName;

	/** 请求url */
	private String operUrl;

	/** 操作地址 */
	private String operIp;

	/** 操作地点 */
	private String operLocation;

	/** 请求参数 */
	private String operParam;

	/** 返回参数 */
	private String jsonResult;

	/** 操作状态（0正常 1异常） */
	private Integer status;

	/** 错误消息 */
	private String errorMsg;

	/** 操作时长*/
	private long time;

	// 创建时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") // Jackson包使用注解
	private Date operTime;

}
