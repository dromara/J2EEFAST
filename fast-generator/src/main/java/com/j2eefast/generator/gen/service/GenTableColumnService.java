package com.j2eefast.generator.gen.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.generator.gen.entity.GenTableColumnEntity;
import com.j2eefast.generator.gen.entity.GenTableEntity;
import com.j2eefast.generator.gen.mapper.GenTableColumnMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    public List<GenTableColumnEntity> selectGenTableColumnListByTableId(Map<String, Object> params) {
        String tableId = (String) params.get("tableId");
        List<GenTableColumnEntity> list = this.list(new QueryWrapper<GenTableColumnEntity>()
                .eq(StrUtil.isNotBlank(tableId), "table_id", tableId).orderByAsc("sort").select("column_id, table_id, column_name, column_comment, column_type," +
                        " java_type, java_field, is_pk, is_increment, is_required, is_insert, is_edit, is_list, " +
                        "is_query, query_type, html_type, dict_type,is_table_sort, sort,circle_type, edit_info, create_by, create_time, update_by, update_time"));
        return list;
    }

    public int updateGenTableColumn(GenTableColumnEntity genTable) {
        return genTableColumnMapper.updateGenTableColumn(genTable);
    }

    public List<GenTableColumnEntity> selectDbTableColumnsByName(String dbType,String tableName) {
        return genTableColumnMapper.selectDbTableColumnsByName(tableName);
    }

//    public List<GenTableColumnEntity> selectDbTableColumnsByName1(String tableName) {
//        return genTableColumnMapper.selectDbTableColumnsByName(tableName);
//    }

    public int deleteGenTableColumnByIds(Long[] ids) {
        return genTableColumnMapper.deleteGenTableColumnByIds(ids);
    }

}
