package com.j2eefast.framework.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.j2eefast.framework.shiro.RedisShiroSessionDAO;
import com.j2eefast.framework.shiro.ShiroSessionListener;
import com.j2eefast.framework.shiro.realm.UserRealm;

/**
 * Shiro的配置文件
 */
@Configuration
public class ShiroConfig {

	/**
	 * Session超时时间，单位为毫秒（默认30分钟）
	 */
	@Value("${shiro.session.expireTime: 30}")
	private int expireTime;

	/**
	 * 设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 (默认设置5分钟)
	 */
	@Value("${shiro.session.validationTime: 5}")
	private int validationTime;

	/**
	 * 设置Cookie的域名
	 */
	@Value("${shiro.cookie.domain: ''}")
	private String domain;

	/**
	 * 设置cookie的有效访问路径设置与项目路径一直
	 */
	@Value("${server.servlet.contextPath: /fast}")
	private String path;

	/**
	 * 设置HttpOnly属性
	 */
	@Value("${shiro.cookie.httpOnly: true}")
	private boolean httpOnly;

	/**
	 * 设置Cookie的过期时间，秒为单位
	 */
	@Value("${shiro.cookie.maxAge: 30}")
	private int maxAge;

	@Bean
    public Cookie cookieDAO() {
       Cookie cookie= new org.apache.shiro.web.servlet.SimpleCookie();
       cookie.setName("fast.session.id");
       return cookie;
    }

	@Bean("sessionManager")
	public SessionManager sessionManager(RedisShiroSessionDAO redisShiroSessionDAO,
			@Value("${framework.redis.open}") boolean redisOpen,
			@Value("${framework.shiro.redis}") boolean shiroRedis) {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		Collection<SessionListener> listeners = new ArrayList<SessionListener>();
		// 配置监听
		listeners.add(sessionListener());

		sessionManager.setSessionListeners(listeners);

		// 设置session过期时间为1小时(单位：毫秒)，默认为30分钟
		sessionManager.setGlobalSessionTimeout(1000 * 60 * expireTime);
		// 去掉 JSESSIONID
		sessionManager.setSessionIdUrlRewritingEnabled(false);

		sessionManager.setSessionIdCookie(cookieDAO());

		// 是否开启删除无效的session对象 默认为true
		sessionManager.setDeleteInvalidSessions(true);

		// 是否开启定时调度器进行检测过期session 默认为true
		sessionManager.setSessionValidationSchedulerEnabled(true);

		// 设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
		// 设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler
		// 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
		sessionManager.setSessionValidationInterval(1000 * 60 * validationTime);

		// 如果开启redis缓存且framework.shiro.redis=true，则shiro session存到redis里
		if (redisOpen && shiroRedis) {
			redisShiroSessionDAO.setSessionIdGenerator(new CustomSessionIdGenerator());
			sessionManager.setSessionDAO(redisShiroSessionDAO);
		}else{
			MemorySessionDAO  sessionDAO = new MemorySessionDAO();
			sessionDAO.setSessionIdGenerator(new CustomSessionIdGenerator());
			sessionManager.setSessionDAO(sessionDAO);
		}

		return sessionManager;
	}

	/**
	 * 缓存管理器 使用Ehcache实现
	 */
	@Bean
	public EhCacheManager getEhCacheManager() {
		net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.getCacheManager("fastOs");
		EhCacheManager em = new EhCacheManager();
		if (ToolUtil.isEmpty(cacheManager)) {
			em.setCacheManager(new net.sf.ehcache.CacheManager(getCacheManagerConfigFileInputStream()));
			return em;
		}
		else {
			em.setCacheManager(cacheManager);
			return em;
		}
	}

	/**
	 * 返回配置文件流 避免ehcache配置文件一直被占用，无法完全销毁项目重新部署
	 */
	protected InputStream getCacheManagerConfigFileInputStream() {
		String configFile = "classpath:ehcache/ehcache-shiro.xml";
		InputStream inputStream = null;
		try {
			inputStream = ResourceUtils.getInputStreamForPath(configFile);
			byte[] b = IOUtils.toByteArray(inputStream);
			InputStream in = new ByteArrayInputStream(b);
			return in;
		}
		catch (IOException e) {
			throw new ConfigurationException(
					"Unable to obtain input stream for cacheManagerConfigFile [" + configFile + "]", e);
		}
		finally {
			IoUtil.close(inputStream);
		}
	}

	@Bean(name = "shiroCacheManager")
	public MemoryConstrainedCacheManager getMemoryConstrainedCacheManager() {
		return new MemoryConstrainedCacheManager();
	}

	/**
	 * 自定义Realm
	 */
	@Bean
	public UserRealm userRealm(EhCacheManager cacheManager) {
		UserRealm userRealm = new UserRealm();
		userRealm.setCacheManager(cacheManager);
		return userRealm;
	}

	@Bean("securityManager")
	public SecurityManager securityManager(UserRealm userRealm, SessionManager sessionManager) {
		
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		
		// 设置realm.
		securityManager.setRealm(userRealm);
		
		// 记住我
        securityManager.setRememberMeManager(rememberMeManager());

        securityManager.setCacheManager(getEhCacheManager());

        securityManager.setSessionManager(sessionManager);
		
		//securityManager.setRememberMeManager(null); //取消记住我
		return securityManager;
	}

	
	
	/**
     * cookie 属性设置
     */
    public SimpleCookie rememberMeCookie()
    {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge * 24 * 60 * 60);
        return cookie;
    }
	
	 /**
     * 记住我
     */
    public CookieRememberMeManager rememberMeManager()
    {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("fCq+/xW488hMTCD+cmJ3aQ=="));
        return cookieRememberMeManager;
    }

	// Shiro连接约束配置,即过滤链的定义
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		shiroFilter.setLoginUrl("/login");
		// 权限认证失败，则跳转到指定页面
		shiroFilter.setUnauthorizedUrl("/");
		//shiroFilter.set
		Map<String, String> filterMap = new LinkedHashMap<>();
		filterMap.put("/swagger/**", "anon");
		filterMap.put("/api/trade.receive/**", "anon");
		filterMap.put("/v2/api-docs", "anon");
		filterMap.put("/swagger-ui.html", "anon");
		filterMap.put("/webjars/**", "anon");
		filterMap.put("/swagger-resources/**", "anon");
		filterMap.put("/profile/fileUeditor/upload/image/**","anon");
		filterMap.put("/statics/**", "anon");
		filterMap.put("/login", "anon");
		filterMap.put("/upbw/**", "anon");
		filterMap.put("/login1.html", "anon");
		filterMap.put("/sys/login", "anon");
		filterMap.put("/favicon.ico", "anon");
		filterMap.put("/captcha.gif", "anon");
		filterMap.put("/**", "user"); //authc
		shiroFilter.setFilterChainDefinitionMap(filterMap);
		return shiroFilter;
	}

	@Bean("sessionListener")
	public ShiroSessionListener sessionListener() {
		ShiroSessionListener sessionListener = new ShiroSessionListener();
		return sessionListener;
	}

	@Bean("lifecycleBeanPostProcessor")
	public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
		proxyCreator.setProxyTargetClass(true);
		return proxyCreator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor( @Qualifier("securityManager")SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	class CustomSessionIdGenerator implements SessionIdGenerator{

		@Override
		public Serializable generateId(Session session) {
			return IdUtil.fastSimpleUUID();
		}
	}
}
