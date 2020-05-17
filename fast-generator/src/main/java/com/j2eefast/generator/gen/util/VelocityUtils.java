package com.j2eefast.generator.gen.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.j2eefast.framework.utils.UserUtils;
import com.j2eefast.generator.gen.config.GenConfig;
import com.j2eefast.generator.gen.entity.GenTableColumnEntity;
import com.j2eefast.generator.gen.entity.GenTableEntity;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import com.alibaba.fastjson.JSONObject;

/**
 * @author zhouzhou
 * @date 2020-03-12 22:53
 */
public class VelocityUtils
{
    /** 项目空间路径 */
    private static final String PROJECT_PATH = "main/java";

    /** mybatis空间路径 */
    private static final String MYBATIS_PATH = "main/resources/mapper";

    /** html空间路径 */
    private static final String TEMPLATES_PATH = "main/resources/templates/modules";

    /**
     * 设置模板变量信息
     * 
     * @return 模板列表
     */
    public static VelocityContext prepareContext(GenTableEntity genTable)
    {
        //生成模块名
        String moduleName = genTable.getModuleName();
        //生成业务名
        String businessName = genTable.getBusinessName();
        //生成包路径
        String packageName = genTable.getPackageName();
        //使用的模板
        String tplCategory = genTable.getTplCategory();
        //生成功能名
        String functionName = genTable.getFunctionName();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tplCategory", genTable.getTplCategory());
        velocityContext.put("tableName", genTable.getTableName());
        velocityContext.put("functionName", StrUtil.isNotEmpty(functionName) ? functionName : "【请填写功能名称】");
        velocityContext.put("ClassName", genTable.getClassName());
        velocityContext.put("className", StringUtils.uncapitalize(genTable.getClassName()));
        velocityContext.put("moduleName", genTable.getModuleName());
        velocityContext.put("businessName", genTable.getBusinessName());
        velocityContext.put("basePackage", getPackagePrefix(packageName));
        velocityContext.put("packageName", packageName);
        velocityContext.put("author", genTable.getFunctionAuthor());
        velocityContext.put("datetime", DateUtil.format(new Date(),DatePattern.NORM_DATETIME_MINUTE_PATTERN));
        velocityContext.put("pkColumn", genTable.getPkColumn());
        velocityContext.put("parentId", genTable.getParentId());
        velocityContext.put("parentName", genTable.getParentName());
        velocityContext.put("menuName", genTable.getMenuName());
        velocityContext.put("menuOrder", genTable.getMenuOrder());
        velocityContext.put("moduleCodes", genTable.getModuleCodes());

        velocityContext.put("menuIcon", genTable.getMenuIcon());
        velocityContext.put("menuTarget", genTable.getMenuTarget());
        velocityContext.put("createBy", UserUtils.getLoginName());
        velocityContext.put("updateBy", UserUtils.getLoginName());
        velocityContext.put("dbTypeTb", genTable.isDbTypeTb());
        velocityContext.put("importList", getImportList(genTable.getColumns()));
        Sequence n =  new Sequence();
        for(int i=0; i< 5; i++){
            velocityContext.put("mId"+i, n.nextId());
        }
        velocityContext.put("permissionPrefix", getPermissionPrefix(moduleName, businessName));
        velocityContext.put("columns", genTable.getColumns());
        velocityContext.put("table", genTable);
        velocityContext.put("pageTheme", "<@pageTheme mark=\"${config.optimize()?string('true', 'false')}\">");
        if (GenConstants.TPL_TREE.equals(tplCategory))
        {
            setTreeVelocityContext(velocityContext, genTable);
        }
        return velocityContext;
    }

    public static boolean isCrud(String tplCategory,String c)
    {
        if(tplCategory != null ){
            String[] s = tplCategory.split(",");
            for(String l : s){
                if(l.equals(c)){
                    return true;
                }
            }
            return false;
        }else{
            return false;
        }
    }

    public static void setTreeVelocityContext(VelocityContext context, GenTableEntity genTable)
    {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSONObject.parseObject(options);
        String treeCode = getTreecode(paramsObj);
        String treeParentCode = getTreeParentCode(paramsObj);
        String treeName = getTreeName(paramsObj);

        context.put("treeCode", treeCode);
        context.put("treeParentCode", treeParentCode);
        context.put("treeName", treeName);
        context.put("expandColumn", getExpandColumn(genTable));
        if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE))
        {
            context.put("tree_parent_code", paramsObj.getString(GenConstants.TREE_PARENT_CODE));
        }
        if (paramsObj.containsKey(GenConstants.TREE_NAME))
        {
            context.put("tree_name", paramsObj.getString(GenConstants.TREE_NAME));
        }
    }

    /**
     * 获取模板信息
     * 
     * @return 模板列表
     */
    public static List<String> getTemplateList(String tplCategory)
    {
        List<String> templates = new ArrayList<String>();
        templates.add("vm/java/entity.java.vm");
        templates.add("vm/java/mapper.java.vm");
        templates.add("vm/java/service.java.vm");
//        templates.add("vm/java/serviceImpl.java.vm");
        templates.add("vm/java/controller.java.vm");
        templates.add("vm/xml/mapper.xml.vm");

        if (isCrud(tplCategory,GenConstants.TPL_CRUD) || isCrud(tplCategory,GenConstants.TPL_R) )
        {
            templates.add("vm/html/list.html.vm");
        }
        else if (isCrud(tplCategory,GenConstants.TPL_TREE))
        {
            templates.add("vm/html/tree.html.vm");
            templates.add("vm/html/list-tree.html.vm");
        }
        if (isCrud(tplCategory,GenConstants.TPL_CRUD) || isCrud(tplCategory,GenConstants.TPL_C) )
        {
            templates.add("vm/html/add.html.vm");
        }
        if (isCrud(tplCategory,GenConstants.TPL_CRUD) || isCrud(tplCategory,GenConstants.TPL_U) )
        {
            templates.add("vm/html/edit.html.vm");
        }
        templates.add("vm/sql/sql.vm");
        return templates;
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, GenTableEntity genTable)
    {
        // 文件名称
        String fileName = "";
        // 包路径
        String packageName = genTable.getPackageName(); //com.j2eefast.bcs.tbc
        // 模块名
        String moduleName = genTable.getModuleName(); //tbc
        // 大写类名
        String className = genTable.getClassName(); //TbcDriverLog
        // 业务名称
        String businessName = genTable.getBusinessName();//log

        String javaPath = PROJECT_PATH + "/" + StringUtils.replace(packageName, ".", "/");
        String mybatisPath = MYBATIS_PATH + "/" + moduleName;
        String htmlPath = TEMPLATES_PATH + "/" + moduleName + "/" + businessName;

        if (template.contains("entity.java.vm"))
        {
            fileName = StrUtil.format("{}/entity/{}Entity.java", javaPath, className);
        }
        else if (template.contains("mapper.java.vm"))
        {
            fileName = StrUtil.format("{}/mapper/{}Mapper.java", javaPath, className);
        }
        else if (template.contains("service.java.vm"))
        {
            fileName = StrUtil.format("{}/service/{}Service.java", javaPath, className);
        }
//        else if (template.contains("serviceImpl.java.vm"))
//        {
//            fileName = StrUtil.format("{}/service/impl/{}ServiceImpl.java", javaPath, className);
//        }
        else if (template.contains("controller.java.vm"))
        {
            fileName = StrUtil.format("{}/controller/{}Controller.java", javaPath, className);
        }
        else if (template.contains("mapper.xml.vm"))
        {
            fileName = StrUtil.format("{}/{}Mapper.xml", mybatisPath, className);
        }
        else if (template.contains("list.html.vm"))
        {
            fileName = StrUtil.format("{}/{}.html", htmlPath, businessName);
        }
        else if (template.contains("list-tree.html.vm"))
        {
            fileName = StrUtil.format("{}/{}.html", htmlPath, businessName);
        }
        else if (template.contains("tree.html.vm"))
        {
            fileName = StrUtil.format("{}/tree.html", htmlPath);
        }
        else if (template.contains("add.html.vm"))
        {
            fileName = StrUtil.format("{}/add.html", htmlPath);
        }
        else if (template.contains("edit.html.vm"))
        {
            fileName = StrUtil.format("{}/edit.html", htmlPath);
        }
        else if (template.contains("sql.vm"))
        {
            fileName = businessName + "Menu.sql";
        }
        return fileName;
    }

    /**
     * 获取项目文件路径
     * 
     * @return 路径
     */
    public static String getProjectPath()
    {
        String packageName = GenConfig.getPackageName();
        StringBuffer projectPath = new StringBuffer();
        projectPath.append("main/java/");
        projectPath.append(packageName.replace(".", "/"));
        projectPath.append("/");
        return projectPath.toString();
    }

    /**
     * 获取包前缀
     * 
     * @param packageName 包名称
     * @return 包前缀名称
     */
    public static String getPackagePrefix(String packageName)
    {
        int lastIndex = packageName.lastIndexOf(".");
        String basePackage = StringUtils.substring(packageName, 0, lastIndex);
        return basePackage;
    }

    /**
     * 根据列类型获取导入包
     * 
     * @param columns 列集合
     * @return 返回需要导入的包列表
     */
    public static HashSet<String> getImportList(List<GenTableColumnEntity> columns)
    {
        HashSet<String> importList = new HashSet<String>();
        for (GenTableColumnEntity column : columns)
        {
            if (!column.isSuperColumn() && GenConstants.TYPE_DATE.equals(column.getJavaType()))
            {
                importList.add("java.util.Date");
            }
            else if (!column.isSuperColumn() && GenConstants.TYPE_BIGDECIMAL.equals(column.getJavaType()))
            {
                importList.add("java.math.BigDecimal");
            }
        }
        return importList;
    }

    /**
     * 获取权限前缀
     * 
     * @param moduleName 模块名称
     * @param businessName 业务名称
     * @return 返回权限前缀
     */
    public static String getPermissionPrefix(String moduleName, String businessName)
    {
        return StrUtil.format("{}:{}", moduleName, businessName);
    }

    /**
     * 获取树编码
     * 
     * @param paramsObj 生成其他选项
     * @return 树编码
     */
    public static String getTreecode(JSONObject paramsObj)
    {
        if (paramsObj.containsKey(GenConstants.TREE_CODE))
        {
            return StrUtil.toCamelCase(paramsObj.getString(GenConstants.TREE_CODE));
        }
        return "";
    }

    /**
     * 获取树父编码
     * 
     * @param paramsObj 生成其他选项
     * @return 树父编码
     */
    public static String getTreeParentCode(JSONObject paramsObj)
    {
        if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE))
        {
            return StrUtil.toCamelCase(paramsObj.getString(GenConstants.TREE_PARENT_CODE));
        }
        return "";
    }

    /**
     * 获取树名称
     * 
     * @param paramsObj 生成其他选项
     * @return 树名称
     */
    public static String getTreeName(JSONObject paramsObj)
    {
        if (paramsObj.containsKey(GenConstants.TREE_NAME))
        {
            return StrUtil.toCamelCase(paramsObj.getString(GenConstants.TREE_NAME));
        }
        return "";
    }

    /**
     * 获取需要在哪一列上面显示展开按钮
     * 
     * @param genTable 业务表对象
     * @return 展开按钮列序号
     */
    public static int getExpandColumn(GenTableEntity genTable)
    {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSONObject.parseObject(options);
        String treeName = paramsObj.getString(GenConstants.TREE_NAME);
        int num = 0;
        for (GenTableColumnEntity column : genTable.getColumns())
        {
            if (column.isList())
            {
                num++;
                String columnName = column.getColumnName();
                if (columnName.equals(treeName))
                {
                    break;
                }
            }
        }
        return num;
    }
}