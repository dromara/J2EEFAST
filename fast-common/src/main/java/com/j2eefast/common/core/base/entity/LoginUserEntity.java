package com.j2eefast.common.core.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>用于专门存放登陆用户信息</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-07 09:17
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Data
public class LoginUserEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户主键ID
	 */
	private Long id;

	/**
	 * 账号
	 */
	private String username;

	/**
	 * 手机
	 */
	private String mobile;

	/**
	 * 盐
	 */
	private String salt;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 公司ID
	 */
	private Long compId;

	/**
	 * 部门id
	 */
	private Long deptId;

	/**
	 * 角色集
	 */
	private List<Long> roleList;

	/**
	 * 部门名称
	 */
	private String deptName;

	/**
	 * 后端密码安全等级
	 */
	private String pwdSecurityLevel;

	/**
	 * 密码修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	private Date pwdUpdateDate;
	/**
	 * 公司名称
	 */
	private String compName;

	/**
	 * 角色名称集
	 */
	private List<String> roleNames;

	/**
	 * 角色备注（code）
	 */
	private List<String> roleKey;

	/**
	 * 角色权限范围 0=未设置, 1=仅本人数据, 2=本公司数据,3=本部门及以下数据权限,4=本部门数据权限, 5=自定义数据权限  6 所有数据权限
	 * 数据权限范围 优先级 如果多个数据权限范围 则已最大优先
	 * 例如:
	 * 有 角色1-数据范围6   角色2-数据权限5  角色3-数据权限1
	 * 如果都把这些角色都分配给这个用户- 则此用户权限为6 所有数据权限
	 */
//	private List<String> dataScope;

	/**
	 * 系统标识集合
	 */
	private List<Map<String, Object>> modules;

	/**
	 * 用户角色对应菜单的权限
	 */
	private List<Map<Object,Object>> rolePerm;

	/**
	 * 拥有菜单的权限
	 */
	private Set<String> permissions;

	/**
	 * 租户编码
	 */
	private String tenantCode;

	/**
	 * 租户的数据源名称
	 */
	private String tenantDataSourceName;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	private Date loginTime;

	/**
	 * 登录IP
	 */
	private String loginIp;

	/**
	 * 登陆状态
	 */
	private Integer loginStatus;

	/**
	 *上次登陆地点
	 */
	private String loginLocation;

	/**
	 * 当前登陆时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	private Date nowLoginTime;

	/**
	 * 当前登陆地点
	 */
	private String nowLoginLocation;


	private String sId;

}
