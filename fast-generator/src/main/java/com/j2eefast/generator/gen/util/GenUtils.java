package com.j2eefast.generator.gen.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.j2eefast.common.core.exception.RxcException;
import com.j2eefast.common.db.dao.sql.AllTableListSql;
import com.j2eefast.common.db.entity.SysDatabaseEntity;
import com.j2eefast.generator.gen.config.GenConfig;
import com.j2eefast.generator.gen.entity.GenTableColumnEntity;
import com.j2eefast.generator.gen.entity.GenTableEntity;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * 代码生成器 工具类
 * 
 * @author ruoyi
 */
@Slf4j
public class GenUtils
{
    /**
     * 初始化表信息
     */
    public static void initTable(GenTableEntity genTable, String operName)
    {
        genTable.setClassName(convertClassName(genTable.getTableName()));
        genTable.setPackageName(GenConfig.getPackageName());
        genTable.setModuleName(getModuleName(GenConfig.getPackageName()));
        genTable.setBusinessName(getBusinessName(genTable.getTableName()));
        genTable.setFunctionName(replaceText(genTable.getTableComment()));
        genTable.setFunctionAuthor(GenConfig.getAuthor());
        genTable.setCreateBy(operName);
    }

    /**
     * 初始化列属性字段
     */
    public static void initColumnField(GenTableColumnEntity column, GenTableEntity table)
    {
        String dataType = getDbType(column.getColumnType()).toLowerCase();
        column.setColumnType(column.getColumnType().toLowerCase());
        String columnName = column.getColumnName().toLowerCase();
        column.setColumnName(columnName);  //设置为小写//  数据库中一般栏位名大写不敏感
        column.setTableId(table.getId());
        column.setCreateBy(table.getCreateBy());
        // 设置java字段名
        column.setJavaField(StrUtil.toCamelCase(columnName));

        if (arraysContains(GenConstants.COLUMNTYPE_STR, dataType))
        {
            column.setJavaType(GenConstants.TYPE_STRING);
            // 字符串长度超过500设置为文本域
            Integer columnLength = getColumnLength(column.getColumnType());
            String htmlType = columnLength >= 500 ? GenConstants.HTML_TEXTAREA : GenConstants.HTML_INPUT;
            column.setHtmlType(htmlType);
        }
        else if (arraysContains(GenConstants.COLUMNTYPE_TIME, dataType))
        {
            column.setJavaType(GenConstants.TYPE_DATE);
            column.setHtmlType(GenConstants.HTML_DATETIME);
        }
        else if (arraysContains(GenConstants.COLUMNTYPE_NUMBER, dataType))
        {
            column.setHtmlType(GenConstants.HTML_INPUT);

            // 如果是浮点型
            String[] str = StringUtils.split(StringUtils.substringBetween(column.getColumnType(), "(", ")"), ",");
            if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0)
            {
                column.setJavaType(GenConstants.TYPE_DOUBLE);
            }
            // 如果是整形
            else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10)
            {
                column.setJavaType(GenConstants.TYPE_INTEGER);
            }
            // 长整形
            else
            {
                column.setJavaType(GenConstants.TYPE_LONG);
            }
        }

        // 插入字段（默认所有字段都需要插入）
        column.setIsInsert(GenConstants.REQUIRE);

        // 编辑字段
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_EDIT, columnName) && !column.isPk())
        {
            column.setIsEdit(GenConstants.REQUIRE);
        }
        // 列表字段
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_LIST, columnName) && !column.isPk())
        {
            column.setIsList(GenConstants.REQUIRE);
        }
        // 查询字段
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_QUERY, columnName) && !column.isPk())
        {
            column.setIsQuery(GenConstants.REQUIRE);
        }

        // 查询字段类型
        if (StrUtil.endWithIgnoreCase(columnName, "name"))
        {
            column.setQueryType(GenConstants.QUERY_LIKE);
        }
        // 状态字段设置单选框
        if (StrUtil.endWithIgnoreCase(columnName, "status"))
        {
            column.setHtmlType(GenConstants.HTML_RADIO);
        }
        // 类型&性别字段设置下拉框
        else if (StrUtil.endWithIgnoreCase(columnName, "type")
                || StrUtil.endWithIgnoreCase(columnName, "sex"))
        {
            column.setHtmlType(GenConstants.HTML_SELECT);
        }
    }

    /**
     * 校验数组是否包含指定值
     * 
     * @param arr 数组
     * @param targetValue 值
     * @return 是否包含
     */
    public static boolean arraysContains(String[] arr, String targetValue)
    {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * 获取模块名
     * 
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName)
    {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        String moduleName = StringUtils.substring(packageName, lastIndex + 1, nameLength);
        return moduleName;
    }

    /**
     * 获取业务名
     * 
     * @param tableName 表名
     * @return 业务名
     */
    public static String getBusinessName(String tableName)
    {
        int lastIndex = tableName.lastIndexOf("_");
        int nameLength = tableName.length();
        String businessName = StringUtils.substring(tableName, lastIndex + 1, nameLength);
        return businessName;
    }

    /**
     * 表名转换成Java类名
     * 
     * @param tableName 表名称
     * @return 类名
     */
    public static String convertClassName(String tableName)
    {
        boolean autoRemovePre = GenConfig.getAutoRemovePre();
        String tablePrefix = GenConfig.getTablePrefix();
        if (autoRemovePre && StringUtils.isNotEmpty(tablePrefix))
        {
            String[] searchList = StringUtils.split(tablePrefix, ",");
            String[] replacementList = emptyList(searchList.length);
            tableName = StringUtils.replaceEach(tableName, searchList, replacementList);
        }
        String name = StrUtil.toCamelCase(tableName);
        return name.substring(0,1).toUpperCase() + name.substring(1);
    }

    /**
     * 关键字替换
     * 
     * @param text 需要被替换的名字
     * @return 替换后的名字
     */
    public static String replaceText(String text){
        return text.replaceAll("(?:表|FAST)", "");
    }

    /**
     * 获取数据库类型字段
     * 
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static String getDbType(String columnType){
        if (StringUtils.indexOf(columnType, "(") > 0){
            return StringUtils.substringBefore(columnType, "(");
        }
        else{
            return columnType;
        }
    }

    /**
     * 获取字段长度
     * 
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static Integer getColumnLength(String columnType)
    {
        if (StringUtils.indexOf(columnType, "(") > 0)
        {
            String length = StrUtil.subBetween(columnType, "(", ")");
            return Integer.valueOf(length);
        }
        else
        {
            return 0;
        }
    }

    /**
     * 获取空数组列表
     * 
     * @param length 长度
     * @return 数组信息
     */
    public static String[] emptyList(int length)
    {
        String[] values = new String[length];
        for (int i = 0; i < length; i++)
        {
            values[i] = StrUtil.EMPTY;
        }
        return values;
    }
    
    

	/**
	* @Title: selectTables 
	* @Description: 获取不同的数据的表名
	* @param dbInfo
	* @return  List<GenTableEntity> 
	* @author mfksn001@163.com
	* @Date: 2020年5月29日
	 */
	public static List<GenTableEntity> selectTables(SysDatabaseEntity dbInfo) {
		List<GenTableEntity> tables = new ArrayList<>();
		try {
			Class.forName(dbInfo.getJdbcDriver());
			Connection conn = DriverManager.getConnection(
					dbInfo.getJdbcUrl(), dbInfo.getUserName(), dbInfo.getPassword());

			//获取数据库名称
			String dbName = getDbName(dbInfo);

			//构造查询语句
			PreparedStatement preparedStatement = conn.prepareStatement(new AllTableListSql().getSql(dbInfo.getJdbcUrl()));

			//拼接设置数据库名称
			if (!dbInfo.getJdbcUrl().contains("sqlserver") && !dbInfo.getJdbcUrl().contains("postgresql")) {
				preparedStatement.setString(1, dbName);
			}

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				GenTableEntity genTableEntity = new GenTableEntity();
				String tableName = resultSet.getString("tableName");
				String tableComment = resultSet.getString("tableComment");
				Date updateTime = resultSet.getDate("updateTime");
				Date createTime = resultSet.getDate("createTime");
				genTableEntity.setTableName(tableName);
				genTableEntity.setTableComment(StringUtils.isBlank(tableComment)?tableName:tableComment);
				genTableEntity.setUpdateTime(updateTime);
				genTableEntity.setCreateTime(createTime);
				tables.add(genTableEntity);
			}
			return tables;
		} catch (Exception ex) {
			log.error("查询所有表错误！", ex);
			throw new RxcException("查询所有表错误！","60001");
		}
	}
	


	/**
	 * 获取数据库名称
	 *
	 * @author zhouzhou
	 * @Date 2019-06-18 15:25
	 */
	private static String getDbName(SysDatabaseEntity dbInfo) {

		if (dbInfo.getJdbcUrl().contains("oracle")) {

			//如果是oracle，直接返回username
			return dbInfo.getUserName().toUpperCase().trim();

		} else if (dbInfo.getJdbcUrl().contains("postgresql")) {

			//postgresql，直接返回最后一个/后边的字符
			int first = dbInfo.getJdbcUrl().lastIndexOf("/") + 1;
			return dbInfo.getJdbcUrl().substring(first);

		} else if (dbInfo.getJdbcUrl().contains("sqlserver")) {

			//sqlserver，直接返回最后一个=后边的字符
			int first = dbInfo.getJdbcUrl().lastIndexOf("=") + 1;
			return dbInfo.getJdbcUrl().substring(first);

		} else {

			//mysql，返回/和?之间的字符
			String jdbcUrl = dbInfo.getJdbcUrl();
			int first = jdbcUrl.lastIndexOf("/") + 1;
			int last = jdbcUrl.indexOf("?");
			return jdbcUrl.substring(first, last);
		}
	}
	
	
	/**
	* @Title: getDbType 
	* @Description: 返回数据库的类型
	* @param dbInfo
	* @return  String 
	* @author mfksn001@163.com
	* @Date: 2020年5月29日
	 */
	public static String getDatebasebType(SysDatabaseEntity dbInfo) {

		if (dbInfo.getJdbcUrl().contains("oracle")) {
			return "oracle";

		} else if (dbInfo.getJdbcUrl().contains("postgresql")) {
			return "postgresql";

		} else if (dbInfo.getJdbcUrl().contains("sqlserver")) {
			return "sqlserver";

		} else if (dbInfo.getJdbcUrl().contains("mysql")) {
             return "mysql";
		}
		return "";
	}

}