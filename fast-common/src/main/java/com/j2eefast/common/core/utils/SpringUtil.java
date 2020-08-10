package com.j2eefast.common.core.utils;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author zhouzhou
 * @date 2020-03-12 14:34
 */
@Component
public class SpringUtil implements BeanFactoryPostProcessor{

	private static ConfigurableListableBeanFactory BEAN_FACTORY;
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		SpringUtil.BEAN_FACTORY = beanFactory;
	}

	/**
	 * 获取对象
	 * @author zhouzhou
	 * @date 2020-03-12 14:39
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException{
	    return (T) BEAN_FACTORY.getBean(name);
	}
	
	/**
	 * 获取类型为requiredType的对象
	 * @author zhouzhou
	 * @date 2020-03-12 14:39
	 */
	public static <T> T getBean(Class<T> clz) throws BeansException{
	    T result = (T) BEAN_FACTORY.getBean(clz);
	    return result;
	}

	/**
	 * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
	 * @author zhouzhou
	 * @date 2020-03-12 14:39
	 */
	public static boolean containsBean(String name){
	    return BEAN_FACTORY.containsBean(name);
	}
	
	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 
	 * </P>
	 * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	 * @author zhouzhou
	 * @date 2020-03-12 14:39
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException{
	    return BEAN_FACTORY.isSingleton(name);
	}
	

	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
	    return BEAN_FACTORY.getType(name);
	}
	
	/**
	 * 如果给定的bean名字在bean定义中有别名，则返回这些别名
	 * @author zhouzhou
	 * @date 2020-03-12 14:39
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
	    return BEAN_FACTORY.getAliases(name);
	}

	/**
	 * 通过SPring AOP 代理获取
	 * @param invoker
	 * @param <T>
	 * @return
	 */
	public static <T> T getAopProxy(T invoker){
		return (T) AopContext.currentProxy();
	}
}
