package com.j2eefast.framework.utils;

import cn.hutool.crypto.SecureUtil;
import com.j2eefast.common.core.base.entity.LoginUserEntity;
import com.j2eefast.framework.shiro.realm.UserRealm;
import com.j2eefast.framework.sys.service.SysUserDeptService;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 
 * 登陆用户信息
 * @author zhouzhou 18774995071@163.com
 * @time 2018-11-15 20:20
 * @version V1.0
 *
 */
public class UserUtils {


	/** 加密算法 */
	public final static String hashAlgorithmName = "SHA-256";
	/** 循环次数 */
	public final static int hashIterations = 16;

	public static String sha256(String password, String salt) {
		return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
	}

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取用户信息
	 * @return
	 */
	public static LoginUserEntity getUserInfo() {
		return (LoginUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 获取登陆IP
	 * @return
	 */
	public static String getIp(){
        return getSubject().getSession().getHost();
    }


	/**
	 * 重新装载用户信息--> 修改了当前用户信息需要调用
	 * @param user
	 */
	public static void reloadUser(LoginUserEntity user){
		Subject subject = getSubject();
		PrincipalCollection principalCollection = subject.getPrincipals();
		String realmName = principalCollection.getRealmNames().iterator().next();
		PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
		// 重新加载Principal
		subject.runAs(newPrincipalCollection);
	}

	/**
	 * 清空缓存
	 */
	public static void clearCachedAuthorizationInfo() {
		RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		UserRealm realm = (UserRealm) rsm.getRealms().iterator().next();
		realm.clearCachedAuthorizationInfo();
	}

	/**
	 * 用户ID
	 * @return
	 */
	public static Long getUserId() {
		return getUserInfo().getId();
	}

	/**
	 * 用户账号
	 * @return
	 */
	public static String getLoginName() {
		return getUserInfo().getUsername();
	}

	/**
	 * 校验
	 * @param roleKey
	 * @return
	 */
	public static boolean hasRole(String roleKey){
		return  getSubject().hasRole(roleKey);
	}


	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static void removeSessionAttribute(String key){
		getSession().removeAttribute(key);
	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static void logout() {
		SecurityUtils.getSubject().logout();
	}

	public static String getKaptcha(String key) {
		Object kaptcha = getSessionAttribute(key);
		if (kaptcha == null) {
			throw new RxcException(ToolUtil.message("sys.login.code.invalid"),"50004");
		}
		getSession().removeAttribute(key);
		return kaptcha.toString();
	}

	/**
	 * 生成随机盐
	 */
	public static String randomSalt()
	{
		return RandomStringUtils.randomAlphanumeric(20);
	}

}
