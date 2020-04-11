package com.j2eefast.framework.manager.factory;

import java.util.Date;
import java.util.TimerTask;

import com.j2eefast.common.core.utils.HttpContextUtil;
import com.j2eefast.framework.log.entity.SysLoginInfoEntity;
import com.j2eefast.framework.log.entity.SysOperLogEntity;
import com.j2eefast.framework.log.service.SysLoginInfoSerice;
import com.j2eefast.framework.log.service.SysOperLogSerice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.j2eefast.common.core.utils.AddressUtil;
import com.j2eefast.common.core.utils.ServletUtil;
import com.j2eefast.common.core.utils.SpringUtil;
import com.j2eefast.framework.utils.UserUtils;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;

/**
 * 异步工厂
 * @author zhouzhou
 * @date 2017-03-12 16:40
 */
public class AsyncFactory {
	
	 private static final Logger 					LOG 					= LoggerFactory.getLogger(AsyncFactory.class);


	/**
	 * 操作日志记录
	 *
	 * @param operLog 操作日志信息
	 * @return 任务task
	 */
	public static TimerTask recordOper(final SysOperLogEntity operLog){
		
		return new TimerTask(){
			@Override
			public void run(){
				// 远程查询操作地点
				operLog.setOperLocation(AddressUtil.getRealAddressByIP(operLog.getOperIp()));
				SpringUtil.getBean(SysOperLogSerice.class).save(operLog);
			}
		};
	}

	 /**
	     * 记录登陆信息
	     * 
	     * @param username 用户名
	     * @param status 状态
	     * @param message 消息
	     * @param args 列表
	     * @return 任务task
	     */
	    public static TimerTask recordLogininfor(final String username, final Long compId, final String status,
												 final String message,final Date loginDate,final String location, final Object... args){
			final UserAgent userAgent =   UserAgentUtil.parse(HttpContextUtil.getRequest().getHeader("User-Agent"));
			final String ip = HttpContextUtil.getIp();
	        return new TimerTask(){
	            @Override
	            public void run(){
	                // 获取客户端操作系统
	                String os = userAgent.getOs().toString();
	                // 获取客户端浏览器
	                String browser = userAgent.getBrowser().toString();
	                // 封装对象
	                SysLoginInfoEntity logininfor = new SysLoginInfoEntity();
	                logininfor.setUsername(username);
	                logininfor.setIpaddr(ip);
	                logininfor.setLoginLocation(location);
	                logininfor.setBrowser(browser);
	                logininfor.setOs(os);
					logininfor.setCompId(compId);
	                logininfor.setMsg(message);
	                
	                if(userAgent.isMobile()) {
	                	logininfor.setMobile("0");
	                }else {
	                	logininfor.setMobile("1");
	                }
	                // 日志状态
	                logininfor.setStatus(status);
	                logininfor.setLoginTime(loginDate);
	                // 插入数据
	                SpringUtil.getBean(SysLoginInfoSerice.class).save(logininfor);
	                LOG.info("记录登陆信息!");
	            }
	        };
	    }

	/**
	 * 记录登陆信息
	 *
	 * @param username 用户名
	 * @param status 状态
	 * @param message 消息
	 * @param args 列表
	 * @return 任务task
	 */
	public static TimerTask recordLogininfor(final String username,final Long compId, final String status, final String message, final Object... args){
		final UserAgent userAgent =   UserAgentUtil.parse(HttpContextUtil.getRequest().getHeader("User-Agent"));
		final String ip = HttpContextUtil.getIp();
		return new TimerTask(){
			@Override
			public void run(){
				// 获取客户端操作系统
				String os = userAgent.getOs().toString();
				// 获取客户端浏览器
				String browser = userAgent.getBrowser().toString();
				// 封装对象
				SysLoginInfoEntity logininfor = new SysLoginInfoEntity();
				logininfor.setUsername(username);
				logininfor.setIpaddr(ip);
				logininfor.setLoginLocation(AddressUtil.getRealAddressByIP(ip));
				logininfor.setBrowser(browser);
				logininfor.setOs(os);
				logininfor.setCompId(compId);
				logininfor.setMsg(message);
				if(userAgent.isMobile()) {
					logininfor.setMobile("0");
				}else {
					logininfor.setMobile("1");
				}
				// 日志状态
				logininfor.setStatus(status);
				logininfor.setLoginTime(DateUtil.date());
				// 插入数据
				SpringUtil.getBean(SysLoginInfoSerice.class).save(logininfor);
				LOG.info("记录登陆信息!");
			}
		};
	}
}
