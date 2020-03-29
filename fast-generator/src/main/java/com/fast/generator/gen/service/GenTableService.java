package com.fast.generator.gen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.common.core.utils.PageUtil;
import com.fast.generator.gen.entity.GenTableEntity;

import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName: GenTableService
 * @Package: com.fast.generator.service
 * @Description: (用一句话描述该文件做什么)
 * @author: ZhouHuan Emall:18774995071@163.com
 * @time 2020/1/6 15:23
 * @version V1.0
 
 *
 */
public interface GenTableService extends IService<GenTableEntity> {
    PageUtil selectGenTableList(Map<String, Object> params);


    /**
     * @Description: 生成批量代码
     * @auther: ZhouHuan 18774995071@163.com
     * @date: 2020/1/6 17:17
     */
    byte[] generatorCode(String[] tableNames);


    /**
     * @Description: 生成代码
     * @auther: ZhouHuan 18774995071@163.com
     * @date: 2020/1/6 17:20
     */
    byte[] generatorCode(String tableName);

    /**
     * 预览代码
     *
     * @param tableId 表编号
     * @return 预览数据列表
     */
    Map<String, String> previewCode(Long tableId);

    boolean genCode(Long tableId);

    /**
     * 查询业务信息
     *
     * @param id 业务ID
     * @return 业务信息
     */
    GenTableEntity selectGenTableById(Long id);

    GenTableEntity selectGenTableMenuById(Long id);

    /**
     * 修改保存参数校验
     *
     * @param genTable 业务信息
     */
    void validateEdit(GenTableEntity genTable);


    void updateGenTable(GenTableEntity genTable);


    List<GenTableEntity> selectDbTableList0();

    List<GenTableEntity> selectDbTableList1();

    List<GenTableEntity> selectDbTableListC();

    PageUtil queryDbPage(Map<String, Object> params);


    /**
     * 查询据库列表
     *
     * @param tableNames 表名称组
     * @return 数据库表集合
     */
    List<GenTableEntity> selectDbTableListByNames(String[] tableNames,String dbType);

    List<GenTableEntity> selectDbTableListByNames1(String[] tableNames);

    PageUtil  selectDbTablePage1(Map<String, Object> params,List<String> notList);

    /**
     * 导入表结构
     *
     * @param tableList 导入表列表
     * @param operName 操作人员
     */
    void importGenTable(List<GenTableEntity> tableList, String operName, String dbType);


    void deleteGenTableByIds(Long[] ids);

}
