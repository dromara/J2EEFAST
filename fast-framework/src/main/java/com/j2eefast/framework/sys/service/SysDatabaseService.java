package com.j2eefast.framework.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.common.db.context.DataSourceContext;
import com.j2eefast.common.db.context.SqlSessionFactoryContext;
import com.j2eefast.common.db.entity.SysDatabaseEntity;
import com.j2eefast.framework.sys.mapper.SysDatabaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>多源数据</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-15 18:11
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Slf4j
@Service
public class SysDatabaseService extends ServiceImpl<SysDatabaseMapper, SysDatabaseEntity> {

	@Resource
	private SysDatabaseMapper sysDatabaseMapper;

	/**
	 * 页面展示查询翻页
	 */
	public PageUtil findPage(Map<String, Object> params) {
		String dbName = (String) params.get("dbName");
		Page<SysDatabaseEntity> page = this.sysDatabaseMapper.
				findPage(new Query<SysDatabaseEntity>(params).getPage(),dbName);
		return new PageUtil(page);
	}


	/**
	 * 新增
	 * @param database
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean add(SysDatabaseEntity database) {
		//判断数据库连接是否可用
		Connection conn = null;
		try {
			Class.forName(database.getJdbcDriver());
			conn = DriverManager.getConnection(
					database.getJdbcUrl(), database.getUserName(), database.getPassword());
		} catch (Exception e) {
			log.error("-->",e);
			throw new RxcException("连接数据库失败!请检查参数是否配置有误!");
		}finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					log.error("数据库关闭失败",e);
				}
			}
		}

		if(this.save(database)){
			//动态加载系统

			//先判断context中是否有了这个数据源名称
			SqlSessionFactory sqlSessionFactory = SqlSessionFactoryContext.getSqlSessionFactorys().get(database.getDbName());
			if (sqlSessionFactory != null) {
				throw new RxcException("当前上下文中已存在该名称，请重启项目或更换名称");
			}

			//往上下文中添加数据源
			SqlSessionFactoryContext.addSqlSessionFactory(database.getDbName(), database);

			return true;
		}
		return false;
	}


	/**
	 * 检查db名称是否有一样的
	 * @param database
	 * @return
	 */
	public boolean checkDataNameUnique(SysDatabaseEntity database) {
		Long dbid = ToolUtil.isEmpty(database.getId()) ? -1L : database.getId();
		SysDatabaseEntity dababaseInfo = this.getOne(new QueryWrapper<SysDatabaseEntity>()
				.eq("db_name",database.getDbName()));
		if (!ToolUtil.isEmpty(dababaseInfo) && dababaseInfo.getId().longValue() != dbid.longValue()) {
			return  false;
		}
		return true;
	}

	public boolean deleteBatchByIds(Long[] ids){
		List<SysDatabaseEntity> list = this.listByIds(Arrays.asList(ids));
		boolean flag = false;
		for(SysDatabaseEntity db: list){
			if(db.getDbName().equalsIgnoreCase(DataSourceContext.MASTER_DATASOURCE_NAME)){
				new RxcException("主数据库不能删除!");
			}
			DataSourceContext.removeByName(db.getDbName());
			this.removeById(db.getId());
			flag = true;
		}
		return flag;
	}

}
