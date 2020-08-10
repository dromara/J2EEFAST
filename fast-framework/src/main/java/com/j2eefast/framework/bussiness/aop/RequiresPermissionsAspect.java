package com.j2eefast.framework.bussiness.aop;

import com.j2eefast.framework.utils.Constant;
import com.j2eefast.framework.utils.UserUtils;
import lombok.extern.java.Log;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(4)
@Aspect
@Component
@Log
public class RequiresPermissionsAspect {
    @Pointcut("@annotation(org.apache.shiro.authz.annotation.RequiresPermissions)")
    public void dataFilterCut() {

    }

    @SuppressWarnings("unchecked")
    @Before("dataFilterCut()")
    public void dataFilter(JoinPoint point) throws Throwable {

        MethodSignature signature = (MethodSignature) point.getSignature();
        RequiresPermissions dataFilter = signature.getMethod().getAnnotation(RequiresPermissions.class);
        UserUtils.setSessionAttribute(Constant.REQUIRES_PERMISSIONS,dataFilter.value());
    }
}
