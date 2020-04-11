package com.j2eefast.framework.sys.entity;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j2eefast.common.core.base.entity.BaseEntity;
import lombok.Data;

/**
 * <p>系统用户</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2019-03-20 16:40
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
@TableName("sys_user")
@Data
public class SysUserEntity extends BaseEntity {

	/**
	 * 用户ID
	 */
	@TableId
	private Long userId;

	/**
	 * 用户名
	 */
	@NotBlank(message = "登陆账号不能为空")
	private String username;

	/**
	 * 用户名称
	 */
	@NotBlank(message = "姓名不能为空")
	private String name;

	/**
	 * 密码
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	/**
	 * 密码安全级别（0初始 1很弱 2弱 3安全 4很安全）
	 */
	private String pwdSecurityLevel;

	/**
	 * 盐
	 */
	private String salt;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 状态 0：禁用 1：正常
	 */
	private String status;

	@TableLogic
	private String delFlag;

	/**
	 * 用户头像
	 */
	private String avatar;

	/**
	 * 密码修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	private Date pwdUpdateDate;

	/**
	 * 用户对应卡号
	 */
	private String cardId;

	/**
	 * 用户是否在线
	 */
	@TableField(exist = false)
	private Integer loginStatus;

	/**
	 * 角色权限字符列表
	 */
	@TableField(exist = false)
	private List<String> RoleKeys;

	/**
	 * 角色ID列表
	 */
	@TableField(exist = false)
	private List<Long> roleIdList;


	/**
	 * 地区ID列表
	 */
	@TableField(exist = false)
	private List<Long> deptIdList;

	/**
	 * 岗位ID列表
	 */
	@TableField(exist = false)
	private List<String> postCodes;

	/**
	 * 上次登陆时间
	 */
	@TableField(exist = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	private Date loginTime;
	
	/**
	 *上次登陆地点
	 */
	@TableField(exist = false)
	private String loginLocation;

	/**
	 * 当前登陆时间
	 */
	@TableField(exist = false)
	private String nowLoginTime;

	/**
	 * 当前登陆地点
	 */
	@TableField(exist = false)
	private String nowLoginLocation;

	/**
	 * 角色名称
	 */
	@TableField(exist = false)
	private String roleName;

	/**
	 * 岗位名称
	 */
	@TableField(exist = false)
	private String postName;

	/**
	 * 岗位编码
	 */
	@TableField(exist = false)
	private String postCode;
	
	/**
	 * 部门ID --暂时不用
	 */
	private Long deptId;

	/**
	 * 部门名称 --暂时不用
	 */
	@TableField(exist = false)
	private String deptName;

	/**
	 * 公司ID
	 */
	@NotNull(message = "公司不能为空")
	private Long compId;

	/**
	 * 公司名称
	 */
	@TableField(exist = false)
	private String compName;

	/**
	 * 用户登录IP
	 */
	@TableField(exist = false)
	private String ip;
	
	/**
	 * 用户会话ID
	 */
	@TableField(exist = false)
	private String sid;
}
