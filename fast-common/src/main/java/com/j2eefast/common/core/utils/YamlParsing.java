package com.j2eefast.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.core.CollectionFactory;
import org.springframework.lang.Nullable;

import java.util.*;

/**
 * <p>Yaml解析器</p>
 *
 * @author: zhouzhou 重写
 * @date: 2020-04-29 10:01
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Slf4j
public class YamlParsing extends YamlProcessors implements FactoryBean<Properties>, InitializingBean {

	private boolean singleton = true;

	@Nullable
	private Properties properties;

	/**
	 * Set if a singleton should be created, or a new object on each request
	 * otherwise. Default is {@code true} (a singleton).
	 */
	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}

	@Override
	public boolean isSingleton() {
		return this.singleton;
	}

	@Override
	public void afterPropertiesSet() {
		if (isSingleton()) {
			this.properties = createProperties();
		}
	}

	@Override
	@Nullable
	public Properties getObject() {
		return (this.properties != null ? this.properties : createProperties());
	}

	@Override
	public Class<?> getObjectType() {
		return Properties.class;
	}


	/**
	 * Template method that subclasses may override to construct the object
	 * returned by this factory. The default implementation returns a
	 * properties with the content of all resources.
	 * <p>Invoked lazily the first time {@link #getObject()} is invoked in
	 * case of a shared singleton; else, on each {@link #getObject()} call.
	 * @return the object returned by this factory
	 * @see #process(YamlProcessor.MatchCallback)
	 */
	protected Properties createProperties() {
		Properties result = CollectionFactory.createStringAdaptingProperties();
		process((properties, map) -> result.putAll(properties));
		return result;
	}

}
