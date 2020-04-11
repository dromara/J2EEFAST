package com.j2eefast.common.core.config.properties;

import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>数据库数据源配置</p>
 * <p>说明:类中属性包含默认值的不要在这里修改,应该在"application.yml"中配置</p>
 * @author zhouzhou
 * @date 2020-03-11 19:51
 */
@Data
public class DruidProperties {
	
	/**
	 * 数据库连接地址
	 */
	private String  url   											   = "jdbc:mysql://127.0.0.1:3306/fastdb?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
	/**
	 * 数据库用户名称
	 */
    private String  username										   = "fast";
    /**
     * 数据库用户密码
     */
    private String  password										   = "fast@123$";
    /**
     * 数据库驱动类名
     */
    private String  driverClassName									   = "com.mysql.cj.jdbc.Driver";
    private Integer initialSize 									   = 2;
    private Integer minIdle 										   = 1;
    private Integer maxActive 										   = 20;
    private Integer maxWait 										   = 60000;
    private Integer timeBetweenEvictionRunsMillis 					   = 60000;
    private Integer minEvictableIdleTimeMillis 						   = 300000;
    private String  validationQuery									   = "select 1";
    private Boolean testWhileIdle 									   = true;
    private Boolean testOnBorrow 									   = true;
    private Boolean testOnReturn 									   = true;
    private Boolean poolPreparedStatements 							   = true;
    /**
     * 是否开启
     */
    private Boolean enabled											   = true;
    private Integer maxPoolPreparedStatementPerConnectionSize 		   = 20;
    private String  filters										       = "stat";
    private String  dataSourceName;
    /**
     * 多源数据库
     */
    private Map<Object, Object> map;
    
    public void config(DruidDataSource dataSource) {

        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        //定义初始连接数
        dataSource.setInitialSize(initialSize);    
        //最小空闲
        dataSource.setMinIdle(minIdle);       
        //定义最大连接数
        dataSource.setMaxActive(maxActive);         
        //最长等待时间
        dataSource.setMaxWait(maxWait);             
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(getValidateQueryByUrl(url));
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setEnable(enabled);
        // 打开PSCache，并且指定每个连接上PSCache的大小
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        
        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Properties createProperties() {
        Properties properties = new Properties();
        properties.put("url", this.url);
        properties.put("username", this.username);
        properties.put("password", this.password);
        properties.put("driverClassName", this.driverClassName);
        properties.put("initialSize", this.initialSize);
        properties.put("maxActive", this.maxActive);
        properties.put("minIdle", this.minIdle);
        properties.put("maxWait", this.maxWait);
        properties.put("poolPreparedStatements", this.poolPreparedStatements);
        properties.put("maxPoolPreparedStatementPerConnectionSize", this.maxPoolPreparedStatementPerConnectionSize);
        properties.put("validationQuery", getValidateQueryByUrl(this.url));
        properties.put("testOnBorrow", this.testOnBorrow);
        properties.put("testOnReturn", this.testOnReturn);
        properties.put("testWhileIdle", this.testWhileIdle);
        properties.put(enabled, this.enabled);
        properties.put("timeBetweenEvictionRunsMillis", this.timeBetweenEvictionRunsMillis);
        properties.put("minEvictableIdleTimeMillis", this.minEvictableIdleTimeMillis);
        properties.put("filters", this.filters);
        return properties;
    }

    private String getValidateQueryByUrl(String url) {
    	
    	String oracle = "oracle";
    	String postgresql = "postgresql";
    	String sqlserver = "sqlserver";
    	
        if (url.contains(oracle)) {
            return "select 1 from dual";
        } else if (url.contains(postgresql)) {
            return "select version()";
        } else if (url.contains(sqlserver)) {
            return "select 1";
        } else {
            return "select 1";
        }
    }
}
