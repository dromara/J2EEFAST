package com.fast.framework.log.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/**
 * 
 * 登陆日志
 * @author zhouzhou
 * @date 2018-03-13 14:53
 */
@TableName("sys_login_infor")
@Data
public class SysLoginInfoEntity implements Serializable {
	
	private static final long 					serialVersionUID 					= 1L;
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;
	/** 
	 *  登录账号
	 */
	private String username;
	/** 
	 *  登录IP地址
	 */
	private String ipaddr;
	/** 
	 *  登录地点
	 */
	private String loginLocation;
	/** 
	 *  浏览器类型
	 */
	private String browser;
	/**
	 *  系统类型
	 */
	private String os;
	/**
	 * 登录状态（0成功 1失败）
	 */
	private String status;
	/**
	 * 提示消息
	 */
	private String msg;
	/**
	 * 访问时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date loginTime;
	/**
	 * 移动平台(0) 电脑设备(1)
	 */
	private String mobile;
	/**
	 * 公司ID
	 */
	private Long compId;

}
