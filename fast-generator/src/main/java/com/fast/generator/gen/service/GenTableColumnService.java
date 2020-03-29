package com.fast.generator.gen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.common.core.utils.PageUtil;
import com.fast.generator.gen.entity.GenTableColumnEntity;
import com.fast.generator.gen.entity.GenTableEntity;

import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 
 * @ClassName: GenTableColumnService
 * @Package: com.fast.generator.service
 * @Description: (用一句话描述该文件做什么)
 * @author: ZhouHuan Emall:18774995071@163.com
 * @time 2020/1/6 15:27
 */
public interface GenTableColumnService extends IService<GenTableColumnEntity> {
    List<GenTableColumnEntity> selectGenTableColumnListByTableId(Map<String, Object> params);

    int updateGenTableColumn(GenTableColumnEntity genTable);

    /**
     * 根据表名称查询列信息
     *
     * @param tableName 表名称
     * @return 列信息
     */
    List<GenTableColumnEntity> selectDbTableColumnsByName(String tableName);


    List<GenTableColumnEntity> selectDbTableColumnsByName1(String tableName);


    int deleteGenTableColumnByIds(Long[] ids);
}
