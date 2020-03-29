package com.fast.framework.shiro.realm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fast.common.core.exception.RxcException;
import com.fast.common.core.utils.ToolUtil;
import com.fast.framework.shiro.service.SysLoginService;
import com.fast.framework.sys.dao.SysMenuDao;
import com.fast.framework.sys.dao.SysUserDao;
import com.fast.framework.sys.entity.SysMenuEntity;
import com.fast.framework.sys.entity.SysUserEntity;
import com.fast.framework.sys.service.SysUserService;
import com.fast.framework.utils.Constant;
import com.fast.framework.utils.ShiroUtils;


/**
 * 自定义的指定Shiro验证用户登录的类 认证
 * @author zhouzhou
 * @date 2020-03-12 20:57
 */
//@Component
public class UserRealm extends AuthorizingRealm {
	
	private final static Logger 					LOG 					= LoggerFactory.getLogger(UserRealm.class);
	
	
	@Autowired
	private SysUserDao sysUserDao;
//	private SysUserService sysUserService;

//	@Autowired
//	private SysMenuDao sysMenuDao;
	@Autowired
	private SysLoginService sysLoginService;
	
	


	/**
	 * 授权(验证权限时调用)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
		Long userId = user.getUserId();

		List<String> permsList;
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 系统管理员，拥有最高权限
		if (userId.equals(Constant.SUPER_ADMIN)
				|| user.getUsername().equals("admin123")) { //测试账号
//			List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
//			permsList = new ArrayList<>(menuList.size());
//			for (SysMenuEntity menu : menuList) {
//				permsList.add(menu.getPerms());
//			}
			info.addRole("ADMIN");
			info.addStringPermission("*:*:*");
		} else {
			permsList = sysUserDao.queryAllPerms(userId);
			// 用户权限列表
			Set<String> permsSet = new HashSet<>();
			for (String perms : permsList) {
				if (ToolUtil.isEmpty(perms)) {
					continue;
				}
				permsSet.addAll(Arrays.asList(perms.trim().split(",")));
			}
			info.setStringPermissions(permsSet);
		}
		return info;
	}

	/**
	 * 认证(登录时调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = token.getUsername();
		String password = "";
		if (token.getPassword() != null)
        {
			password = new String(token.getPassword());
        }
		// 查询用户信息
		SysUserEntity user = new SysUserEntity();
		try {
			user = sysLoginService.login(username, password);
		}catch (RxcException e) {
			//不同异常不同抛出
			if(e.getCode().equals("50001")) {
				throw new UnknownAccountException(e.getMessage(), e);
			}else if(e.getCode().equals("50002")) {
				throw new LockedAccountException(e.getMessage(), e);
			}else if(e.getCode().equals("50003")) {
				throw new ExcessiveAttemptsException(e.getMessage(), e);
			}else if(e.getCode().equals("50005")) {
				throw new IncorrectCredentialsException(e.getMessage(), e);
			}else if(e.getCode().equals("50004")) {
				throw new UnknownAccountException(e.getMessage(), e);
			}
		}catch (Exception e)
        {
			LOG.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
		
//		user = sysUserDao.selectOne(user);
//
//		System.out.println("+++++++++++++++++++++++++登陆0");
//		// 账号不存在
//		if (user == null) {
//			throw new UnknownAccountException("账号或密码不正确");
//		}
//
//		// 账号锁定
//		if (user.getStatus() == 0) {
//			throw new LockedAccountException("账号已被锁定,请联系管理员");
//		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(),
				ByteSource.Util.bytes(user.getSalt()), getName());
		return info;
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
		shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
		shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
		shaCredentialsMatcher.setStoredCredentialsHexEncoded(true);
		super.setCredentialsMatcher(shaCredentialsMatcher);
	}

	/**
	 * 清理缓存权限
	 */
	public void clearCachedAuthorizationInfo()
	{
		this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
	}
}
