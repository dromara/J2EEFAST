package com.j2eefast;

import com.j2eefast.common.core.config.MyEncryptablePropertyResolver;
import com.j2eefast.common.core.constants.ConfigConstant;
import com.j2eefast.common.core.crypto.MyEncryptablePropertyDetector;
import com.j2eefast.common.core.utils.HexUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description:项目启动入口
 * @author zhouzhou 18774995071@163.com
 * @date 2018-11-14 23:28
 */
@EnableCaching
@SpringBootApplication
public class FastApplication extends SpringBootServletInitializer {
	
	static Logger logger = LoggerFactory.getLogger(FastApplication.class);
	
	public static void main(String[] args) {

		try {
			SpringApplication.run(FastApplication.class, args);
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
			logger.error("项目启动异常:",e);
		}
		
	}

	
	/**
	 * 
	 * web容器中进行部署
	 * 
	 * @author zhouzhou
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FastApplication.class);
	}

}