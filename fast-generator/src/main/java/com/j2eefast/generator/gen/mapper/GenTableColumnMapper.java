package com.j2eefast.generator.gen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j2eefast.generator.gen.entity.GenTableColumnEntity;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * <p> 代码生成 Mapper 接口</p>
 *
 * @author: zhouzhou
 * @date: 2019-04-07 13:21
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public interface GenTableColumnMapper extends BaseMapper<GenTableColumnEntity> {

	int updateGenTableColumn(GenTableColumnEntity genTableColumn);


	/** 
	* @Title: findListByTableId 
	* @param tableId
	* @return  List<GenTableColumnEntity> 
	* @author mfksn001@163.com
	* @Date: 2020年6月2日
	*/
	List<GenTableColumnEntity> findListByTableId(Long tableId);
	
	/**
	* @Title: generateDbTableColumnsByName 
	* @Description: 产生表栏位名
	* @param dbType 数据库类型
	* @param schema schema
	* @param tableName 表名
	* @return  List<GenTableColumnEntity> 
	* @author mfksn001@163.com
	* @Date: 2020年6月1日
	 */
	List<GenTableColumnEntity> generateDbTableColumnsByName(@Param("dbType") String dbType ,
                                                            @Param("schema") String schema ,
                                                            @Param("tableName") String tableName);


	int deleteGenTableColumnByIds(Long[] ids);

}
