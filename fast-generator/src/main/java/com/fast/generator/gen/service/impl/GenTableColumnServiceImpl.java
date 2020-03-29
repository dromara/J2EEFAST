package com.fast.generator.gen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.generator.gen.dao.GenTableColumnDao;
import com.fast.generator.gen.entity.GenTableColumnEntity;
import com.fast.generator.gen.service.GenTableColumnService;

import cn.hutool.core.util.StrUtil;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName: GenTableColumnServiceImpl
 * @Package: com.fast.generator.service.impl
 * @Description: (用一句话描述该文件做什么)
 * @author: ZhouHuan Emall:18774995071@163.com
 * @time 2020/1/6 15:28
 * @version V1.0
 
 *
 */
@Service("genTableColumnService")
public class GenTableColumnServiceImpl extends ServiceImpl<GenTableColumnDao, GenTableColumnEntity>
        implements GenTableColumnService {

    @Override
    public List<GenTableColumnEntity> selectGenTableColumnListByTableId(Map<String, Object> params) {
        String tableId = (String) params.get("tableId");
        List<GenTableColumnEntity> list = this.list(new QueryWrapper<GenTableColumnEntity>()
                .eq(StrUtil.isNotBlank(tableId), "table_id", tableId).orderByAsc("sort").select("column_id, table_id, column_name, column_comment, column_type," +
                        " java_type, java_field, is_pk, is_increment, is_required, is_insert, is_edit, is_list, " +
                        "is_query, query_type, html_type, dict_type,is_table_sort, sort,circle_type, edit_info, create_by, create_time, update_by, update_time"));
        return list;
    }

    @Override
    public int updateGenTableColumn(GenTableColumnEntity genTable) {
        return this.baseMapper.updateGenTableColumn(genTable);
    }

    @Override
    public List<GenTableColumnEntity> selectDbTableColumnsByName(String tableName) {
        return this.baseMapper.selectDbTableColumnsByName(tableName);
    }

    @Override
    public List<GenTableColumnEntity> selectDbTableColumnsByName1(String tableName) {
        return this.baseMapper.selectDbTableColumnsByName(tableName);
    }

    @Override
    public int deleteGenTableColumnByIds(Long[] ids) {
        return this.baseMapper.deleteGenTableColumnByIds(ids);
    }
}
