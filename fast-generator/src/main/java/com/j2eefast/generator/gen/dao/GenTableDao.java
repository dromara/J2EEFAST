//package com.j2eefast.generator.gen.dao;
//
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.j2eefast.generator.gen.entity.GenTableEntity;
//import org.apache.ibatis.annotations.Param;
//
//import java.util.List;
//
///**
// * @version V1.0
//
// * @ClassName: GenTableDao
// * @Package: com.j2eefast.generator.dao
// * @Description: (用一句话描述该文件做什么)
// * @author: zhouzhou Emall:18774995071@163.com
// * @time 2020/1/6 15:22
// */
//public interface GenTableDao  extends BaseMapper<GenTableEntity> {
//
//    GenTableEntity selectGenTableByName(String tableName);
//
//    GenTableEntity selectGenTableById(Long id);
//
//    GenTableEntity selectGenTableMenuById(Long id);
//
//    int updateGenTable(GenTableEntity genTable);
//
//    /**
//     * 查询据库列表
//     *
//     * @return 数据库表集合
//     */
//    List<GenTableEntity> selectDbTableList();
//
//    List<GenTableEntity> selectDbTableList1();
//
//    List<GenTableEntity> selectDbNotTableList();
//
//    List<GenTableEntity>  selectDbTablePage(IPage<?> params, @Param("tableName") String tableName,
//                                            @Param("tableComment") String tableComment);
//
//    List<GenTableEntity>  selectDbTablePage1(IPage<?> params, @Param("tableName") String tableName,
//                                            @Param("tableComment") String tableComment,@Param("notList") List<String> notList);
//    /**
//     * 查询据库列表
//     *
//     * @param tableNames 表名称组
//     * @return 数据库表集合
//     */
//    List<GenTableEntity> selectDbTableListByNames(String[] tableNames);
//
//    int deleteGenTableByIds(Long[] ids);
//
//}
