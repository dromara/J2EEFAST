package com.j2eefast.generator.gen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.j2eefast.generator.gen.entity.GenTableEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> 代码生成 Mapper 接口</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-07 13:21
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public interface GenTableMapper extends BaseMapper<GenTableEntity> {

	GenTableEntity findGenTableByName(@Param("tableName") String tableName);

	GenTableEntity findGenTableById(@Param("tableId")  Long tableId);

	GenTableEntity findGenTableMenuById(@Param("tableId")  Long tableId);

	int updateGenTable(GenTableEntity genTable);

	/**
	 * 主据库列表
	 *
	 * @return 数据库表集合
	 */
	List<GenTableEntity> findDbTableList();

	/**
	 * 从数据库
	 * @return
	 */
	List<GenTableEntity> findSlaveDbTableList();

	List<GenTableEntity> findDbNotTableList(@Param("dbType") String dbType);

	List<GenTableEntity>  selectDbTablePage(IPage<?> params,
											@Param("tableName") String tableName,
											@Param("tableComment") String tableComment);

	List<GenTableEntity>  selectNotDbTablePage(IPage<?> params,
											 @Param("tableName") String tableName,
											 @Param("tableComment") String tableComment,
											 @Param("notList") List<String> notList);
	/**
	 * 查询据库列表
	 *
	 * @param tableNames 表名称组
	 * @return 数据库表集合
	 */
	List<GenTableEntity> selectDbTableListByNames(String[] tableNames);

	int deleteGenTableByIds(Long[] ids);

}
