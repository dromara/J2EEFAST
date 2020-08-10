package com.j2eefast.common.core.handler;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.j2eefast.common.core.base.entity.BaseEntity;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p></p>
 *
 * @author: zhouzhou
 * @date: 2020-05-21 13:50
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Slf4j
public class ResultWarpReturnValueHandler  implements HandlerMethodReturnValueHandler {

	private final HandlerMethodReturnValueHandler delegate;

	/** 委托 */
	public ResultWarpReturnValueHandler(HandlerMethodReturnValueHandler delegate) {
		this.delegate = delegate;
	}

	/**
	 * 判断返回类型是否需要转成字符串返回
	 * @param returnType 方法返回类型
	 * @return 需要转换返回true，否则返回false
	 */
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return delegate.supportsReturnType(returnType);
	}

	/**
	 * 返回值转换
	 */
	@Override
	public void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
		// 委托SpringMVC默认的RequestResponseBodyMethodProcessor进行序列化
		Method method = returnType.getMethod();
		BussinessLog annotation = method.getAnnotation(BussinessLog.class);
		if(annotation != null){
		}
//		for(Annotation s: a){
//			log.info(s.toString());
//		}
//		if(returnValue instanceof ResponseData){
//			ResponseData r = (ResponseData) returnValue;
//			//
//			AtomicBoolean flag = new AtomicBoolean(false);
//			if(r.get("data") instanceof PageUtil){
//				PageUtil p = (PageUtil) r.get("data");
//				p.getList().forEach(o->{
//					if(o instanceof BaseEntity){
//						flag.set(true);
//					}
//				});
//			}
//			if(flag.get()){
//				JSONObject json = JSONUtil.parseObj(r,false);
//				JSONObject data = (JSONObject) json.get("data");
//				JSONArray jarr = data.getJSONArray("list");
//				for(int i=0; i<jarr.size(); i++){
//					jarr.getJSONObject(i).remove("sql_filter");
//				}
//				log.info(json.toString());
//				//returnValue = JSONUtil.quote(json.toString());
//			}
//		}

		delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
	}
}
