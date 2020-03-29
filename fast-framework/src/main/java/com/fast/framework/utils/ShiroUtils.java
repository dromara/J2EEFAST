package com.fast.framework.utils;

import com.fast.framework.shiro.realm.UserRealm;
import com.fast.framework.sys.service.SysUserDeptService;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import com.fast.common.core.exception.RxcException;
import com.fast.common.core.utils.ToolUtil;
import com.fast.framework.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 
 * @Description:Shiro工具类
 * @author ZhouHuan 18774995071@163.com
 * @time 2018-11-15 20:20
 * @version V1.0 注意：本内容仅限于聚龙(上海)企业发展有限公司内部传阅，禁止外泄以及用于其他的商业目
 *
 */
public class ShiroUtils {


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

	public static SysUserEntity getUserEntity() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}
	
	public static String getIp()
    {
        return getSubject().getSession().getHost();
    }


	public static void setSysUser(SysUserEntity user)
	{
		Subject subject = getSubject();
		PrincipalCollection principalCollection = subject.getPrincipals();
		String realmName = principalCollection.getRealmNames().iterator().next();

		PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
		// 重新加载Principal
		subject.runAs(newPrincipalCollection);
	}

	public static void clearCachedAuthorizationInfo()
	{
		RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		UserRealm realm = (UserRealm) rsm.getRealms().iterator().next();
		realm.clearCachedAuthorizationInfo();
	}

	public static Long getUserId() {
		return getUserEntity().getUserId();
	}
	
	public static String getLoginName() {
		return getUserEntity().getUsername();
	}

	public static boolean isPermissions(String roleKey){
		List<String> rks =  getUserEntity().getRoleKeys();
		if(rks!=null){
			return  rks.contains(roleKey);
		}else{
			return false;
		}
	}


	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
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
			//throw new RxcException("验证码已失效");
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
