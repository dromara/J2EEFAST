package com.fast.framework.config;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fast.framework.sys.entity.SysUserEntity;
import com.fast.framework.utils.ShiroUtils;

import cn.hutool.core.util.StrUtil;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.fast.common.core.utils.ToolUtil;
import org.springframework.stereotype.Component;

@Component
public class MyInterceptor extends HandlerInterceptorAdapter{


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		//unlock
//		String unlock = request.getParameter("_unlock");
//		if(!StrUtil.isEmpty(unlock)){
//			request.setAttribute("_unlock", "1");
//		}else{
//			request.setAttribute("_unlock", "0");
//		}

		//切换语言
		String language = request.getParameter("_lang");

        if(ToolUtil.isNotEmpty(language)){
        	Cookie c1 = new Cookie("_lang",language);
	    	c1.setMaxAge(60*60*24*7);
	    	c1.setPath("/");
	        response.addCookie(c1);
        }

		if(request.getCookies() == null){
			HttpSession httpSession =request.getSession(true);
			String  sessionId = httpSession.getId();
			Cookie cookie = new Cookie("_secretKey",StrUtil.replace(sessionId,"-",""));
			cookie.setMaxAge(60*60*24*7);
			cookie.setPath("/");
			response.addCookie(cookie);
			request.setAttribute("_secretKey", StrUtil.replace(sessionId,"-",""));
		}else{
			Cookie[] Cookies = request.getCookies();
			boolean mark = false;
			for(int i =0;Cookies !=null && i<Cookies.length;i++){
				Cookie c = Cookies[i];
				if(c.getName().equals("_secretKey")) {
					request.setAttribute("_secretKey", c.getValue());
					mark = true;
				}
			}

			if(!mark){
				HttpSession httpSession =request.getSession();
				String  sessionId = httpSession.getId();
				Cookie cookie = new Cookie("_secretKey",StrUtil.replace(sessionId,"-",""));
				cookie.setMaxAge(60*60*24*7);
				cookie.setPath("/");
				response.addCookie(cookie);
				request.setAttribute("_secretKey", StrUtil.replace(sessionId,"-",""));
			}
		}

		String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String path = request.getContextPath();
        String basePath = scheme + "://" + serverName + ":" + port + path;
        request.setAttribute("ctxStatic", path);
		request.setAttribute("basePath", basePath);
        return super.preHandle(request,response,handler);
	}


}
