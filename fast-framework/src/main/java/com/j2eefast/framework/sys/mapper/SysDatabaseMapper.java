package com.j2eefast.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.j2eefast.common.db.entity.SysDatabaseEntity;
import org.apache.ibatis.annotations.Param;

/**
 * <p>多源数据Mapper</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-15 18:11
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public interface SysDatabaseMapper extends BaseMapper<SysDatabaseEntity> {

	/**
	 * 页面分页查询
	 */
	Page<SysDatabaseEntity> findPage(IPage<?> params,
								 @Param("dbName") String dbName);

	/** 
	* @Title: getByName 
	* @Description: 根据用户名获取数据源
	* @param dbName
	* @return  SysDatabaseEntity 
	* @author mfksn001@163.com
	* @Date: 2020年6月1日
	*/
	SysDatabaseEntity getByName(@Param("dbName") String dbName);

}
