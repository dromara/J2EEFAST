package com.fast.framework.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import com.fast.framework.utils.*;
import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

//import com.fast.framework.shiro.ShiroTag;

/**
 * Freemarker配置
 */
@Configuration
public class FreemarkerConfig {
	
	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer(SysConfig sysconfig,
													 DictConfig dict, PermissionConfig permission) throws IOException, TemplateException {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("classpath:/templates");
		
		Map<String, Object> variables = new HashMap<>(1);
//		variables.put("shiro", shiroTag);
		variables.put("config", sysconfig);
		variables.put("dict",dict);
		variables.put("permission",permission);
		configurer.setFreemarkerVariables(variables);
		Properties settings = new Properties();
		settings.setProperty("default_encoding", "utf-8");
		settings.setProperty("number_format", "0.##");
		
		//扫描整个项目包括子项目
		configurer.setPreferFileSystemAccess(false);
		
		configurer.setFreemarkerSettings(settings);
		freemarker.template.Configuration configuration = configurer.createConfiguration();
		//shiro权限控制 前端使用宏获取判断
		configuration.setSharedVariable("shiro", new ShiroTags());
		configurer.setConfiguration(configuration);
		return configurer;
	}
	

}
