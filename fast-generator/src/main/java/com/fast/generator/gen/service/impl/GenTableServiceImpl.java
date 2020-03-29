package com.fast.generator.gen.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.core.exception.RxcException;
import com.fast.common.core.page.Query;
import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.ToolUtil;
import com.fast.framework.utils.Global;
import com.fast.generator.gen.dao.GenTableDao;
import com.fast.generator.gen.entity.GenTableColumnEntity;
import com.fast.generator.gen.entity.GenTableEntity;
import com.fast.generator.gen.service.GenTableColumnService;
import com.fast.generator.gen.service.GenTableService;
import com.fast.generator.gen.util.GenUtils;
import com.fast.generator.gen.util.VelocityInitializer;
import com.fast.generator.gen.util.VelocityUtils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @ClassName: GenTableServiceImpl
 * @Package: com.fast.generator.service.impl
 * @Description: (用一句话描述该文件做什么)
 * @author: ZhouHuan Emall:18774995071@163.com
 * @time 2020/1/6 15:24
 * @version V1.0
 
 *
 */
@Service("genTableService")
public class GenTableServiceImpl extends ServiceImpl<GenTableDao, GenTableEntity>
        implements GenTableService {
    private static final Logger log = LoggerFactory.getLogger(GenTableServiceImpl.class);

    @Autowired
    private GenTableColumnService genTableColumnService;

    @Autowired
    @Lazy
    private GenTableService genTableService;

    @Override
    public PageUtil selectGenTableList(Map<String, Object> params) {
        String tableName = (String) params.get("tableName");
        String tableComment = (String) params.get("tableComment");

        Page<GenTableEntity> page = this.baseMapper.selectPage(new Query<GenTableEntity>(params).getPage(),
                new QueryWrapper<GenTableEntity>()
                        .like(StringUtils.isNotBlank(tableName), "table_name", tableName)
                        .like(StringUtils.isNotBlank(tableComment), "table_comment", tableComment)
        );
        return new PageUtil(page);
    }

    @Override
    public byte[] generatorCode(String[] tableNames) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames)
        {
            generatorCode(tableName, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 生成代码
     *
     * @param tableName 表名称
     * @return 数据
     */
    @Override
    public byte[] generatorCode(String tableName)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        generatorCode(tableName, zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    @Override
    public Map<String, String> previewCode(Long tableId) {
        Map<String, String> dataMap = new LinkedHashMap<>();
        // 查询表信息
        GenTableEntity table = this.baseMapper.selectGenTableById(tableId);
        // 查询列信息
        List<GenTableColumnEntity> columns = table.getColumns();
        setPkColumn(table, columns);
        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates)
        {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, CharsetUtil.UTF_8);
            tpl.merge(context, sw);
            String temp =StrUtil.replace(sw.toString(),"<@>","#");
            temp =StrUtil.replace(temp,"<$>","$");
            dataMap.put(template, temp);
        }
        return dataMap;
    }

    @Override
    public boolean genCode(Long tableId) {
        // 查询表信息
        GenTableEntity table = this.baseMapper.selectGenTableById(tableId);
        String path = table.getRunPath().equals("/")? Global.getConifgFile(): table.getRunPath();
        // 查询列信息
        List<GenTableColumnEntity> columns = table.getColumns();
        setPkColumn(table, columns);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);
        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        if(table.getIsCover().equals("N")){
            for (String template : templates)
            {
                if(!template.contains("sql.vm")){
                    String p  = path + VelocityUtils.getFileName(template, table);
                    if(FileUtil.exist(p)){
                        throw  new RxcException(new File(p).getName() +"文件存在","99992");
                    }
                }
            }
        }

        for (String template : templates)
        {
            if(!template.contains("sql.vm")){
                // 渲染模板
                StringWriter sw = new StringWriter();
                Template tpl = Velocity.getTemplate(template, CharsetUtil.UTF_8);
                tpl.merge(context, sw);
                try
                {
                    String p = path + VelocityUtils.getFileName(template, table);
                    String temp =StrUtil.replace(sw.toString(),"<@>","#");
                    temp =StrUtil.replace(temp,"<$>","$");
                    FileUtil.writeString(temp,p,CharsetUtil.UTF_8);
                }
                catch (IORuntimeException e)
                {
                    throw  new RxcException("文件生成失败","99991");
                }
            }
        }
        return true;
    }

    @Override
    public GenTableEntity selectGenTableById(Long id) {
        return this.baseMapper.selectGenTableById(id);
    }

    @Override
    public GenTableEntity selectGenTableMenuById(Long id) {
        return this.baseMapper.selectGenTableMenuById(id);
    }

    @Override
    public void validateEdit(GenTableEntity genTable) {
        if (genTable.isTree())
        {
            if (StringUtils.isEmpty(genTable.getTreeCode()))
            {
                throw new RxcException("树编码字段不能为空");
            }
            else if (StringUtils.isEmpty(genTable.getTreeParentCode()))
            {
                throw new RxcException("树父编码字段不能为空");
            }
            else if (StringUtils.isEmpty(genTable.getTreeName()))
            {
                throw new RxcException("树名称字段不能为空");
            }
        }
    }

    @Override
    public void updateGenTable(GenTableEntity genTable) {
        int row = this.baseMapper.updateGenTable(genTable);
        if (row > 0)
        {
            for (GenTableColumnEntity cenTableColumn : genTable.getColumns())
            {
                genTableColumnService.updateGenTableColumn(cenTableColumn);
            }
        }
    }

    @Override
    public List<GenTableEntity> selectDbTableList0() {
        return this.baseMapper.selectDbTableList();
    }

    @Override
    public List<GenTableEntity> selectDbTableList1() {
        return this.baseMapper.selectDbTableList1();
    }

    @Override
    public List<GenTableEntity> selectDbTableListC() {
        List<GenTableEntity> list = genTableService.selectDbTableList1();
        List<GenTableEntity> notList = this.baseMapper.selectDbNotTableList();


        Iterator it=list.iterator();

        while(it.hasNext()) {
            GenTableEntity g = (GenTableEntity) it.next();
            for(GenTableEntity K : notList){
                if(g.getTableName().equals(K.getTableName())){
                    it.remove();
                }
            }
        }

//        for(int i=0; i< list.size(); i++){
//
//        }
//        for(GenTableEntity g: list){
//            System.out.println(g.getTableName() + "|-->");
//            for(GenTableEntity K : notList){
//                if(g.getTableName().equals(K.getTableName())){
//                    System.out.println(K.getTableName());
//                    list.remove(g);
//                }
//            }
//        }
        return list;
    }

    @Override
    public PageUtil queryDbPage(Map<String, Object> params) {
        String tableName = (String) params.get("tableName");
        String tableComment = (String) params.get("tableComment");
        String dbType = (String) params.get("dbType");
        if(dbType.equals("0")){
            Page<GenTableEntity> tempPage = new Query<GenTableEntity>(params).getPage();
            Page<GenTableEntity> page = tempPage.setRecords(this.baseMapper.selectDbTablePage(
                    tempPage, tableName,tableComment));
            return new PageUtil(page);
        }else{
            List<GenTableEntity> notList = this.baseMapper.selectDbNotTableList();
            List<String> names = null;
            if(ToolUtil.isNotEmpty(notList)){
                names =  notList.stream().map(GenTableEntity :: getTableName).collect(Collectors.toList());
            }
            return genTableService.selectDbTablePage1(params,names);
        }

    }

    @Override
    public List<GenTableEntity> selectDbTableListByNames(String[] tableNames,String dbType) {
        List<GenTableEntity> list = null;
        if(dbType.equals("0")){
            list =  this.baseMapper.selectDbTableListByNames(tableNames);

        }else{
            list = genTableService.selectDbTableListByNames1(tableNames);
        }
        for(GenTableEntity l:list){
            l.setDbType(dbType);
        }
        return list;
    }

    @Override
    public List<GenTableEntity> selectDbTableListByNames1(String[] tableNames) {
        return this.baseMapper.selectDbTableListByNames(tableNames);
    }


    @Override
    public PageUtil selectDbTablePage1(Map<String, Object> params,List<String> notList) {
        String tableName = (String) params.get("tableName");
        String tableComment = (String) params.get("tableComment");
        Page<GenTableEntity> tempPage = new Query<GenTableEntity>(params).getPage();
        Page<GenTableEntity> page = tempPage.setRecords(this.baseMapper.selectDbTablePage1(
                tempPage, tableName,tableComment, notList));
        return new PageUtil(page);
    }

    @Override
    public void importGenTable(List<GenTableEntity> tableList, String operName, String dbType) {
        for (GenTableEntity table : tableList)
        {
            try
            {

                String tableName = table.getTableName();
                GenUtils.initTable(table, operName);
                boolean row = this.save(table);
                if (row)
                {
                    // 保存列信息
                    List<GenTableColumnEntity> genTableColumns = null;
                    if(dbType.equals("0")){
                        genTableColumns  = genTableColumnService.selectDbTableColumnsByName(tableName);
                    }else{
                        genTableColumns  = genTableColumnService.selectDbTableColumnsByName1(tableName);
                    }
                    for (GenTableColumnEntity column : genTableColumns)
                    {
                        GenUtils.initColumnField(column, table);
                        genTableColumnService.save(column);
                    }
                }
            }
            catch (Exception e)
            {
                log.error("表名 " + table.getTableName() + " 导入失败：", e);
            }
        }
    }

    @Override
    public void deleteGenTableByIds(Long[] ids) {
        this.baseMapper.deleteGenTableByIds(ids);
        genTableColumnService.deleteGenTableColumnByIds(ids);
    }


    /**
     * 查询表信息并生成代码
     */
    private void generatorCode(String tableName, ZipOutputStream zip)
    {
        // 查询表信息
        GenTableEntity table = this.baseMapper.selectGenTableByName(tableName);
        // 查询列信息
        List<GenTableColumnEntity> columns = table.getColumns();
        setPkColumn(table, columns);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates)
        {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, CharsetUtil.UTF_8);
            tpl.merge(context, sw);
            try
            {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));

                String temp =StrUtil.replace(sw.toString(),"<@>","#");
                temp =StrUtil.replace(temp,"<$>","$");
                IOUtils.write(temp, zip, CharsetUtil.UTF_8);
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            }
            catch (IOException e)
            {
                log.error("渲染模板失败，表名：" + table.getTableName(), e);
            }
        }
    }

    /**
     * 设置主键列信息
     *
     * @param table 业务表信息
     * @param columns 业务字段列表
     */
    public void setPkColumn(GenTableEntity table, List<GenTableColumnEntity> columns)
    {
        for (GenTableColumnEntity column : columns)
        {
            if (column.isPk())
            {
                table.setPkColumn(column);
                break;
            }
        }
        if (ToolUtil.isEmpty(table.getPkColumn()))
        {
            table.setPkColumn(columns.get(0));
        }
    }
}
