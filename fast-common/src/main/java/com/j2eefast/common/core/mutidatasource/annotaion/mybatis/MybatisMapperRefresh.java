package com.j2eefast.common.core.mutidatasource.annotaion.mybatis;

import cn.hutool.core.date.SystemClock;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.j2eefast.common.db.context.DataSourceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.ResourceUtils;
/**
 * <p>刷新mapper.xml配置文件</p>
 *
 * @author: zhouzhou
 * @date: 2020-05-28 14:48
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Slf4j
public class MybatisMapperRefresh {

	/**
	 * 记录jar包存在的mapper
	 */
	private Map<String, List<Resource>> jarMapper = new HashMap<>();
	private SqlSessionFactory sqlSessionFactory;
	private Resource[] mapperLocations;
	private Long beforeTime = 0L;
	private Configuration configuration;
	private String dbName;
	/**
	 * xml文件目录
	 */
	private Set<String> fileSet;

	public MybatisMapperRefresh(String dbName,SqlSessionFactory sqlSessionFactory){
		this.mapperLocations = DataSourceContext.getMapperLocations().get(dbName).clone();
		this.sqlSessionFactory = sqlSessionFactory;
		this.configuration = sqlSessionFactory.getConfiguration();
		this.beforeTime = DataSourceContext.getBeforeTime().get(dbName);
		this.dbName = dbName;
	}

	public void loadRefresh(){
		fileSet = new HashSet<>();
		for (Resource mapperLocation : mapperLocations) {
			try {
				if (ResourceUtils.isJarURL(mapperLocation.getURL())) {
					String key = new UrlResource(ResourceUtils.extractJarFileURL(mapperLocation.getURL()))
							.getFile().getPath();
					fileSet.add(key);
					if (jarMapper.get(key) != null) {
						jarMapper.get(key).add(mapperLocation);
					} else {
						List<Resource> resourcesList = new ArrayList<>();
						resourcesList.add(mapperLocation);
						jarMapper.put(key, resourcesList);
					}
				} else {
					fileSet.add(mapperLocation.getFile().getPath());
				}
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}

		try {
			for (String filePath : fileSet) {
				File file = new File(filePath);
				if (file.isFile() && file.lastModified() > beforeTime) {
					List<Resource> removeList = jarMapper.get(filePath);
					if (removeList != null && !removeList.isEmpty()) {
						for (Resource resource : removeList) {
							this.refresh(resource);
						}
					} else {
						this.refresh(new FileSystemResource(file));
					}
				}
			}
			DataSourceContext.getBeforeTime().remove(dbName);
			DataSourceContext.addBeforeTime(dbName, SystemClock.now());
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * 刷新mapper
	 *
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private void refresh(Resource resource) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
		this.configuration = sqlSessionFactory.getConfiguration();
		boolean isSupper = configuration.getClass().getSuperclass() == Configuration.class;
		try {
			Field loadedResourcesField = isSupper ? configuration.getClass().getSuperclass().getDeclaredField("loadedResources")
					: configuration.getClass().getDeclaredField("loadedResources");
			loadedResourcesField.setAccessible(true);
			Set loadedResourcesSet = ((Set) loadedResourcesField.get(configuration));
			XPathParser xPathParser = new XPathParser(resource.getInputStream(), true, configuration.getVariables(),
					new XMLMapperEntityResolver());
			XNode context = xPathParser.evalNode("/mapper");
			String namespace = context.getStringAttribute("namespace");
			Field field = MapperRegistry.class.getDeclaredField("knownMappers");
			field.setAccessible(true);
			Map mapConfig = (Map) field.get(configuration.getMapperRegistry());
			mapConfig.remove(Resources.classForName(namespace));
			loadedResourcesSet.remove(resource.toString());
			configuration.getCacheNames().remove(namespace);
			cleanParameterMap(context.evalNodes("/mapper/parameterMap"), namespace);
			cleanResultMap(context.evalNodes("/mapper/resultMap"), namespace);
			cleanKeyGenerators(context.evalNodes("insert|update"), namespace);
			clearMapperdMap(context.evalNodes("select|insert|update|delete"), namespace);
			cleanSqlElement(context.evalNodes("/mapper/sql"), namespace);
			XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(resource.getInputStream(),
					sqlSessionFactory.getConfiguration(),
					resource.toString(), sqlSessionFactory.getConfiguration().getSqlFragments());
			xmlMapperBuilder.parse();
			log.info("刷新: {}  success!",resource);
		} catch (IOException e) {
			log.error("Refresh IOException :" + e.getMessage());
		} finally {
			ErrorContext.instance().reset();
		}
	}

	/**
	 * 清理parameterMap
	 *
	 * @param list
	 * @param namespace
	 */
	private void cleanParameterMap(List<XNode> list, String namespace) {
		for (XNode parameterMapNode : list) {
			String id = parameterMapNode.getStringAttribute("id");
			configuration.getParameterMaps().remove(namespace + "." + id);
		}
	}

	private void clearMapperdMap(List<XNode> list, String namespace){
		for (XNode parameterMapNode : list) {
			String id = parameterMapNode.getStringAttribute("id");
			configuration.getMappedStatementNames().remove(namespace + "." + id);
		}
	}

	/**
	 * 清理resultMap
	 *
	 * @param list
	 * @param namespace
	 */
	private void cleanResultMap(List<XNode> list, String namespace) {
		for (XNode resultMapNode : list) {
			String id = resultMapNode.getStringAttribute("id", resultMapNode.getValueBasedIdentifier());
			configuration.getResultMapNames().remove(id);
			configuration.getResultMapNames().remove(namespace + "." + id);
			clearResultMap(resultMapNode, namespace);
		}
	}

	private void clearResultMap(XNode xNode, String namespace) {
		for (XNode resultChild : xNode.getChildren()) {
			if ("association".equals(resultChild.getName()) || "collection".equals(resultChild.getName())
					|| "case".equals(resultChild.getName())) {
				if (resultChild.getStringAttribute("select") == null) {
					configuration.getResultMapNames().remove(
							resultChild.getStringAttribute("id", resultChild.getValueBasedIdentifier()));
					configuration.getResultMapNames().remove(
							namespace + "." + resultChild.getStringAttribute("id", resultChild.getValueBasedIdentifier()));
					if (resultChild.getChildren() != null && !resultChild.getChildren().isEmpty()) {
						clearResultMap(resultChild, namespace);
					}
				}
			}
		}
	}

	/**
	 * 清理selectKey
	 *
	 * @param list
	 * @param namespace
	 */
	private void cleanKeyGenerators(List<XNode> list, String namespace) {
		for (XNode context : list) {
			String id = context.getStringAttribute("id");
			configuration.getKeyGeneratorNames().remove(id + SelectKeyGenerator.SELECT_KEY_SUFFIX);
			configuration.getKeyGeneratorNames().remove(namespace + "." + id + SelectKeyGenerator.SELECT_KEY_SUFFIX);
		}
	}

	/**
	 * 清理sql节点缓存
	 *
	 * @param list
	 * @param namespace
	 */
	private void cleanSqlElement(List<XNode> list, String namespace) {
		for (XNode context : list) {
			String id = context.getStringAttribute("id");
			configuration.getSqlFragments().remove(id);
			configuration.getSqlFragments().remove(namespace + "." + id);
		}
	}
}
