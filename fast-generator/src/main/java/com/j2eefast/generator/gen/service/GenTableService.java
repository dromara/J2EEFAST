package com.j2eefast.generator.gen.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.common.db.context.DataSourceContext;
import com.j2eefast.framework.utils.Global;
import com.j2eefast.generator.gen.entity.GenTableColumnEntity;
import com.j2eefast.generator.gen.entity.GenTableEntity;
import com.j2eefast.generator.gen.mapper.GenTableMapper;
import com.j2eefast.generator.gen.util.GenUtils;
import com.j2eefast.generator.gen.util.VelocityInitializer;
import com.j2eefast.generator.gen.util.VelocityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
 * @time 2020/1/6 15:23
 * @version V1.0
 */
@Slf4j
@Service
public class GenTableService extends ServiceImpl<GenTableMapper,GenTableEntity> {


    @Autowired
    private GenTableColumnService genTableColumnService;

    @Lazy
    @Resource
    private GenTableService genTableService;

    @Resource
    private GenTableMapper genTableMapper;

    public PageUtil findPage(Map<String, Object> params) {
        String tableName = (String) params.get("tableName");
        String tableComment = (String) params.get("tableComment");
        Page<GenTableEntity> page = this.baseMapper.selectPage(new Query<GenTableEntity>(params).getPage(),
                new QueryWrapper<GenTableEntity>()
                        .like(StringUtils.isNotBlank(tableName), "table_name", tableName)
                        .like(StringUtils.isNotBlank(tableComment), "table_comment", tableComment)
        );
        return new PageUtil(page);
    }

    public byte[] generatorCode(String[] tableNames) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames)
        {
            generatorCode(tableName, zip);
        }
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }

    public byte[] generatorCode(String tableName){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        generatorCode(tableName, zip);
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }

    /**
     * 查询表信息并生成代码
     */
    private void generatorCode(String tableName, ZipOutputStream zip){
        // 查询表信息
        GenTableEntity table = genTableMapper.findGenTableByName(tableName);
        // 查询列信息
        List<GenTableColumnEntity> columns = table.getColumns();
        setPkColumn(table, columns);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates){
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, CharsetUtil.UTF_8);
            tpl.merge(context, sw);
            try{
                // 添加到zip
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));

                String temp = StrUtil.replace(sw.toString(),"<@>","#");
                temp =StrUtil.replace(temp,"<$>","$");
                IOUtils.write(temp, zip, CharsetUtil.UTF_8);
                IoUtil.close(sw);
                zip.closeEntry();
            } catch (IOException e){
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
    public void setPkColumn(GenTableEntity table, List<GenTableColumnEntity> columns){
        for (GenTableColumnEntity column : columns) {
            if (column.isPk()){
                table.setPkColumn(column);
                break;
            }
        }
        if (ToolUtil.isEmpty(table.getPkColumn())) {
            table.setPkColumn(columns.get(0));
        }
    }

    public boolean genCode(Long tableId) {
        // 查询表信息
        GenTableEntity table = genTableMapper.findGenTableById(tableId);
        String path = table.getRunPath().equals("/")? Global.getConifgFile(): table.getRunPath();
        // 查询列信息
        List<GenTableColumnEntity> columns = table.getColumns();
        setPkColumn(table, columns);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);
        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        if(table.getIsCover().equals("N")){
            for (String template : templates) {
                if(!template.contains("sql.vm")){
                    String p  = path + VelocityUtils.getFileName(template, table);
                    if(FileUtil.exist(p)){
                        throw  new RxcException(new File(p).getName() +"文件存在","99992");
                    }
                }
            }
        }

        for (String template : templates) {
            if(!template.contains("sql.vm")){
                // 渲染模板
                StringWriter sw = new StringWriter();
                Template tpl = Velocity.getTemplate(template, CharsetUtil.UTF_8);
                tpl.merge(context, sw);
                try {
                    String p = path + VelocityUtils.getFileName(template, table);
                    String temp =StrUtil.replace(sw.toString(),"<@>","#");
                    temp =StrUtil.replace(temp,"<$>","$");
                    FileUtil.writeString(temp,p,CharsetUtil.UTF_8);
                }
                catch (IORuntimeException e) {
                    throw  new RxcException("文件生成失败","99991");
                }
            }
        }
        return true;
    }

    public GenTableEntity findGenTableById(Long id) {
        return this.genTableMapper.findGenTableById(id);
    }

    public GenTableEntity findGenTableMenuById(Long id) {
        return this.genTableMapper.findGenTableMenuById(id);
    }

    public void validateEdit(GenTableEntity genTable) {
        if (genTable.isTree()) {
            if (StringUtils.isEmpty(genTable.getTreeCode())) {
                throw new RxcException("树编码字段不能为空");
            }
            else if (StringUtils.isEmpty(genTable.getTreeParentCode())) {
                throw new RxcException("树父编码字段不能为空");
            }
            else if (StringUtils.isEmpty(genTable.getTreeName())) {
                throw new RxcException("树名称字段不能为空");
            }
        }
    }

    public boolean update(GenTableEntity genTable) {
        int row = this.genTableMapper.updateGenTable(genTable);
        if (row > 0) {
            for (GenTableColumnEntity cenTableColumn : genTable.getColumns())
            {
                genTableColumnService.updateGenTableColumn(cenTableColumn);
            }
            return  true;
        }
        return false;
    }

    public List<GenTableEntity> findDbTableList() {
        return this.genTableMapper.findDbTableList();
    }

    public List<GenTableEntity> findSlaveDbTableList(String dbType) {
        return this.genTableMapper.findSlaveDbTableList();
    }


    public List<GenTableEntity> findNoDbTableList(String dbType) {
        List<GenTableEntity> list = genTableService.findSlaveDbTableList(dbType);
        List<GenTableEntity> notList = this.genTableMapper.findDbNotTableList(dbType);
        Iterator it=list.iterator();
        while(it.hasNext()) {
            GenTableEntity g = (GenTableEntity) it.next();
            for(GenTableEntity K : notList){
                if(g.getTableName().equals(K.getTableName())){
                    it.remove();
                }
            }
        }
        return list;
    }

    public PageUtil queryDbPage(Map<String, Object> params) {
        String tableName = (String) params.get("tableName");
        String tableComment = (String) params.get("tableComment");
        String dbType = (String) params.get("dbType");
        if(dbType.equals(DataSourceContext.MASTER_DATASOURCE_NAME)){
            Page<GenTableEntity> tempPage = new Query<GenTableEntity>(params).getPage();
            Page<GenTableEntity> page = tempPage.setRecords(this.genTableMapper.selectDbTablePage(
                    tempPage, tableName,tableComment));
            return new PageUtil(page);
        }else{
            List<GenTableEntity> notList = this.genTableMapper.findDbNotTableList(dbType);
            List<String> names = null;
            if(ToolUtil.isNotEmpty(notList)){
                names =  notList.stream().map(GenTableEntity :: getTableName).collect(Collectors.toList());
            }
            return genTableService.selectNotDbTablePage(dbType,params,names);
        }
    }

    public PageUtil selectNotDbTablePage(String dbType,Map<String, Object> params,List<String> notList) {
        String tableName = (String) params.get("tableName");
        String tableComment = (String) params.get("tableComment");
        Page<GenTableEntity> tempPage = new Query<GenTableEntity>(params).getPage();
        Page<GenTableEntity> page = tempPage.setRecords(this.genTableMapper.selectNotDbTablePage(
                tempPage, tableName,tableComment, notList));
        return new PageUtil(page);
    }

    public void importGenTable(List<GenTableEntity> tableList, String operName, String dbType) {
        for (GenTableEntity table : tableList) {
            try {

                String tableName = table.getTableName();
                GenUtils.initTable(table, operName);
                boolean row = this.save(table);
                if (row) {
                    // 保存列信息
                    List<GenTableColumnEntity> genTableColumns = genTableColumnService.selectDbTableColumnsByName(dbType,tableName);
//                    if(dbType.equals(DataSourceContext.MASTER_DATASOURCE_NAME)){
//
//                        genTableColumns  = genTableColumnService.selectDbTableColumnsByName(tableName);
//                    }else{
//                        genTableColumns  = genTableColumnService.selectDbTableColumnsByName1(tableName);
//                    }
                    for (GenTableColumnEntity column : genTableColumns) {
                        GenUtils.initColumnField(column, table);
                        genTableColumnService.save(column);
                    }
                }
            }
            catch (Exception e) {
                log.error("表名 " + table.getTableName() + " 导入失败：", e);
            }
        }
    }

    public boolean deleteGenTableByIds(Long[] ids) {
       return this.genTableMapper.deleteGenTableByIds(ids)> 0 &&
               genTableColumnService.deleteGenTableColumnByIds(ids) >0;
    }

    public Map<String, String> previewCode(Long tableId) {

        Map<String, String> dataMap = new LinkedHashMap<>();
        // 查询表信息
        GenTableEntity table = this.genTableMapper.findGenTableById(tableId);
        // 查询列信息
        List<GenTableColumnEntity> columns = table.getColumns();
        setPkColumn(table, columns);
        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates){
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

    public List<GenTableEntity> selectDbTableListByNames(String[] tableNames,String dbType) {
        List<GenTableEntity> list = null;
        if(dbType.equals(DataSourceContext.MASTER_DATASOURCE_NAME)){
            list =  this.genTableMapper.selectDbTableListByNames(tableNames);
        }else{
            list = genTableService.selectDbTableListByNames1(dbType,tableNames);
        }
        for(GenTableEntity l:list){
            l.setDbType(dbType);
        }
        return list;
    }

    public List<GenTableEntity> selectDbTableListByNames1(String dbType,String[] tableNames) {
        return this.genTableMapper.selectDbTableListByNames(tableNames);
    }
}
