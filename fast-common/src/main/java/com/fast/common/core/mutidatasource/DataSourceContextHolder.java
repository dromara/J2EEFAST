package com.fast.common.core.mutidatasource;

/**
 * datasource的上下文
 * @author zhouzhou
 * @date 2020-03-12 09:48
 */
public class DataSourceContextHolder {
	
	private static final ThreadLocal<String> CONTEXT_HOLDER 		   = new ThreadLocal<String>();
	
    /**
     * 设置数据源类型
     *
     * @param dataSourceType 数据库类型
     */
    public static void setDataSourceType(String dataSourceType) {
    	CONTEXT_HOLDER.set(dataSourceType);
    }

    /**
     * 获取数据源类型
     */
    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清除数据源类型
     */
    public static void clearDataSourceType() {
    	CONTEXT_HOLDER.remove();
    }
}
