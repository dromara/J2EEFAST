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

	GenTableEntity findByName(@Param("tableName") String tableName);

	GenTableEntity findGenTableMenuById(@Param("id")  Long id);	
	
	GenTableEntity findByTableId(Long id);

	int updateGenTable(GenTableEntity genTable);


	int deleteGenTableByIds(Long[] ids);

	/** 
	* @Title: findDbTableList 
	* @Description: 获取数库的表名列表
	* @param dbType 数据类型： mysql,oracle...
	* @param dbTableName  表名用名收索
	* @param schema mysql:database Name  , oracle: 用户名(大写)
	* @return  List<GenTableEntity> 
	* @author mfksn001@163.com
	* @Date: 2020年5月30日
	*/
	List<GenTableEntity> generateTableList(@Param("dbType") String dbType ,
			                               @Param("schema") String schema ,
			                               @Param("tableName")  String tableName,  
			                               @Param("tableComment") String tableComment,
										   @Param("notList") List<String> notList);
	
	/**
	* @Title: generateTableListByNames 
	* @Description: 根据表名List 列出表名List
	* @param dbType
	* @param schema
	* @param tableNames
	* @return  List<GenTableEntity> 
	* @author mfksn001@163.com
	* @Date: 2020年6月1日
	 */
	List<GenTableEntity> generateTableListByNames(@Param("dbType") String dbType ,
                                                  @Param("schema") String schema,
                                                  @Param("tableNames") String[] tableNames);
	
	
	/** 
	* @Title: generateTablePage 
	* @Description: 获取数库的表名列表page
	* @param dbType 数据类型： mysql,oracle...
	* @param dbTableName  表名用名收索
	* @param schema mysql:database Name  , oracle: 用户名(大写)
	* @return  List<GenTableEntity> 
	* @author mfksn001@163.com
	* @Date: 2020年5月30日
	*/
	List<GenTableEntity> generateTablePage(IPage<?> params,
											@Param("dbType") String dbType ,
											@Param("schema") String schema,
								            @Param("tableName")  String tableName,  
								            @Param("tableComment") String tableComment,
										    @Param("notList") List<String> notList);
	
	

	

}
