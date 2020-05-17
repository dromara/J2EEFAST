package com.j2eefast;

import com.j2eefast.common.core.io.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Description:项目启动入口
 * @author zhouzhou 18774995071@163.com
 * @date 2018-11-14 23:28
 */
@EnableCaching
@SpringBootApplication (exclude = {
		SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class FastApplication extends SpringBootServletInitializer {
	
	static Logger log = LoggerFactory.getLogger(FastApplication.class);
	
	public static void main(String[] args) {

		try {
			//启动配置文件扫描
			SpringApplication app = new SpringApplication(FastApplication.class);
			app.setDefaultProperties(PropertiesUtils.getInstance().getProperties());
			app.run(args);
			System.out.println("-----------------------------------------------------\n"
					+ "//             ┏┓   ┏┓					//\n"
					+ "//            ┏┛┻━━━┛┻┓				        //\n"
					+ "//  		   ┃   ☃   ┃					//\n"
					+ "//            ┃ ┳┛ ┗┳ ┃				        //\n"
					+ "//            ┃   ┻   ┃				        //\n"
					+ "//            ┗━┓   ┏━┛				        //\n" 
					+ "//              ┃   ┗━━━┓				//\n"
					+ "//              ┃神兽保佑   ┣┓				//\n" 
					+ "//              ┃启动成功!┏┛				//\n"
					+ "//              ┗┓┓┏━┳┓┏┛				//\n" 
					+ "//               ┃┫┫  ┃┫┫				//\n"
					+ "//               ┗┻┛  ┗┻┛				//\n"
					+ "------------------------------------------------------------------- \n");
		}catch (Exception e) {
			log.error("项目启动异常:",e);
		}
		
	}
	

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		this.setRegisterErrorPageFilter(false);
		application.properties(PropertiesUtils.getInstance().getProperties());
		return application.sources(FastApplication.class);
	}

}