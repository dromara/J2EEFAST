package com.fast.modules.sys.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fast.common.core.license.annotation.FastLicense;
import com.fast.common.core.utils.*;
import com.fast.framework.manager.factory.AsyncFactory;
import com.fast.framework.sys.entity.SysRoleEntity;
import com.fast.framework.sys.service.SysRoleService;
import com.fast.framework.utils.Constant;
import com.fast.framework.utils.Global;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fast.common.core.crypto.SoftEncryption;
import com.fast.common.core.exception.RxcException;
import com.fast.common.core.exception.ServiceException;
import com.fast.common.core.manager.AsyncManager;
import com.fast.framework.sys.entity.SysUserEntity;
import com.fast.framework.sys.service.SysUserDeptService;
import com.fast.framework.utils.RedisKeys;
import com.fast.framework.utils.ShiroUtils;

/**
 * 
 * 登陆控制类
 * @author zhouzhou
 * @date 2018-11-14 23:28
 */
@Controller
public class SysLoginController {

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysUserDeptService sysUserDeptService;

	@Autowired
	private RedisUtil redisUtil;
	
	/**
	 *生成验证码图片
	 * @author zhouzhou
	 * @date 2020-03-07 14:46
	 */
	@RequestMapping("captcha.gif")
	public void captcha(HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/gif");
		GifCaptcha gifCaptcha = new GifCaptcha(130,48,4);
		gifCaptcha.setCharType(Captcha.TYPE_DEFAULT);
		String result = gifCaptcha.text();
		ShiroUtils.setSessionAttribute(Constant.KAPTCHA_SESSION_KEY, result);
		gifCaptcha.out(response.getOutputStream());
	}


	

	/**
	 * 登陆
	 * @author zhouzhou
	 * @date 2020-03-07 14:47
	 */
	@FastLicense
	@ResponseBody
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public R login(String username, String password, String captcha, Boolean rememberMe, HttpServletRequest request) {

			String secretKey = "";
			Cookie[] cooKies = request.getCookies();
			for(int i =0;cooKies !=null && i<cooKies.length;i++){
				Cookie c = cooKies[i];
				if("_secretKey".equals(c.getName())) {
					request.setAttribute("_secretKey", c.getValue());
					secretKey = c.getValue();
				}
			}
			Subject subject = null;
			try {
				username =new String(SoftEncryption.decryptBySM4(Base64.decode(username),
						HexUtil.decodeHex(secretKey)).get("bytes",byte[].class)).trim();
				password =new String(SoftEncryption.decryptBySM4(Base64.decode(password),
						HexUtil.decodeHex(secretKey)).get("bytes",byte[].class)).trim();
				UsernamePasswordToken token = new UsernamePasswordToken(username, password,rememberMe);
				subject = ShiroUtils.getSubject();
				subject.login(token);
			}catch (ServiceException e){
				return R.error("50006",ToolUtil.message("sys.login.sm4"));
			}
			catch (AuthenticationException e) {
				RxcException ex = (RxcException) e.getCause();
				String msg = ToolUtil.message("sys.login.failure");
				if(!ToolUtil.isEmpty(e.getMessage())){
					msg = e.getMessage();
				}
				if("50004".equals(ex.getCode())) {
					return R.error(ex.getCode(),ex.getMessage());
				}
				return R.error(msg);
			}
		
		
		// 剔除其他此账号在其它地方登录
		if(Global.getConfig("SYS_IS_LOGIN").equals("0")){
			List<Session> loginedList = getLoginedSession(subject);
			for (Session session : loginedList) {
				redisUtil.set("sys:session:" +  ShiroUtils.getUserEntity().getUsername(), "00000", RedisUtil.MINUTE);
				session.stop();
			}
		}
		// 获取用户对应角色权限
		SysUserEntity user = ShiroUtils.getUserEntity();
		List<SysRoleEntity> rs = sysRoleService.selectRolesByUserId(user.getUserId());
		List<Long> roleList = new ArrayList<>();
		List<String> roleKey = new ArrayList<>();
		for(SysRoleEntity r:rs){
			roleList.add(r.getRoleId());
			roleKey.add(r.getRoleKey());
		}
		user.setRoleKeys(roleKey);
		user.setRoleIdList(roleList);
		user.setSid(ShiroUtils.getSubject().getSession().getId().toString());
		List<Long> roleIdList = sysUserDeptService.queryDeptIdList(user.getUserId());
		user.setDeptIdList(roleIdList);
		
		return R.ok();
	}

	// 遍历同一个账户的session
	private List<Session> getLoginedSession(Subject currentUser) {
		Collection<Session> list = ((DefaultSessionManager) ((DefaultSecurityManager) SecurityUtils
				.getSecurityManager()).getSessionManager()).getSessionDAO().getActiveSessions();
		List<Session> loginedList = new ArrayList<Session>();
		SysUserEntity loginUser = (SysUserEntity) currentUser.getPrincipal();
		for (Session session : list) {

			Subject s = new Subject.Builder().session(session).buildSubject();

			if (s.isAuthenticated()) {
				SysUserEntity user = (SysUserEntity) s.getPrincipal();
				if (user.getUsername().equalsIgnoreCase(loginUser.getUsername())) {
					if (!session.getId().equals(currentUser.getSession().getId())) {
						loginedList.add(session);
					}
				}
			}
		}
		return loginedList;
	}

	@RequestMapping(value = "/Account/Lock",method = RequestMethod.GET)
	public String lock(ModelMap mmap) throws Exception {
		mmap.put("avatar", ShiroUtils.getUserEntity().getAvatar());
		SysUserEntity loginUser = ShiroUtils.getUserEntity();
		loginUser.setLoginStatus(-1); //锁屏
		ShiroUtils.setSysUser(loginUser);
		return "lock";
	}


	@ResponseBody
	@RequestMapping(value = "/Account/login", method = RequestMethod.POST)
	public R login(String username, String password, HttpServletRequest request) {

		if(ToolUtil.isNotEmpty(username) && ToolUtil.isNotEmpty(password)){
			SysUserEntity loginUser = ShiroUtils.getUserEntity();

			String secretKey = "";
			Cookie[] Cookies = request.getCookies();
			for(int i =0;Cookies !=null && i<Cookies.length;i++){
				Cookie c = Cookies[i];
				if(c.getName().equals("_secretKey")) {
					request.setAttribute("_secretKey", c.getValue());
					secretKey = c.getValue();
				}
			}
			try{
				username =new String(SoftEncryption.decryptBySM4(Base64.decode(username),
						HexUtil.decodeHex(secretKey)).get("bytes",byte[].class)).trim();

				password =new String(SoftEncryption.decryptBySM4(Base64.decode(password),
						HexUtil.decodeHex(secretKey)).get("bytes",byte[].class)).trim();

			}catch (Exception e){
				throw new RxcException("解密失败,数据异常","50004");
			}
			if(loginUser.getUsername().equals(username)){
				Integer number = redisUtil.get(RedisKeys.getUserLoginKey(username),Integer.class); //用户锁屏密码错误次数

				if( number != null  && number >= Global.getLoginNumCode()) {
					String kaptcha = ShiroUtils.getKaptcha(Constant.KAPTCHA_SESSION_KEY);
					String captcha = (String) ServletUtil.getRequest().getParameter("captcha");
					if (ToolUtil.isEmpty(captcha) || !captcha.equalsIgnoreCase(kaptcha)) {
						throw new RxcException(ToolUtil.message("sys.login.code.error"),"50004");
					}
				}

				password = ShiroUtils.sha256(password, loginUser.getSalt());
				if (password.equals(loginUser.getPassword())){
					loginUser.setLoginStatus(0);
					ShiroUtils.setSysUser(loginUser);
					ShiroUtils.getSession().setAttribute("__unlock","unlock");
					//删除登陆错误
					redisUtil.delete(RedisKeys.getUserLoginKey(username));

					return R.ok();
				}else{
					if(number == null) {
						number = 1;
						redisUtil.set(RedisKeys.getUserLoginKey(username), number, RedisUtil.MINUTE * Global.getLockTime());
					}else {
						number++;
						redisUtil.set(RedisKeys.getUserLoginKey(username), number, RedisUtil.MINUTE * Global.getLockTime());
					}
					AsyncManager.me().execute(AsyncFactory.recordLogininfor(username,loginUser.getCompId(), "50010","锁屏账号或密码不正确,输入错误"+number+" 次!"));
					if(number >= Global.getLoginNumCode()) { //错误3次
						throw new RxcException(ToolUtil.message("sys.login.password.retry.limit.count",Global.getLoginMaxCount()),"50004"); //错误3次
					}
					throw new RxcException(ToolUtil.message("sys.login.password.retry.limit.count",Global.getLoginMaxCount()),"50005");
				}
			}else {
				return R.error("用户名错误");
			}
		}else{
			return R.error("不能为空!");
		}

	}

	/**
	 * 退出
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		ShiroUtils.getSession().stop();
		ShiroUtils.logout();
		return "redirect:login";
	}

}
