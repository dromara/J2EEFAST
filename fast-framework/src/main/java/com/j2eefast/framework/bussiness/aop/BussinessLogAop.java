package com.j2eefast.framework.bussiness.aop;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import com.j2eefast.common.core.base.entity.LoginUserEntity;
import com.j2eefast.common.core.utils.JSON;
import com.j2eefast.common.core.utils.ServletUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.log.entity.SysOperLogEntity;
import com.j2eefast.framework.manager.factory.AsyncFactory;
import com.j2eefast.framework.sys.entity.SysUserEntity;
import com.j2eefast.framework.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.enums.BusinessStatus;
import com.j2eefast.common.core.manager.AsyncManager;

/**
 * 系统日志，切面处理类AOP实现日志统一处理
 * @author zhouzhou
 * @date 2020-03-12 14:53
 */
@Aspect
@Component
public class BussinessLogAop {

	private static final Logger					LOG 					= LoggerFactory.getLogger(BussinessLogAop.class);

	/**
	 * 配置织入点
	 * @author zhouzhou
	 * @date 2020-03-12 14:55
	 */
	@Pointcut("@annotation(com.j2eefast.common.core.business.annotaion.BussinessLog)")
	public void logCut() {
		
	}


	/**
	 * 处理完请求后执行
	 *
	 * @param joinPoint 切点
	 */
	@AfterReturning(pointcut = "logCut()", returning = "jsonResult")
	public void doAfterReturning(JoinPoint joinPoint, Object jsonResult)
	{
		handleLog(joinPoint, null, jsonResult);
	}


	protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult)
	{
		try{
			long beginTime = System.currentTimeMillis();
			// 获得注解
			BussinessLog controllerLog = getAnnotationLog(joinPoint);
			if (controllerLog == null){
				return;
			}

			// 获取当前的用户
			LoginUserEntity currentUser = UserUtils.getUserInfo();

			// *========数据库日志=========*//
			SysOperLogEntity operLog = new SysOperLogEntity();
			operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
			// 请求的地址
			String ip = UserUtils.getIp();

			operLog.setOperIp(ip);
			// 返回参数
			operLog.setJsonResult(JSON.marshal(jsonResult));

			operLog.setOperUrl(ServletUtil.getRequest().getRequestURI());
			if (currentUser != null){
				//当操作用户存在 保存用户相关信息
				operLog.setOperName(currentUser.getName());
				if (ToolUtil.isNotEmpty(currentUser.getCompName())){
					operLog.setCompName(currentUser.getCompName());
				}

				operLog.setCompId(currentUser.getCompId());
				operLog.setDeptId(currentUser.getDeptId());
			}

			if (ToolUtil.isNotEmpty(e)){
				operLog.setStatus(BusinessStatus.FAIL.ordinal());
				operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
			}
			// 设置方法名称
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			operLog.setMethod(className + "." + methodName + "()");
			// 设置请求方式
			operLog.setRequestMethod(ServletUtil.getRequest().getMethod());
			// 处理设置注解上的参数
			getControllerMethodDescription(controllerLog, operLog);

			operLog.setOperTime(new Date());

			//执行时长(毫秒)
			long time = System.currentTimeMillis() - beginTime;

			operLog.setTime(time);

			// 保存数据库
			AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
		}
		catch (Exception ex){
			// 记录本地异常日志
			LOG.error("==前置通知异常==");
			LOG.error("异常信息:{}", ex.getMessage());
		}
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 */
	public void getControllerMethodDescription(BussinessLog log, SysOperLogEntity operLog) throws Exception
	{
		// 设置action动作
		operLog.setBusinessType(log.businessType().ordinal());
		// 设置标题
		operLog.setTitle(log.title());
		// 设置操作人类别
		operLog.setOperatorType(log.operatorType().ordinal());
		// 是否需要保存request，参数和值
		if (log.isSaveRequestData()){
			// 获取参数的信息，传入到数据库中。
			setRequestValue(operLog);
		}
	}


	/**
	 * 获取请求的参数，放到log中
	 */
	private void setRequestValue(SysOperLogEntity operLog) throws Exception{
		Map<String, String[]> map = ServletUtil.getRequest().getParameterMap();
		String params = JSON.marshal(map);
		operLog.setOperParam(StringUtils.substring(params, 0, 2000));
	}


	/**
	 * 是否存在注解，如果存在就获取
	 */
	private BussinessLog getAnnotationLog(JoinPoint joinPoint) throws Exception
	{
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (method != null)
		{
			return method.getAnnotation(BussinessLog.class);
		}
		return null;
	}
	

	
}
