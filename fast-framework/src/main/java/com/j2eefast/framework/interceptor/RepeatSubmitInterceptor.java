package com.j2eefast.framework.interceptor;/**
 * @ClassName: RepeatSubmitInterceptor
 * @Package: com.j2eefast.framework.interceptor
 * @Description: 防止重复提交拦截器(用一句话描述该文件做什么)
 * @author: zhouzhou Emall:18774995071@163.com
 * @time 2020/1/6 12:41
 * @version V1.0
 
 */

import com.j2eefast.common.core.utils.JSON;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.core.utils.ServletUtil;
import com.j2eefast.framework.annotation.RepeatSubmit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ClassName: RepeatSubmitInterceptor
 * @Package: com.j2eefast.framework.interceptor
 * @Description: 防止重复提交拦截器(用一句话描述该文件做什么)
 * @author: zhouzhou Emall:18774995071@163.com
 * @time 2020/1/6 12:41
 * @version V1.0
 
 *
 */
@Component
public abstract class RepeatSubmitInterceptor extends HandlerInterceptorAdapter {

    protected final Logger logger = LoggerFactory.getLogger(RepeatSubmitInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (handler instanceof HandlerMethod)
        {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null)
            {
                if (this.isRepeatSubmit(request)) {
                	ServletUtil.renderString(response, JSON.marshal(ResponseData.error("50007", "不允许重复提交，请稍后再试")));
                    return false;
                }
            }
            return true;
        }
        else
        {
            return super.preHandle(request, response, handler);
        }
    }

    /**
     * 验证是否重复提交由子类实现具体的防重复提交的规则
     *
     * @return
     * @throws Exception
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request) throws Exception;

}