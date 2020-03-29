package com.fast.common.core.utils;

import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>异常工具类.</p>
 *
 * @author: zhouzhou Emall:18774995071@163.com
 * @date: 2019-03-26 17:59
 * @web: https://www.j2eefast.com
 * @version: 1.0.1
 */
public class ExceptionUtil {
    /**
     * 在request中获取异常类
     * @param request
     * @return
     */
    public static Throwable getThrowable(HttpServletRequest request){
        Throwable ex = null;
        if (request.getAttribute("exception") != null) {
            ex = (Throwable) request.getAttribute("exception");
        } else if (request.getAttribute("javax.servlet.error.exception") != null) {
            ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
        }
        return ex;
    }

    public static String getExceptionMessage(Throwable ex){
        String message = null;
        Throwable e = ex;
        while (true){
            if (e == null){
                break;
            }
            if (StringUtils.startsWith(e.getMessage(), "msg:")){
                message = StringUtils.replace(e.getMessage(), "msg:", "");
                break;
            }else if ("com.jeesite.common.service.ServiceException"
                    .equals(e.getClass().getName())){
                message = e.getMessage();
                break;
            }
            if (StringUtils.isBlank(message)){
                e = e.getCause();
            }
        }
        return message;
    }

    /**
     * 将ErrorStack转化为String.
     */
    public static String getStackTraceAsString(Throwable e) {
        if (e == null){
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * 判断异常是否由某些底层的异常引起.
     */
    @SuppressWarnings("unchecked")
    public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
        Throwable cause = ex.getCause();
        while (cause != null) {
            for (Class<? extends Exception> causeClass : causeExceptionClasses) {
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }

    /**
     * 将CheckedException转换为UncheckedException.
     */
    public static RuntimeException unchecked(Exception e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else {
            return new RuntimeException(e);
        }
    }
}
