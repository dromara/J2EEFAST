package com.j2eefast.generator.gen.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.j2eefast.common.core.mutidatasource.DataSourceContextHolder;
import com.j2eefast.common.db.entity.SysDatabaseEntity;
import com.j2eefast.framework.sys.mapper.SysDatabaseMapper;
import com.j2eefast.generator.gen.entity.GenTableColumnEntity;
import com.j2eefast.generator.gen.mapper.GenTableColumnMapper;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @time 2020/1/6 15:23
 * @version V1.0
 */
@Service
public class GenTableColumnService extends ServiceImpl<GenTableColumnMapper,GenTableColumnEntity> {

    @Resource
    private GenTableColumnMapper genTableColumnMapper;
    
    @Resource
    private SysDatabaseMapper sysDatabaseMapper;

    public List<GenTableColumnEntity> selectGenTableColumnListByTableId(Map<String, Object> params) {
        Long tableId = Long.parseLong((String) params.get("id"));
        /*List<GenTableColumnEntity> list = this.list(new QueryWrapper<GenTableColumnEntity>()
                .eq(StrUtil.isNotBlank(tableId), "table_id", tableId).orderByAsc("sort").select("id, table_id, column_name, column_comment, column_type," +
                        " java_type, java_field, is_pk, is_increment, is_required, is_insert, is_edit, is_list, " +
                        "is_query, query_type, html_type, dict_type,is_table_sort, sort,circle_type, edit_info, create_by, create_time, update_by, update_time"));*/
        List<GenTableColumnEntity> list = Lists.newArrayList();
        list = findListByTableId(tableId);
        
        return list;
    }

    public int updateGenTableColumn(GenTableColumnEntity genTable) {
        return genTableColumnMapper.updateGenTableColumn(genTable);
    }

    /** 
	* @Title: findListByTableId 
	* @Description: 获取columns
	* @param id
	* @return  List<GenTableColumnEntity> 
	* @author mfksn001@163.com
	* @Date: 2020年6月2日
	*/
	public List<GenTableColumnEntity> findListByTableId(Long tableId) {
		
		return genTableColumnMapper.findListByTableId(tableId);
	}
    
    
    public List<GenTableColumnEntity> generateDbTableColumnsByName(String dbType,String schema ,String tableName) {
        return genTableColumnMapper.generateDbTableColumnsByName( dbType , schema , tableName);
    }
    
    
    public List<GenTableColumnEntity> generateDbTableColumnsByName(String dbName,String tableName) {
    	  List<GenTableColumnEntity> list = Lists.newArrayList();
		try {
			SysDatabaseEntity db=    sysDatabaseMapper.getByName(dbName);
			 DataSourceContextHolder.setDataSourceType(dbName);
			  list = genTableColumnMapper.generateDbTableColumnsByName(db.getDbType(), db.getSchema() , tableName);
		} catch (Exception e) {
		   e.printStackTrace();
		}finally {
			 DataSourceContextHolder.clearDataSourceType();
		}
        return list ;
    }
    
    public int deleteGenTableColumnByIds(Long[] ids) {
        return genTableColumnMapper.deleteGenTableColumnByIds(ids);
    }

	

}
