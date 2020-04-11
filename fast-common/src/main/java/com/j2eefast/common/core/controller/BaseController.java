package com.j2eefast.common.core.controller;

import com.j2eefast.common.core.utils.CookieUtil;
import com.j2eefast.common.core.utils.HttpContextUtil;
import com.j2eefast.common.core.utils.ResponseData;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;
/**
 * <p>控制器基础类</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-01 19:49
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
public class BaseController {
    protected final String                  REDIRECT                        = "redirect:";
    protected final String                  FORWARD                         = "forward:";


    protected HttpServletRequest getHttpServletRequest() {
        return HttpContextUtil.getRequest();
    }

    protected HttpServletResponse getHttpServletResponse() {
        return HttpContextUtil.getResponse();
    }

    protected HttpSession getSession() {
        return Objects.requireNonNull(HttpContextUtil.getRequest()).getSession();
    }

    protected HttpSession getSession(Boolean flag) {
        return Objects.requireNonNull(HttpContextUtil.getRequest()).getSession(flag);
    }

    protected String getPara(String name) {
        return Objects.requireNonNull(HttpContextUtil.getRequest()).getParameter(name);
    }

    protected void setAttr(String name, Object value) {
        Objects.requireNonNull(HttpContextUtil.getRequest()).setAttribute(name, value);
    }

    /**
     * 删除cookie
     */
    protected void deleteCookieByName(String cookieName) {
        Cookie[] cookies = this.getHttpServletRequest().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                Cookie temp = new Cookie(cookie.getName(), "");
                temp.setMaxAge(0);
                this.getHttpServletResponse().addCookie(temp);
            }
        }
    }

    /**
     * 删除所有cookie
     */
    protected void deleteAllCookie() {
        Cookie[] cookies = this.getHttpServletRequest().getCookies();
        for (Cookie cookie : cookies) {
            Cookie temp = new Cookie(cookie.getName(), "");
            temp.setMaxAge(0);
            this.getHttpServletResponse().addCookie(temp);
        }
    }

    /**
     * 获取并且删除
     * @param name
     * @return
     */
    protected String getCookieToDel(String name){
		return CookieUtil.getCookie(this.getHttpServletRequest(),
                this.getHttpServletResponse(),name);
    }

    /**
     * 返回页面错误
     * @param msg
     * @return
     */
    protected ResponseData error(String msg){
        return ResponseData.error(msg);
    }

    protected ResponseData error(String code, String msg){
        return ResponseData.error(code,msg);
    }

    /**
     * 返回成功消息
     */
    protected ResponseData success(String msg){
        return ResponseData.success(msg);
    }

    protected ResponseData success(){
        return ResponseData.success();
    }

    protected ResponseData success(Object data){
        return ResponseData.success(data);
    }

    protected ResponseData success(String msg,Object data){
        return ResponseData.success(msg,data);
    }

}
