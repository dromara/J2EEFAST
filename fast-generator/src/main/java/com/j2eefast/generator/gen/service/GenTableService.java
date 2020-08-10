package com.j2eefast.generator.gen.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.core.mutidatasource.DataSourceContextHolder;
import com.j2eefast.common.core.page.Query;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.common.db.context.DataSourceContext;
import com.j2eefast.common.db.entity.SysDatabaseEntity;
import com.j2eefast.framework.sys.mapper.SysDatabaseMapper;
import com.j2eefast.framework.utils.Global;
import com.j2eefast.generator.gen.entity.GenTableColumnEntity;
import com.j2eefast.generator.gen.entity.GenTableEntity;
import com.j2eefast.generator.gen.mapper.GenTableMapper;
import com.j2eefast.generator.gen.util.GenUtils;
import com.j2eefast.generator.gen.util.Option;
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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
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
    
    
	@Resource
	private SysDatabaseMapper sysDatabaseMapper;

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
        GenTableEntity table = findGenTableByName(tableName);
        // 查询列信息
        List<GenTableColumnEntity> columns = table.getColumns();
        setPkColumn(table, columns);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory(),table.getTarget());
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
        GenTableEntity table = findGenTableById(tableId);
        String path = table.getRunPath().equals("/")? Global.getConifgFile(): table.getRunPath();
        // 查询列信息
        List<GenTableColumnEntity> columns = table.getColumns();
        setPkColumn(table, columns);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);
        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory(),table.getTarget());
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

        //删除无用生成的文件
        List<String> allTemplates = VelocityUtils.allTemplateList(table.getTarget());
        allTemplates.removeAll(templates);
        for(String template: allTemplates){
            String p = path + VelocityUtils.getFileName(template, table);
            FileUtil.del(p);
        }
        return true;
    }
    /**
    * @Title: findGenTableById 
    * @Description: 已经获取了表的 columns
    * @param id
    * @return  GenTableEntity 
    * @author mfksn001@163.com
    * @Date: 2020年6月2日
     */
    public GenTableEntity findGenTableById(Long id) {
    	GenTableEntity table = genTableMapper.findByTableId(id);
    	if (StringUtils.isNotBlank(table.getOptions())) {
    	    try {
				table.setOption(JSONObject.parseObject(table.getOptions(), Option.class));
			} catch (Exception e) {
				 throw new RxcException("树相关编码解析失败"); 
			}
    	}
    	table.setColumns(genTableColumnService.findListByTableId(id));
        return table;
    }
    
    public GenTableEntity findGenTableByName(String tableName) {
    	GenTableEntity table = genTableMapper.findByName(tableName);
    	if (StringUtils.isNotBlank(table.getOptions())) {
    	    try {
				table.setOption(JSONObject.parseObject(table.getOptions(), Option.class));
			} catch (Exception e) {
				 throw new RxcException("树相关编码解析失败"); 
			}
    	}
    	table.setColumns(genTableColumnService.findListByTableId(table.getId()));
        return table;
    }

    public GenTableEntity findGenTableMenuById(Long id) {
        return this.genTableMapper.findGenTableMenuById(id);
    }

    public void validateEdit(GenTableEntity genTable) {
        if (genTable.isTree()) {
        	if(null == genTable.getOption()) {
        		throw new RxcException("树相关设定字段不能为空");
        	}       	
            if (StringUtils.isEmpty(genTable.getOption().getTreeCode())) {
                throw new RxcException("树编码字段不能为空");
            }
            else if (StringUtils.isEmpty(genTable.getOption().getTreeParentCode())) {
                throw new RxcException("树父编码字段不能为空");
            }
            else if (StringUtils.isEmpty(genTable.getOption().getTreeName())) {
                throw new RxcException("树名称字段不能为空");
            }
        }
    }

    public boolean update(GenTableEntity genTable) {
    	//其它扩展设置
    	if (null != genTable.getOption()) { 
    		genTable.setOptions(JSONObject.toJSONString(genTable.getOption()));
    	}    	
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

    @Transactional
    public void importGenTable(List<GenTableEntity> tableList, String operName, String dbName) {
        for (GenTableEntity table : tableList) {
            try {

                String tableName = table.getTableName();
                GenUtils.initTable(table, operName);
                table.setDbName(dbName);
                boolean row = this.save(table);
                if (row) {
                    // 保存列信息
                //    List<GenTableColumnEntity> genTableColumns = genTableColumnService.selectDbTableColumnsByName(dbName,tableName);
                	 List<GenTableColumnEntity> genTableColumns = genTableColumnService.generateDbTableColumnsByName(dbName,tableName);
                     
                    for (GenTableColumnEntity column : genTableColumns) {
                        GenUtils.initColumnField(column, table);
                        //如果comment为空 设置comment 为JavaField
                        if (StringUtils.isBlank(column.getColumnComment())){ 
                        	column.setColumnComment(column.getJavaField());
                        }
                        genTableColumnService.save(column);
                    }
                }
            }
            catch (Exception e) {
                log.error("表名 " + table.getTableName() + " 导入失败：", e);
            }
        }
    }
    
    public void importGenTable(List<GenTableEntity> tableList, String operName,SysDatabaseEntity db) {
        for (GenTableEntity table : tableList) {
            try {

                String tableName = table.getTableName();
                GenUtils.initTable(table, operName);
                boolean row = this.save(table);
                if (row) {
                    // 保存列信息
                    List<GenTableColumnEntity> genTableColumns = genTableColumnService.generateDbTableColumnsByName(db.getDbType(), db.getSchema(), tableName);

                    for (GenTableColumnEntity column : genTableColumns) {
                        GenUtils.initColumnField(column, table);
                        //如果comment为空 设置comment 为JavaField
                        if (StringUtils.isBlank(column.getColumnComment())){ 
                        	column.setColumnComment(column.getJavaField());
                        }
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
       // GenTableEntity table = this.genTableMapper.findGenTableById(tableId);
        
        GenTableEntity table = genTableService.findGenTableById(tableId);
       
        // 查询列信息
       // table.setColumns(columns);
        List<GenTableColumnEntity> columns = table.getColumns();
        setPkColumn(table, columns);
        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory(),table.getTarget());
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

    
    /**
    * @Title: generateDbTableList 
    * @Description: 获取所有指定数据源的 部分表List
    * @param db
    * @param dbTableName
    * @return  List<GenTableEntity>
    * @author mfksn001@163.com
    * @Date: 2020年5月30日
     */
	public List<GenTableEntity> generateDbTableList(SysDatabaseEntity db, String dbTableName, String dbTableComment) {

		List<GenTableEntity> list = Lists.newArrayList();
		String dbType = db.getDbType();
        List<GenTableEntity> notList = this.list(new QueryWrapper<GenTableEntity>().eq("db_name",db.getDbName()));
        List<String> names = null;
        if(ToolUtil.isNotEmpty(notList)){
            names =  notList.stream().map(GenTableEntity :: getTableName).collect(Collectors.toList());
        }
		DataSourceContextHolder.setDataSourceType(db.getDbName());
		try {
			list = genTableMapper.generateTableList(dbType, db.getSchema(), dbTableName, dbTableComment,names);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSourceContextHolder.clearDataSourceType();
		}
		return list;

	}
	
    public List<GenTableEntity>  generateDbTableList(SysDatabaseEntity db) {
		
		List<GenTableEntity> list = Lists.newArrayList();
		String dbType = db.getDbType();
        List<GenTableEntity> notList = this.list(new QueryWrapper<GenTableEntity>().eq("db_name",db.getDbName()));
        List<String> names = null;
        if(ToolUtil.isNotEmpty(notList)){
            names =  notList.stream().map(GenTableEntity :: getTableName).collect(Collectors.toList());
        }
		if (!DataSourceContext.MASTER_DATASOURCE_NAME.equals(db.getDbName())) {
			DataSourceContextHolder.setDataSourceType(db.getDbName());
			list = genTableMapper.generateTableList(dbType, db.getSchema(),null, null,names);
			DataSourceContextHolder.clearDataSourceType();
		} else {
			list = genTableMapper.generateTableList(dbType, db.getSchema(), null, null,names);
		}

		return list;

	}	
    
    
    /**
    * @Title: generateTablePage 
    * @Description: 根据数据源，获取表的相关信息表分页列表,
    * @param params
    * @return  PageUtil 
    * @author mfksn001@163.com
    * @Date: 2020年6月1日
     */
	public PageUtil generateDbTablePage(Map<String, Object> params) {
		String tableName = (String) params.get("tableName");
		String tableComment = (String) params.get("tableComment");
		String dbName = (String) params.get("dbName");
		Page<GenTableEntity> page = new Query<GenTableEntity>(params).getPage();
		List<GenTableEntity> list = Lists.newArrayList();
		try {
			SysDatabaseEntity db = sysDatabaseMapper.getByName(dbName);
			String dbType = db.getDbType();
			String schema = db.getSchema();
			List<GenTableEntity> notList = this.list(new QueryWrapper<GenTableEntity>().eq("db_name",dbName));
            List<String> names = null;
            if(ToolUtil.isNotEmpty(notList)){
                names =  notList.stream().map(GenTableEntity :: getTableName).collect(Collectors.toList());
            }
			if (!DataSourceContext.MASTER_DATASOURCE_NAME.equals(dbName)) {
				DataSourceContextHolder.setDataSourceType(db.getDbName());  //指定数据源
				list = genTableMapper.generateTablePage(page, dbType, schema, tableName, tableComment,names);
			} else {
				list = genTableMapper.generateTablePage(page, dbType, schema, tableName, tableComment,names);
			}
			page.setRecords(list);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		DataSourceContextHolder.clearDataSourceType();
		return new PageUtil(page);
	}
  
    
    /**
    * @Title: generateDbTableListByNames 
    * @Description: 根据数据源，获取表的相关信息
    * @param db
    * @param tableNames
    * @return  List<GenTableEntity> 
    * @author mfksn001@163.com
    * @Date: 2020年6月1日
     */
    public List<GenTableEntity> generateDbTableListByNames(SysDatabaseEntity db ,String[] tableNames) {
       	List<GenTableEntity> list  = Lists.newArrayList();
       	DataSourceContextHolder.setDataSourceType(db.getDbName());
		try {
			  list = genTableMapper.generateTableListByNames(db.getDbType(),db.getSchema() , tableNames);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			  DataSourceContextHolder.clearDataSourceType();
		}
        return list;
    }
}
