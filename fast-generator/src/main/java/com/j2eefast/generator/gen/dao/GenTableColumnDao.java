//package com.j2eefast.generator.gen.dao;
//
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.j2eefast.generator.gen.entity.GenTableColumnEntity;
//
//import java.util.List;
//
///**
// * @version V1.0
//
// * @ClassName: GenTableColumnDao
// * @Package: com.j2eefast.generator.dao
// * @Description: (用一句话描述该文件做什么)
// * @author: zhouzhou Emall:18774995071@163.com
// * @time 2020/1/6 15:27
// */
//public interface GenTableColumnDao extends BaseMapper<GenTableColumnEntity> {
//    int updateGenTableColumn(GenTableColumnEntity genTableColumn);
//
//
//    /**
//     * 根据表名称查询列信息
//     *
//     * @param tableName 表名称
//     * @return 列信息
//     */
//    List<GenTableColumnEntity> selectDbTableColumnsByName(String tableName);
//
//
//    int deleteGenTableColumnByIds(Long[] ids);
//}
