package com.j2eefast.framework.sys.factory;

import com.j2eefast.common.core.base.entity.LoginUserEntity;
import com.j2eefast.framework.sys.constant.factory.ConstantFactory;
import com.j2eefast.framework.sys.entity.SysUserEntity;

/**
 * <p>用户创建工厂</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-07 10:00
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public class UserFactory {

	/**
	 * 通过用户表的信息创建一个登录用户
	 */
	public static LoginUserEntity createLoginUser(SysUserEntity user) {
		LoginUserEntity loginUser = new LoginUserEntity();

		if (user == null) {
			return loginUser;
		}
		loginUser.setId(user.getUserId());
		loginUser.setUsername(user.getUsername());
		loginUser.setDeptId(user.getDeptId());
		loginUser.setCompName(ConstantFactory.me().getCompName(user.getUserId()));
		loginUser.setCompId(user.getCompId());
		loginUser.setName(user.getName());
		loginUser.setPwdUpdateDate(user.getPwdUpdateDate());
		loginUser.setMobile(user.getMobile());
		loginUser.setPassword(user.getPassword());
		loginUser.setSalt(user.getSalt());
		loginUser.setEmail(user.getEmail());
		loginUser.setPwdSecurityLevel(user.getPwdSecurityLevel());
		loginUser.setAvatar(user.getAvatar());

		return loginUser;
	}
}
