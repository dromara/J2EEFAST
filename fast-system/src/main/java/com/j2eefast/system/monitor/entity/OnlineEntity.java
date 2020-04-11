package com.j2eefast.system.monitor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * <p>在线用户信息</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-10 11:33
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
@Data
public class OnlineEntity {

	private Long userId;

	private String sId;

	private String name;

	private String username;

	private String compName;

	private String loginIp;

	private String loginLocation;

	private Integer loginStatus;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	private Date loginTime;
}
