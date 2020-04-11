package com.j2eefast.framework.quartz.utils;

import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;
import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.core.utils.SpringUtil;
import com.j2eefast.common.core.utils.ToolUtil;

/**
 * 执行定时任务
 */
public class ScheduleRunnable implements Runnable {
	private Object target;
	private Method method;
	private String params;

	public ScheduleRunnable(String beanName, String methodName, String params)
			throws NoSuchMethodException, SecurityException {
		this.target = SpringUtil.getBean(beanName);
		this.params = params;

		if (ToolUtil.isNotEmpty(params)) {
			this.method = target.getClass().getDeclaredMethod(methodName, String.class);
		} else {
			this.method = target.getClass().getDeclaredMethod(methodName);
		}
	}

	@Override
	public void run() {
		try {
			ReflectionUtils.makeAccessible(method);
			if (ToolUtil.isNotEmpty(params)) {
				method.invoke(target, params);
			} else {
				method.invoke(target);
			}
		} catch (Exception e) {
			throw new RxcException("执行定时任务失败", e);
		}
	}

}
