package com.j2eefast.common.core.utils;

import cn.hutool.core.util.URLUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>cookie操作</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-02 21:55
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public class CookieUtil {

	/**
	 * 设置 Cookie（生成时间为1天）
	 * @param name 名称
	 * @param value 值
	 */
	public static void setCookie(HttpServletResponse response, String name, String value) {
		setCookie(response, name, value, 60*60*24);
	}

	/**
	 * 设置 Cookie
	 * @param name 名称
	 * @param value 值
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String path) {
		setCookie(response, name, value, path, 60*60*24);
	}

	/**
	 * 设置 Cookie
	 * @param name 名称
	 * @param value 值
	 * @param maxAge 生存时间（单位秒）
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
		setCookie(response, name, value, "/", maxAge);
	}

	public static void setReadCookie(HttpServletResponse response, String name, String value, int maxAge) {
		setReadCookie(response, name, value, "/", maxAge);
	}

	/**
	 * 设置 前端可读Cookie
	 * @param name 名称
	 * @param value 值
	 * @param maxAge 生存时间（单位秒）
	 */
	public static void setReadCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
		if (ToolUtil.isNotEmpty(name)){
			name = URLUtil.encodeAll(name);
			value = URLUtil.encodeAll(value);
			Cookie cookie = new Cookie(name, null);
			cookie.setPath(path);
			cookie.setMaxAge(maxAge);
			cookie.setValue(value);
			response.addCookie(cookie);
		}
	}

	public static void setCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
		if (ToolUtil.isNotEmpty(name)){
			name = URLUtil.encodeAll(name);
			value = URLUtil.encodeAll(value);
			Cookie cookie = new Cookie(name, null);
			cookie.setPath(path);
			cookie.setMaxAge(maxAge);
			cookie.setHttpOnly(true);
			cookie.setValue(value);
			response.addCookie(cookie);
		}
	}

	/**
	 * 获得指定Cookie的值
	 * @param name 名称
	 * @return 值
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		return getCookie(request, null, name, false);
	}

	/**
	 * 获得指定Cookie的值，并删除。
	 * @param name 名称
	 * @return 值
	 */
	public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		return getCookie(request, response, name, true);
	}

	/**
	 * 获得指定Cookie的值
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param name 名字
	 * @param isRemove 是否移除
	 * @return 值
	 */
	public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, boolean isRemove) {
		return getCookie(request, response, name, "/", isRemove);
	}

	/**
	 * 获得指定Cookie的值
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param name 名字
	 * @param isRemove 是否移除
	 * @return 值
	 */
	public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, String path, boolean isRemove) {
		String value = null;
		if (ToolUtil.isNotEmpty(name)){
			name = URLUtil.encodeAll(name);
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(name)) {
						value = URLUtil.decode(cookie.getValue());
						if (isRemove && response != null) {
							cookie.setPath(path);
							cookie.setMaxAge(0);
							response.addCookie(cookie);
						}
					}
				}
			}
		}
		return value;
	}
}
