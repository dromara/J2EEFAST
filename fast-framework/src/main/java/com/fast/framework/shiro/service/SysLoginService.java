package com.fast.framework.shiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.fast.common.core.exception.RxcException;
import com.fast.common.core.manager.AsyncManager;
import com.fast.common.core.utils.R;
import com.fast.common.core.utils.RedisUtil;
import com.fast.common.core.utils.ServletUtil;
import com.fast.common.core.utils.ToolUtil;
import com.fast.framework.manager.factory.AsyncFactory;
import com.fast.framework.sys.dao.SysUserDao;
import com.fast.framework.sys.entity.SysUserEntity;
import com.fast.framework.sys.service.SysUserService;
import com.fast.framework.utils.Constant;
import com.fast.framework.utils.Global;
import com.fast.framework.utils.RedisKeys;
import com.fast.framework.utils.ShiroUtils;

/**
 * 
 * @Description:登陆验证
 * @author ZhouHuan 18774995071@163.com
 * @time 2019-04-09 15:10
 * @version V1.0 
 的
 *
 */
@Component
public class SysLoginService {
		
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private SysUserDao sysUserDao;
//	private SysUserService sysUserService;
	
	public SysUserEntity login(String username, String password) {
	
		Integer number = redisUtil.get(RedisKeys.getUserLoginKey(username),Integer.class);
		
		String captcha = (String) ServletUtil.getRequest().getParameter("captcha");
		if( (number != null  && number >= Global.getLoginNumCode()) || ToolUtil.isNotEmpty(captcha)) {
			String kaptcha = ShiroUtils.getKaptcha(Constant.KAPTCHA_SESSION_KEY);
			if (ToolUtil.isEmpty(captcha) || !captcha.equalsIgnoreCase(kaptcha)) {
				throw new RxcException(ToolUtil.message("sys.login.code.error"),"50004");
			}
		}
		
		//用户名或者密码为空
		if(StrUtil.isBlankOrUndefined(username) || StrUtil.isBlankOrUndefined(password) ) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username,-1L, "50005","账号或密码错误,请重试."));
			throw new RxcException(ToolUtil.message("sys.login.failure"),"50005");
		}
		
		SysUserEntity user = sysUserDao.queryByUserName(username);
		
		if(user == null && ReUtil.isMatch(Constant.MOBILE_PHONE_NUMBER_PATTERN, username)) { //手机号码登陆
			user = sysUserDao.queryByMobile(username);
		}
		
		if(user == null && ReUtil.isMatch(Constant.EMAIL_PATTERN,username)) { //邮箱登陆
			user = sysUserDao.queryByEmail(username);
		}
		
		if(user == null) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username,-1L, "50001","账号或密码错误,请重试."));
			throw new RxcException(ToolUtil.message("sys.login.failure"),"50001");
		}
		
		if(user.getStatus() == "1") {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username,user.getCompId(), "50002","账号已被锁定,请联系管理员"));
			throw new RxcException(ToolUtil.message("sys.login.accountDisabled"),"50002");
		}
		
		//判断是否账户是否错误次数过多被锁
//		Integer number = redisUtil.get(RedisKeys.getUserLoginKey(user.getUsername()),Integer.class);
		
		if( number != null  && number >= Global.getLoginMaxCount()) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username,user.getCompId(), "50003","账户被锁定,"+Global.getLockTime()+" 分钟后解锁!"));
			throw new RxcException(ToolUtil.message("sys.login.failedNumLock",Global.getLockTime()),"50003");
		}
		
		//判断密码是否正确
		if(!ShiroUtils.sha256(password, user.getSalt()).equals(user.getPassword())) { //密码不正确
			if(number == null) {
				number = 1;
				redisUtil.set(RedisKeys.getUserLoginKey(user.getUsername()), number, RedisUtil.MINUTE * Global.getLockTime());
			}else {
				number++;
				redisUtil.set(RedisKeys.getUserLoginKey(user.getUsername()), number, RedisUtil.MINUTE * Global.getLockTime());
			}
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username,user.getCompId(), "50004","账号或密码不正确,输入错误"+number+" 次!"));
			if(number >= Global.getLoginNumCode()) { //错误3次
				throw new RxcException(ToolUtil.message("sys.login.password.retry.limit.count",Global.getLoginMaxCount()),"50004"); //错误3次
			}
			throw new RxcException(ToolUtil.message("sys.login.password.retry.limit.count",Global.getLoginMaxCount()),"50005");
		}
		
		//删除登陆错误
		redisUtil.delete(RedisKeys.getUserLoginKey(user.getUsername())); 
		
		AsyncManager.me().execute(AsyncFactory.recordLogininfor(user.getUsername(),user.getCompId(), "00000","登陆成功!"));
		
		return user;
	}
}
