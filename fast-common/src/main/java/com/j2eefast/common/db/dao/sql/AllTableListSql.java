package com.j2eefast.common.db.dao.sql;

/**
 * <p>库中所有表查询</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-20 13:12
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public class AllTableListSql extends AbstractSql {


	@Override
	protected String mysql() {
		return "select table_name as tableName,table_comment as tableComment, create_time as createTime, update_time as updateTime from information_schema.`tables` where table_schema = ?";
	}

	@Override
	protected String sqlServer() {
		return "SELECT DISTINCT\n" +
				"d.name as tableName,\n" +
				"CONVERT(varchar(200), f.value) as tableComment\n" +
				"FROM\n" +
				"syscolumns a\n" +
				"LEFT JOIN systypes b ON a.xusertype= b.xusertype\n" +
				"INNER JOIN sysobjects d ON a.id= d.id\n" +
				"AND d.xtype= 'U'\n" +
				"AND d.name<> 'dtproperties'\n" +
				"LEFT JOIN syscomments e ON a.cdefault= e.id\n" +
				"LEFT JOIN sys.extended_properties g ON a.id= G.major_id\n" +
				"AND a.colid= g.minor_id\n" +
				"LEFT JOIN sys.extended_properties f ON d.id= f.major_id\n" +
				"AND f.minor_id= 0";
	}

	@Override
	protected String pgSql() {
		return "select " +
				"relname as \"tableName\"," +
				"cast(obj_description(relfilenode,'pg_class') as varchar) as \"tableComment\" " +
				"from pg_class c \n" +
				"where  relkind = 'r' and relname not like 'pg_%' and relname not like 'sql_%' order by relname";
	}

	@Override
	protected String oracle() {
	/*	return "select ut.table_name as tableName, co.comments as tableComment from user_tables ut\n" +
				"left join user_tab_comments co on ut.table_name = co.table_name\n" +
				"where tablespace_name is not null and  user= ?";*/
        return " SELECT  t.table_name AS tableName, c.comments AS tableComment ,uob.CREATED as createTime ,uob.LAST_DDL_TIME as updateTime"
        		+ " FROM all_tables t LEFT JOIN user_tab_comments c ON t.table_name = c.table_name left join user_objects uob on  uob.object_name=t.table_name" 
        		+" where  t.OWNER = ?  ORDER BY t.table_name "; 
	}
}
