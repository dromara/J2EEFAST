package com.j2eefast.generator.gen.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.j2eefast.common.core.base.entity.BaseEntity;
import com.j2eefast.generator.gen.util.GenConstants;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 *
 * @ClassName: GenTable
 * @Package: com.j2eefast.generator.entity
 * @Description: (用一句话描述该文件做什么)
 * @author: zhouzhou Emall:18774995071@163.com
 * @time 2020/1/6 15:14
 * @version V1.0
 
 *
 */
@TableName("gen_table")
public class GenTableEntity extends BaseEntity {

    /** 编号 */
    @TableId
    private Long tableId;

    /** 表名称 */
    @NotBlank(message = "表名称不能为空")
    private String tableName;

    /** 表描述 */
    @NotBlank(message = "表描述不能为空")
    private String tableComment;

    /** 实体类名称(首字母大写) */
    @NotBlank(message = "实体类名称不能为空")
    private String className;

    /** 使用的模板（crud单表操作 tree树表操作）crud,tree,r,u,d */
    private String tplCategory;

    /** 生成包路径 */
    @NotBlank(message = "生成包路径不能为空")
    private String packageName;

    /** 生成模块名 */
    @NotBlank(message = "生成模块名不能为空")
    private String moduleName;

    /** 生成业务名 */
    @NotBlank(message = "生成业务名不能为空")
    private String businessName;

    /** 生成功能名 */
    @NotBlank(message = "生成功能名不能为空")
    private String functionName;

    /** 生成作者 */
    @NotBlank(message = "作者不能为空")
    private String functionAuthor;

    /**父菜单ID*/
    private Long parentId;

    /**父菜单名称*/
    private String  parentName;

    private int menuOrder;

    /**菜单名称*/
    private String menuName;

    /**所属模块*/
    private String moduleCodes;

    /**菜单图标*/
    private String menuIcon;

    /**菜单打开方式*/
    private String menuTarget;

    /**代码生成路径*/
    private String runPath;

    /**是否需要删除按钮*/
    private String isDel;

  /** 主从数据库*/
    private String dbType;

    /** 操作按钮风格(default,icon)*/
    private String actionsType;

    /**生成代码是否覆盖替换**/
    private String isCover;

    /** 其它生成选项 */
    private String options;

    /** 主键信息 */
    @TableField(exist = false)
    private GenTableColumnEntity pkColumn;

    /** 表列信息 */
    @TableField(exist = false)
    private List<GenTableColumnEntity> columns;

    /** 树编码字段 */
    @TableField(exist = false)
    private String treeCode;

    /** 树父编码字段 */
    @TableField(exist = false)
    private String treeParentCode;

    /** 树名称字段 */
    @TableField(exist = false)
    private String treeName;

    public int getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getModuleCodes() {
        return moduleCodes;
    }

    public void setModuleCodes(String moduleCodes) {
        this.moduleCodes = moduleCodes;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getMenuTarget() {
        return menuTarget;
    }

    public void setMenuTarget(String menuTarget) {
        this.menuTarget = menuTarget;
    }

    public String getIsCover() {
        return isCover;
    }

    public void setIsCover(String isCover) {
        this.isCover = isCover;
    }

    public String getActionsType() {
        return actionsType;
    }

    public void setActionsType(String actionsType) {
        this.actionsType = actionsType;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getRunPath() {
        return runPath;
    }

    public void setRunPath(String runPath) {
        this.runPath = runPath;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTplCategory() {
        return tplCategory;
    }

    public void setTplCategory(String tplCategory) {
        this.tplCategory = tplCategory;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionAuthor() {
        return functionAuthor;
    }

    public void setFunctionAuthor(String functionAuthor) {
        this.functionAuthor = functionAuthor;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public GenTableColumnEntity getPkColumn() {
        return pkColumn;
    }

    public void setPkColumn(GenTableColumnEntity pkColumn) {
        this.pkColumn = pkColumn;
    }

    public List<GenTableColumnEntity> getColumns() {
        return columns;
    }

    public void setColumns(List<GenTableColumnEntity> columns) {
        this.columns = columns;
    }

    public String getTreeCode() {
        return treeCode;
    }

    public void setTreeCode(String treeCode) {
        this.treeCode = treeCode;
    }

    public String getTreeParentCode() {
        return treeParentCode;
    }

    public void setTreeParentCode(String treeParentCode) {
        this.treeParentCode = treeParentCode;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public boolean isRDel(){
        return isDelType(this.isDel);
    }

    public boolean isC()
    {
        return isCrud(this.tplCategory,GenConstants.TPL_C);
    }
    public boolean isR()
    {
        return isCrud(this.tplCategory,GenConstants.TPL_R);
    }
    public boolean isU()
    {
        return isCrud(this.tplCategory,GenConstants.TPL_U);
    }
    public boolean isD()
    {
        return isCrud(this.tplCategory,GenConstants.TPL_D);
    }

    public boolean isCrud()
    {
        return isCrud(this.tplCategory,GenConstants.TPL_CRUD);
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

    public boolean isTree()
    {
        return isCrud(this.tplCategory,GenConstants.TPL_TREE);
    }

    public static boolean isTree(String tplCategory)
    {
        return tplCategory != null && StringUtils.equals(GenConstants.TPL_TREE, tplCategory);
    }


    public boolean isSuperColumn(String javaField)
    {
        return isSuperColumn(this.tplCategory, javaField);
    }

    public static boolean isSuperColumn(String tplCategory, String javaField)
    {
        if (isTree(tplCategory))
        {
            StringUtils.equalsAnyIgnoreCase(javaField, GenConstants.TREE_ENTITY);
        }
        return StringUtils.equalsAnyIgnoreCase(javaField, GenConstants.BASE_ENTITY);
    }


    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public boolean isInsert(String isInsert)
    {
        return isInsert != null && StringUtils.equals("1", isInsert);
    }

    public boolean isDelType(String d){
        return d != null && d.equals("Y");
    }

    public boolean isDbTypeTb()
    {
        return isInsert(this.dbType);
    }
}
