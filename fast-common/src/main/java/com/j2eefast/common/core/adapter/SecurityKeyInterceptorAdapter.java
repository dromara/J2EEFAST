package com.j2eefast.common.core.adapter;

import cn.hutool.core.util.IdUtil;
import com.j2eefast.common.core.constants.ConfigConstant;
import com.j2eefast.common.core.utils.CookieUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 安全key,多语言 拦截器
 * @author zhouzhou
 */
@Component
public class SecurityKeyInterceptorAdapter extends HandlerInterceptorAdapter{


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		//切换语言
		String language = request.getParameter(ConfigConstant.LANGUAGE);

		//设置语言 Cookie
        if(ToolUtil.isNotEmpty(language)){
			CookieUtil.setReadCookie(response,ConfigConstant.LANGUAGE,language,60*60*24*7);
        }
		String _secretKey = "";
		//获取登录安全Key
		if(request.getCookies() == null){
			_secretKey = IdUtil.simpleUUID().toLowerCase();
			CookieUtil.setReadCookie(response,ConfigConstant.SECRETKEY, _secretKey,60*30);

		}else{
			_secretKey = CookieUtil.getCookie(request,ConfigConstant.SECRETKEY);
			if(ToolUtil.isEmpty(_secretKey)){
				_secretKey = IdUtil.simpleUUID().toLowerCase();
				CookieUtil.setReadCookie(response,ConfigConstant.SECRETKEY, _secretKey,60*30);
			}
		}
		request.setAttribute(ConfigConstant.SECRETKEY, _secretKey);
		String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String path = request.getContextPath();
        String basePath = scheme + "://" + serverName + ":" + port + path;
        request.setAttribute(ConfigConstant.CTX_STATIC, path);
		request.setAttribute(ConfigConstant.BASE_PATH, basePath);
        return super.preHandle(request,response,handler);
	}

}
